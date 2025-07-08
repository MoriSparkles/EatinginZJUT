let currentDishId = null;
let currentUserId = null;
let allComments = [];
let selectedRating = 0; // 添加星级评分变量

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();

    const urlParams = new URLSearchParams(window.location.search);
    currentDishId = parseInt(urlParams.get('id'));
    currentUserId = getCurrentUserId(); // 从localStorage或session获取用户ID

    if (currentDishId && !isNaN(currentDishId)) {
        loadDishDetail();
        loadComments();
    } else {
        document.getElementById('dishDetail').innerHTML = '<div class="no-comments">菜品ID无效</div>';
    }

    // 初始化星级评分事件监听器
    setupStarRating();
});

// 检查登录状态
function checkLoginStatus() {
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            const user = JSON.parse(userStr);
            showUserSection(user);
        } catch (error) {
            console.error('解析用户信息失败:', error);
            localStorage.removeItem('currentUser');
            showGuestSection();
        }
    } else {
        showGuestSection();
    }
}

// 显示用户已登录状态
function showUserSection(user) {
    document.getElementById('guestButtons').style.display = 'none';
    document.getElementById('userSection').style.display = 'flex';
    document.getElementById('userName').textContent = user.name || '用户';
}

// 显示游客状态
function showGuestSection() {
    document.getElementById('guestButtons').style.display = 'flex';
    document.getElementById('userSection').style.display = 'none';
}

// 获取当前用户ID（这里需要根据实际登录状态获取）
function getCurrentUserId() {
    // 从localStorage获取用户信息
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            const user = JSON.parse(userStr);
            return user.userId || user.id;
        } catch (error) {
            console.error('解析用户信息失败:', error);
        }
    }
    return null; // 未登录用户
}

// 加载菜品详情
async function loadDishDetail() {
    try {
        const response = await fetch(`/api/dishes/${currentDishId}`);
        const data = await response.json();

        if (response.ok && data && data.dish) {
            displayDishDetail(data);
        } else {
            document.getElementById('dishDetail').innerHTML = '<div class="no-comments">菜品不存在</div>';
        }
    } catch (error) {
        console.error('加载菜品详情失败:', error);
        document.getElementById('dishDetail').innerHTML = '<div class="no-comments">加载失败</div>';
    }
}

// 显示菜品详情
async function displayDishDetail(data) {
    const dish = data.dish;
    const averageRating = data.averageRating || 0;
    const commentCount = data.commentCount || 0;
    const ratingStats = data.ratingStats || {};

    const stars = '★'.repeat(Math.round(averageRating)) + '☆'.repeat(5 - Math.round(averageRating));

    document.getElementById('dishDetail').innerHTML = `
                <div class="dish-header">
                    <div class="dish-image" style="cursor:zoom-in;position:relative;">
                        ${dish.imageUrl ? `<img src="${dish.imageUrl}" alt="${dish.dishName}" onclick="showImagePreview('${dish.imageUrl.replace(/'/g, '\'')}', '${dish.dishName.replace(/'/g, '\'')}')">` : '🍽️'}
                        <span style="position:absolute;left:0;right:0;bottom:8px;color:#fff;background:rgba(0,0,0,0.35);font-size:13px;text-align:center;opacity:0;pointer-events:none;transition:opacity 0.2s;padding:2px 0;" class="img-tip">点击查看大图</span>
                    </div>
                    <div class="dish-info">
                        <div class="dish-title">${dish.dishName}</div>
                        <div class="dish-meta">
                            <div class="meta-item">
                                <span class="meta-label">校区:</span>
                                <span class="meta-value">${dish.campus}</span>
                            </div>
                            <div class="meta-item">
                                <span class="meta-label">食堂:</span>
                                <span class="meta-value">${dish.canteen}</span>
                            </div>
                            <div class="meta-item">
                                <span class="meta-label">档口:</span>
                                <span class="meta-value">${dish.stall}</span>
                            </div>
                            <div class="meta-item">
                                <span class="meta-label">价格:</span>
                                <span class="meta-value">￥${dish.price}</span>
                            </div>
                        </div>
                        <div class="rating-section">
                            <div class="rating-display">
                                <div class="stars">${stars}</div>
                                <div class="rating-text">${averageRating.toFixed(1)}分</div>
                            </div>
                            <div class="rating-stats">
                                <span>共${commentCount}条评论</span>
                            </div>
                            <button id="favoriteBtn" class="btn btn-secondary btn-small" style="margin-left:16px;">
                                <span id="favoriteIcon" class="icon-heart">♡</span> <span id="favoriteText">收藏</span>
                            </button>
                        </div>
                    </div>
                </div>
            `;
    // 悬停时显示提示
    const imgBox = document.querySelector('.dish-image');
    if (imgBox) {
        imgBox.addEventListener('mouseenter', () => {
            const tip = imgBox.querySelector('.img-tip');
            if (tip) tip.style.opacity = 1;
        });
        imgBox.addEventListener('mouseleave', () => {
            const tip = imgBox.querySelector('.img-tip');
            if (tip) tip.style.opacity = 0;
        });
    }
    // 收藏按钮初始化和事件
    await initFavoriteBtn();
}

