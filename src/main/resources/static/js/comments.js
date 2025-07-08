let currentUser = null;
let allComments = [];

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
    loadComments();
});

// 检查登录状态
async function checkLoginStatus() {
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            currentUser = JSON.parse(userStr);
            document.getElementById('userName').textContent = currentUser.name || '用户';
            document.getElementById('guestButtons').style.display = 'none';
            document.getElementById('userSection').style.display = 'flex';
        } catch (error) {
            console.error('解析用户信息失败:', error);
            localStorage.removeItem('currentUser');
            showGuestSection();
        }
    } else {
        // 如果localStorage没有用户信息，尝试从服务器获取
        try {
            const response = await fetch('/api/users/current');
            const data = await response.json();

            if (data.success && data.user) {
                currentUser = data.user;
                localStorage.setItem('currentUser', JSON.stringify(data.user));
                document.getElementById('userName').textContent = currentUser.name || '用户';
                document.getElementById('guestButtons').style.display = 'none';
                document.getElementById('userSection').style.display = 'flex';
            } else {
                showGuestSection();
            }
        } catch (error) {
            console.error('获取用户信息失败:', error);
            showGuestSection();
        }
    }
}

// 显示游客状态
function showGuestSection() {
    document.getElementById('guestButtons').style.display = 'flex';
    document.getElementById('userSection').style.display = 'none';
    // 如果未登录，跳转到登录页面
    window.location.href = '/login';
}

// 退出登录
async function logout() {
    if (confirm('确定要退出登录吗？')) {
        try {
            await fetch('/api/users/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
        } catch (error) {
            console.error('退出登录API调用失败:', error);
        }

        localStorage.removeItem('currentUser');
        window.location.reload();
    }
}

// 加载评论数据
async function loadComments() {
    if (!currentUser) {
        showGuestSection();
        return;
    }

    try {
        // 确保有正确的用户ID
        const userId = currentUser.userId || currentUser.id;
        if (!userId) {
            console.error('用户ID不存在');
            showGuestSection();
            return;
        }

        const response = await fetch(`/api/comments/user/${userId}/with-dish-info`);
        const data = await response.json();

        if (Array.isArray(data)) {
            // 如果返回的是数组，说明API直接返回了评论列表
            allComments = data;
            updateStats();
            displayComments(allComments);
        } else if (data.success) {
            // 如果返回的是包装对象
            allComments = data.comments || [];
            updateStats();
            displayComments(allComments);
        } else {
            document.getElementById('commentsContainer').innerHTML =
                '<div class="no-comments">加载评论失败</div>';
        }
    } catch (error) {
        console.error('加载评论失败:', error);
        document.getElementById('commentsContainer').innerHTML =
            '<div class="no-comments">加载失败，请重试</div>';
    }
}

// 更新统计信息
function updateStats() {
    const totalComments = allComments.length;
    const avgRating = totalComments > 0
        ? (allComments.reduce((sum, comment) => sum + comment.rating, 0) / totalComments).toFixed(1)
        : '0.0';
    const commentedDishes = new Set(allComments.map(comment => comment.dishId)).size;

    document.getElementById('totalComments').textContent = totalComments;
    document.getElementById('avgRating').textContent = avgRating;
    document.getElementById('commentedDishes').textContent = commentedDishes;
}

// 显示评论列表
function displayComments(comments) {
    const container = document.getElementById('commentsContainer');

    if (comments.length === 0) {
        container.innerHTML = '<div class="no-comments">暂无评论</div>';
        return;
    }

    const commentsList = document.createElement('div');
    commentsList.className = 'comments-list';

    comments.forEach(comment => {
        const commentItem = createCommentItem(comment);
        commentsList.appendChild(commentItem);
    });

    container.innerHTML = '';
    container.appendChild(commentsList);
}

// 创建评论项
function createCommentItem(comment) {
    const item = document.createElement('div');
    item.className = 'comment-item';

    const stars = '★'.repeat(comment.rating) + '☆'.repeat(5 - comment.rating);
    const date = new Date(comment.createdAt).toLocaleDateString('zh-CN');

    // 获取菜品信息，支持不同的数据结构
    const dishName = comment.dishName || comment.dish?.name || '未知菜品';
    const campus = comment.campus || comment.dish?.campus || '未知校区';
    const canteen = comment.canteen || comment.dish?.canteen || '未知食堂';
    const stall = comment.stall || comment.dish?.stall || '未知档口';

    item.innerHTML = `
                <div class="comment-header">
                    <div class="dish-info">
                        <div class="dish-name">${dishName}</div>
                        <div class="dish-location">${campus} - ${canteen} - ${stall}</div>
                    </div>
                    <div style="display: flex; align-items: center;">
                        <span class="comment-rating">${stars}</span>
                        <span class="comment-date">${date}</span>
                    </div>
                </div>
                <div class="comment-content">${comment.content || '暂无评论内容'}</div>
                <div class="comment-actions">
                    <a href="/dish-detail?id=${comment.dishId}" class="btn btn-primary">查看菜品</a>
                    <button class="btn btn-danger" onclick="deleteComment(${comment.commentId})">删除评论</button>
                </div>
            `;

    return item;
}

// 筛选评论
function filterComments() {
    const ratingFilter = document.getElementById('ratingFilter').value;

    if (!ratingFilter) {
        displayComments(allComments);
        return;
    }

    const filteredComments = allComments.filter(comment =>
        comment.rating == ratingFilter
    );

    displayComments(filteredComments);
}

// 删除评论
async function deleteComment(commentId) {
    if (!confirm('确定要删除这条评论吗？')) {
        return;
    }

    try {
        const response = await fetch(`/api/comments/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const data = await response.json();

        if (data.success) {
            alert('评论删除成功');
            loadComments(); // 重新加载评论
        } else {
            alert('删除失败：' + (data.message || '未知错误'));
        }
    } catch (error) {
        console.error('删除评论失败:', error);
        alert('删除失败，请重试');
    }
}