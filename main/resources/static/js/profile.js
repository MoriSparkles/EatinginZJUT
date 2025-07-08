let currentUser = null;
let originalFormData = {};

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
    loadUserProfile();
    loadUserStats();
    loadRecentActivity();
    loadFavoriteList(); // 新增
    loadLikeList();     // 新增
});

// 检查登录状态
function checkLoginStatus() {
    const userStr = localStorage.getItem('currentUser');
    if (!userStr) {
        // 尝试从服务器获取当前用户信息
        fetchCurrentUserFromServer();
        return;
    }
}

// 从服务器获取当前用户信息
async function fetchCurrentUserFromServer() {
    try {
        const response = await fetch('/api/users/current');
        const result = await response.json();

        if (result.success && result.user) {
            // 保存到localStorage
            localStorage.setItem('currentUser', JSON.stringify(result.user));
            displayUserProfile(result.user);
        } else {
            alert('请先登录');
            window.location.href = '/login';
        }
    } catch (error) {
        console.error('获取用户信息失败:', error);
        alert('请先登录');
        window.location.href = '/login';
    }
}

// 加载用户信息
async function loadUserProfile() {
    try {
        const userStr = localStorage.getItem('currentUser');
        if (!userStr) {
            await fetchCurrentUserFromServer();
            return;
        }

        const user = JSON.parse(userStr);
        if (user) {
            displayUserProfile(user);
        }
    } catch (error) {
        console.error('加载用户信息失败:', error);
        showError('加载用户信息失败');
    }
}

// 显示用户信息
function displayUserProfile(user) {
    // 更新页面上的用户名显示
    const userNameElements = document.querySelectorAll('#profileUserName');
    userNameElements.forEach(element => {
        element.textContent = user.name || '未知用户';
    });

    // 更新导航栏用户名
    const navUserNameElement = document.getElementById('userName');
    if (navUserNameElement) {
        navUserNameElement.textContent = user.name || '未知用户';
    }

    // 填充表单字段
    const nameInput = document.getElementById('name');
    const phoneInput = document.getElementById('phone');

    if (nameInput) nameInput.value = user.name || '';
    if (phoneInput) phoneInput.value = user.phone || '';

    // 保存原始数据
    originalFormData = {
        name: user.name || '',
        phone: user.phone || '',
    };

    // 更新导航栏显示
    updateNavigationBar(user);
}

// 更新导航栏显示
function updateNavigationBar(user) {
    const guestButtons = document.getElementById('guestButtons');
    const userSection = document.getElementById('userSection');

    if (user && user.name) {
        if (guestButtons) guestButtons.style.display = 'none';
        if (userSection) userSection.style.display = 'flex';
    } else {
        if (guestButtons) guestButtons.style.display = 'flex';
        if (userSection) userSection.style.display = 'none';
    }
}

// 加载用户统计信息
async function loadUserStats() {
    try {
        const userId = getCurrentUserId();
        const response = await fetch(`/api/comments/user/${userId}/with-dish-info`);
        const comments = await response.json();

        const totalComments = comments.length;
        const averageRating = totalComments > 0
            ? (comments.reduce((sum, comment) => sum + comment.rating, 0) / totalComments).toFixed(1)
            : '0.0';

        // 计算注册天数（这里假设用户注册时间为当前时间减去30天）
        const joinDays = 30; // 实际应该从用户注册时间计算

        document.getElementById('totalComments').textContent = totalComments;
        document.getElementById('averageRating').textContent = averageRating;
        document.getElementById('joinDays').textContent = joinDays;
    } catch (error) {
        console.error('加载统计信息失败:', error);
    }
}