// 新增：图片预览弹窗
function showImagePreview(url, alt) {
    let preview = document.getElementById('imagePreviewModal');
    if (!preview) {
        preview = document.createElement('div');
        preview.id = 'imagePreviewModal';
        preview.style.position = 'fixed';
        preview.style.left = 0;
        preview.style.top = 0;
        preview.style.width = '100vw';
        preview.style.height = '100vh';
        preview.style.background = 'rgba(0,0,0,0.7)';
        preview.style.display = 'flex';
        preview.style.alignItems = 'center';
        preview.style.justifyContent = 'center';
        preview.style.zIndex = 9999;
        preview.innerHTML = `<img src="" alt="" style="max-width:90vw;max-height:90vh;border-radius:10px;box-shadow:0 2px 16px #0008;">`;
        preview.addEventListener('click', function () {
            preview.style.display = 'none';
        });
        document.body.appendChild(preview);
    }
    const img = preview.querySelector('img');
    img.src = url;
    img.alt = alt;
    preview.style.display = 'flex';
}

// 加载评论列表
async function loadComments() {
    try {
        const response = await fetch(`/api/comments/dish/${currentDishId}/with-user-info`);
        const comments = await response.json();
        allComments = Array.isArray(comments) ? comments : [];
        displayComments(allComments);
    } catch (error) {
        console.error('加载评论失败:', error);
        document.getElementById('commentsList').innerHTML = '<div class="no-comments">加载评论失败</div>';
    }
}

// 排序评论
function sortComments() {
    const sortType = document.getElementById('sortSelect').value;
    let sortedComments = [...allComments];

    switch (sortType) {
        case 'time-desc':
            sortedComments.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
            break;
        case 'time-asc':
            sortedComments.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
            break;
        case 'rating-desc':
            sortedComments.sort((a, b) => b.rating - a.rating);
            break;
        case 'rating-asc':
            sortedComments.sort((a, b) => a.rating - b.rating);
            break;
    }

    displayComments(sortedComments);
}

// 显示评论列表
function displayComments(comments) {
    const container = document.getElementById('commentsList');

    if (comments.length === 0) {
        container.innerHTML = '<div class="no-comments">暂无评论，快来写第一条评论吧！</div>';
        return;
    }

    const commentsHtml = comments.map(comment => {
        const stars = '★'.repeat(comment.rating) + '☆'.repeat(5 - comment.rating);
        const date = new Date(comment.createdAt).toLocaleString('zh-CN');
        const userName = comment.userName || comment.user?.name || `用户${comment.userId}`;
        const isOwnComment = comment.userId == currentUserId;

        return `
                    <div class="comment-item ${isOwnComment ? 'own-comment' : ''}">
                        <div class="comment-header">
                            <div class="comment-user-info">
                                <div class="comment-user">${userName}</div>
                                <div class="comment-date">${date}</div>
                            </div>
                            <div class="comment-rating">${stars}</div>
                        </div>
                        <div class="comment-content-wrapper">
                            <div class="comment-content">${comment.content}</div>
                            <div class="comment-actions">
                                <button class="btn btn-like btn-small" data-comment-id="${comment.commentId}">
                                    <span class="icon-thumb-up">👍</span> <span class="like-count" id="like-count-${comment.commentId}">0</span>
                                </button>
                                ${isOwnComment ? `<button class="btn btn-danger" onclick="deleteComment(${comment.commentId})">删除评论</button>` : ''}
                            </div>
                        </div>
                    </div>
                `;
    }).join('');

    container.innerHTML = commentsHtml;
    // 初始化点赞状态和数量
    comments.forEach(comment => initLikeBtn(comment.commentId));
}

// 收藏按钮初始化和事件
async function initFavoriteBtn() {
    const btn = document.getElementById('favoriteBtn');
    const icon = document.getElementById('favoriteIcon');
    const text = document.getElementById('favoriteText');
    if (!btn) return;
    if (!currentUserId) {
        btn.onclick = () => { window.location.href = '/login'; };
        return;
    }
    // 查询收藏状态
    const res = await fetch(`/api/favorites/check?dishId=${currentDishId}`);
    const data = await res.json();
    let favorited = data.favorited;
    updateBtn();
    btn.onclick = async function () {
        if (!favorited) {
            const resp = await fetch('/api/favorites', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ dishId: currentDishId })
            });
            if ((await resp.json()).success) favorited = true;
        } else {
            const resp = await fetch(`/api/favorites/${currentDishId}`, { method: 'DELETE' });
            if ((await resp.json()).success) favorited = false;
        }
        updateBtn();
    };
    function updateBtn() {
        if (favorited) {
            icon.textContent = '❤';
            btn.classList.add('active');
            text.textContent = '已收藏';
        } else {
            icon.textContent = '♡';
            btn.classList.remove('active');
            text.textContent = '收藏';
        }
    }
}