// 加载最近活动
async function loadRecentActivity() {
    try {
        const userId = getCurrentUserId();
        const response = await fetch(`/api/comments/user/${userId}/with-dish-info`);
        const comments = await response.json();

        // 取最近的10条评论作为活动
        const recentComments = comments.slice(0, 10);

        const activityHtml = recentComments.map(comment => {
            const date = new Date(comment.createdAt).toLocaleString('zh-CN');
            const stars = '★'.repeat(comment.rating) + '☆'.repeat(5 - comment.rating);
            return `
                        <div class="activity-item">
                            <div class="activity-icon">💬</div>
                            <div class="activity-content">
                                <div class="activity-header">
                                    <div class="activity-title">评论了 ${comment.dishName || '未知菜品'}</div>
                                    <div class="activity-rating">${stars}</div>
                                </div>
                                <div class="activity-comment">${comment.content || '暂无评论内容'}</div>
                                <div class="activity-footer">
                                    <div class="activity-time">${date}</div>
                                    <a href="/dish-detail?id=${comment.dishId}" class="btn btn-primary btn-small">查看菜品</a>
                                </div>
                            </div>
                        </div>
                    `;
        }).join('');

        document.getElementById('recentActivity').innerHTML =
            activityHtml || '<div class="no-activity">暂无评论记录</div>';
    } catch (error) {
        console.error('加载活动记录失败:', error);
        document.getElementById('recentActivity').innerHTML = '<div class="loading">加载失败</div>';
    }
}

// 加载我的收藏
async function loadFavoriteList() {
    try {
        const response = await fetch('/api/favorites');
        const favorites = await response.json();
        let html = '';
        if (Array.isArray(favorites) && favorites.length > 0) {
            html = favorites.map(fav => `
                        <div class="favorite-item-card">
                            <div class="activity-icon">❤️</div>
                            <div class="activity-content">
                                <div class="activity-header">
                                    <div class="activity-title">收藏了 ${fav.dishName || '未知菜品'}</div>
                                </div>
                                <div class="activity-comment">${fav.campus || ''} ${fav.canteen || ''}</div>
                                <div class="activity-footer">
                                    <div class="activity-time">${fav.collectedAt ? `${new Date(fav.collectedAt).toLocaleString('zh-CN')}` : ''}</div>
                                    <a href="/dish-detail?id=${fav.dishId}" class="btn btn-primary btn-small">查看详情</a>
                                </div>
                            </div>
                        </div>
                    `).join('');
        } else {
            html = '<div class="no-activity">暂无收藏</div>';
        }
        document.getElementById('favoriteList').innerHTML = html;
    } catch (error) {
        document.getElementById('favoriteList').innerHTML = '<div class="loading">加载失败</div>';
    }
}
// 加载我的点赞
async function loadLikeList() {
    try {
        const response = await fetch('/api/likes?withCommentInfo=true');
        const likes = await response.json();
        let html = '';
        if (Array.isArray(likes) && likes.length > 0) {
            html = likes.map(like => `
                        <div class="like-item-card">
                            <div class="activity-content">
                                <div class="activity-header">
                                    <span class="activity-icon">👍</span>
                                    <span class="activity-title">点赞了 ${like.dishName ? like.dishName : '未知菜品'}</span>
                                </div>
                                <div class="activity-comment">${like.commentContent || '无内容'}</div>
                                <div class="activity-footer">
                                    <div class="activity-time">${like.createdAt ? `${new Date(like.createdAt).toLocaleString('zh-CN')}` : ''}</div>
                                    <a href="/dish-detail?id=${like.dishId}" class="btn btn-primary btn-small">查看菜品</a>
                                </div>
                            </div>
                        </div>
                    `).join('');
        } else {
            html = '<div class="no-activity">暂无点赞</div>';
        }
        document.getElementById('likeList').innerHTML = html;
    } catch (error) {
        document.getElementById('likeList').innerHTML = '<div class="loading">加载失败</div>';
    }
}

// 获取当前用户ID
function getCurrentUserId() {
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            const user = JSON.parse(userStr);
            return user.userId || user.id || 1;
        } catch (error) {
            console.error('解析用户信息失败:', error);
        }
    }
    return 1;
}

// 提交个人信息表单
document.getElementById('profileForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const formData = {
        name: document.getElementById('name').value,
        phone: document.getElementById('phone').value,
    };

    try {
        const userId = getCurrentUserId();
        const response = await fetch(`/api/users/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        const result = await response.json();

        if (result.success) {
            showSuccess('个人信息更新成功！');
            originalFormData = { ...formData };
        } else {
            showError('更新失败：' + result.message);
        }
    } catch (error) {
        console.error('更新个人信息失败:', error);
        showError('更新失败，请重试');
    }
});

// 提交密码修改表单
document.getElementById('passwordForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordMessage = document.getElementById('passwordMessage');

    passwordMessage.textContent = '';
    passwordMessage.style.display = 'none';

    if (newPassword !== confirmPassword) {
        passwordMessage.textContent = '两次输入的新密码不一致';
        passwordMessage.style.display = 'block';
        passwordMessage.style.color = 'red';
        return;
    }

    if (newPassword.length < 6) {
        passwordMessage.textContent = '新密码长度不能少于6位';
        passwordMessage.style.display = 'block';
        passwordMessage.style.color = 'red';
        return;
    }

    try {
        const userId = getCurrentUserId();
        const response = await fetch(`/api/users/${userId}/password`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ currentPassword, newPassword })
        });
        const result = await response.json();
        if (result.success) {
            passwordMessage.textContent = '密码修改成功！';
            passwordMessage.style.display = 'block';
            passwordMessage.style.color = 'green';
            setTimeout(() => {
                closePasswordModal();
                passwordMessage.textContent = '';
                passwordMessage.style.display = 'none';
                loadUserProfile();
            }, 2000);
        } else {
            passwordMessage.textContent = result.message || '修改密码失败';
            passwordMessage.style.display = 'block';
            passwordMessage.style.color = 'red';
        }
    } catch (error) {
        console.error('修改密码失败:', error);
        passwordMessage.textContent = '修改密码失败，请重试';
        passwordMessage.style.display = 'block';
        passwordMessage.style.color = 'red';
    }
});

// 重置表单
function resetForm() {
    document.getElementById('name').value = originalFormData.name;
    document.getElementById('phone').value = originalFormData.phone;
}

// 显示密码修改模态框
function showPasswordModal() {
    document.getElementById('passwordModal').style.display = 'block';
    document.getElementById('passwordForm').reset();
}

// 关闭密码修改模态框
function closePasswordModal() {
    document.getElementById('passwordModal').style.display = 'none';
    document.getElementById('passwordForm').reset();
}

// 点击模态框外部关闭
window.onclick = function (event) {
    const modal = document.getElementById('passwordModal');
    if (event.target === modal) {
        closePasswordModal();
    }
}

// 显示成功消息
function showSuccess(message) {
    const successDiv = document.getElementById('successMessage');
    successDiv.textContent = message;
    successDiv.style.display = 'block';

    setTimeout(() => {
        successDiv.style.display = 'none';
    }, 3000);
}

// 显示错误消息
function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';

    setTimeout(() => {
        errorDiv.style.display = 'none';
    }, 3000);
}

// 退出登录
async function logout() {
    if (confirm('确定要退出登录吗？')) {
        try {
            // 调用服务器退出登录API
            const response = await fetch('/api/users/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const result = await response.json();
            if (result.success) {
                // 清除本地存储
                localStorage.removeItem('currentUser');
                // 跳转到登录页
                window.location.href = '/login';
            } else {
                alert('退出登录失败：' + result.message);
            }
        } catch (error) {
            console.error('退出登录失败:', error);
            // 即使API调用失败，也清除本地存储并跳转
            localStorage.removeItem('currentUser');
            window.location.href = '/login';
        }
    }
}

// Tab切换逻辑
function showTab(tab) {
    const tabs = ['comments', 'favorites', 'likes'];
    tabs.forEach(t => {
        const panel = document.getElementById('tabPanel-' + t);
        const btn = document.getElementById('tab-' + t);
        if (panel) panel.classList.toggle('active', t === tab);
        if (btn) btn.classList.toggle('active', t === tab);
    });
}