// 点赞按钮初始化
async function initLikeBtn(commentId) {
    const btn = document.querySelector(`.btn-like[data-comment-id='${commentId}']`);
    const countSpan = document.getElementById(`like-count-${commentId}`);
    if (!btn || !countSpan) return;
    // 查询点赞数
    const res = await fetch(`/api/likes/count?commentId=${commentId}`);
    const data = await res.json();
    countSpan.textContent = data.count || 0;
    let liked = false;
    if (currentUserId) {
        const res2 = await fetch(`/api/likes/check?commentId=${commentId}`);
        liked = (await res2.json()).liked;
    }
    updateBtn();
    btn.onclick = async function () {
        if (!currentUserId) { window.location.href = '/login'; return; }
        if (!liked) {
            const resp = await fetch('/api/likes', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ commentId })
            });
            if ((await resp.json()).success) {
                liked = true;
                countSpan.textContent = parseInt(countSpan.textContent) + 1;
            }
        } else {
            const resp = await fetch(`/api/likes/${commentId}`, { method: 'DELETE' });
            if ((await resp.json()).success) {
                liked = false;
                countSpan.textContent = Math.max(0, parseInt(countSpan.textContent) - 1);
            }
        }
        updateBtn();
    };
    function updateBtn() {
        if (liked) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    }
}

// 显示评论表单
function showCommentForm() {
    // 检查用户是否已登录
    const userStr = localStorage.getItem('currentUser');
    if (!userStr) {
        alert('请先登录后再写评论');
        window.location.href = '/login';
        return;
    }

    document.getElementById('commentForm').style.display = 'block';
}

// 隐藏评论表单
function hideCommentForm() {
    document.getElementById('commentForm').style.display = 'none';
    document.getElementById('rating').value = '';
    document.getElementById('commentText').value = '';
    // 重置星级评分
    selectedRating = 0;
    highlightStars(0);
    updateRatingText(0);
}

// 提交评论
async function submitComment() {
    const rating = document.getElementById('rating').value;
    const commentText = document.getElementById('commentText').value;

    if (!rating || rating == 0) {
        alert('请选择评分');
        return;
    }

    if (!commentText.trim()) {
        alert('请填写评论内容');
        return;
    }

    if (commentText.trim().length < 10) {
        alert('评论内容至少需要10个字符');
        return;
    }

    const comment = {
        dishId: currentDishId,
        userId: currentUserId,
        rating: parseInt(rating),
        content: commentText.trim()
    };

    try {
        const response = await fetch('/api/comments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(comment)
        });

        const result = await response.json();

        if (result.success) {
            alert('评论提交成功！');
            hideCommentForm();
            loadComments();
            loadDishDetail(); // 重新加载菜品详情以更新评分
        } else {
            alert('评论提交失败：' + result.message);
        }
    } catch (error) {
        console.error('提交评论失败:', error);
        alert('评论提交失败，请重试');
    }
}

// 删除评论
async function deleteComment(commentId) {
    if (!confirm('确定要删除这条评论吗？')) {
        return;
    }

    try {
        const response = await fetch(`/api/comments/${commentId}`, {
            method: 'DELETE'
        });

        const result = await response.json();

        if (result.success) {
            alert('评论删除成功！');
            loadComments();
            loadDishDetail(); // 重新加载菜品详情以更新评分
        } else {
            alert('评论删除失败：' + result.message);
        }
    } catch (error) {
        console.error('删除评论失败:', error);
        alert('删除评论失败，请重试');
    }
}

// 返回上一页
function goBack() {
    window.history.back();
}

// 退出登录
function logout() {
    if (confirm('确定要退出登录吗？')) {
        localStorage.removeItem('currentUser');
        showGuestSection();
        // 刷新页面以更新所有状态
        window.location.reload();
    }
}

// 设置星级评分事件监听器
function setupStarRating() {
    const stars = document.querySelectorAll('.star');
    stars.forEach(star => {
        star.addEventListener('click', function () {
            const rating = parseInt(this.dataset.rating);
            setRating(rating);
        });

        star.addEventListener('mouseenter', function () {
            const rating = parseInt(this.dataset.rating);
            highlightStars(rating);
        });
    });

    // 星级评分容器
    const starContainer = document.getElementById('starRating');
    if (starContainer) {
        starContainer.addEventListener('mouseleave', function () {
            highlightStars(selectedRating);
        });
    }
}

// 设置评分
function setRating(rating) {
    selectedRating = rating;
    document.getElementById('rating').value = rating;
    highlightStars(rating);
    updateRatingText(rating);
}

// 高亮星星
function highlightStars(rating) {
    const stars = document.querySelectorAll('.star');
    stars.forEach((star, index) => {
        if (index < rating) {
            star.textContent = '★';
            star.classList.add('active');
        } else {
            star.textContent = '☆';
            star.classList.remove('active');
        }
    });
}

// 更新评分文字
function updateRatingText(rating) {
    const ratingTexts = {
        1: '非常不满意',
        2: '不满意',
        3: '一般',
        4: '满意',
        5: '非常满意'
    };
    const ratingTextElement = document.getElementById('ratingText');
    if (ratingTextElement) {
        ratingTextElement.textContent = ratingTexts[rating] || '请选择评分';
    }
}