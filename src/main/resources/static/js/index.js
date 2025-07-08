let currentUser = null;

// 页面加载时检查登录状态
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
    loadPopularDishes();
});

// 检查登录状态
async function checkLoginStatus() {
    // 首先检查localStorage
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            currentUser = JSON.parse(userStr);
            showUserSection();
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
                showUserSection();
            } else {
                showGuestSection();
            }
        } catch (error) {
            console.error('获取用户信息失败:', error);
            showGuestSection();
        }
    }
}

// 显示用户已登录状态
function showUserSection() {
    document.getElementById('guestButtons').style.display = 'none';
    document.getElementById('userSection').style.display = 'flex';
    document.getElementById('userName').textContent = currentUser.name || '用户';
    if (currentUser.userId === 1) {
        document.getElementById('adminMenu').style.display = 'flex';
    } else {
        document.getElementById('adminMenu').style.display = 'none';
    }
}

// 显示游客状态
function showGuestSection() {
    document.getElementById('guestButtons').style.display = 'flex';
    document.getElementById('userSection').style.display = 'none';
}

// 退出登录
async function logout() {
    if (confirm('确定要退出登录吗？')) {
        try {
            // 调用退出登录API
            await fetch('/api/users/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
        } catch (error) {
            console.error('退出登录API调用失败:', error);
        }

        // 清除本地存储
        localStorage.removeItem('currentUser');
        currentUser = null;
        showGuestSection();

        // 刷新页面以更新所有状态
        window.location.reload();
    }
}

// 加载热门菜品
async function loadPopularDishes() {
    try {
        const response = await fetch('/api/dishes?page=1&size=6');
        const data = await response.json();

        if (!data.content || data.content.length === 0) {
            document.getElementById('dishesContainer').innerHTML =
                '<div class="no-dishes">暂无菜品信息</div>';
            return;
        }

        displayDishes(data.content);
    } catch (error) {
        console.error('加载菜品失败:', error);
        document.getElementById('dishesContainer').innerHTML =
            '<div class="no-dishes">加载失败，请重试</div>';
    }
}

// 显示菜品列表
function displayDishes(dishes) {
    const container = document.getElementById('dishesContainer');
    const grid = document.createElement('div');
    grid.className = 'dishes-grid';

    dishes.forEach(dish => {
        const card = createDishCard(dish);
        grid.appendChild(card);
    });

    container.innerHTML = '';
    container.appendChild(grid);
}

// 创建菜品卡片
function createDishCard(dish) {
    const card = document.createElement('div');
    card.className = 'dish-card';

    const rating = dish.averageRating || 0;
    const commentCount = dish.commentCount || 0;

    card.innerHTML = `
                <div class="dish-image" style="cursor:zoom-in;position:relative;">
                    ${dish.imageUrl ? `<img src="${dish.imageUrl}" alt="${dish.dishName}" onclick="showImagePreview('${dish.imageUrl.replace(/'/g, '\'')}', '${dish.dishName.replace(/'/g, '\'')}')">` : '🍽️'}
                    <span style="position:absolute;left:0;right:0;bottom:8px;color:#fff;background:rgba(0,0,0,0.35);font-size:13px;text-align:center;opacity:0;pointer-events:none;transition:opacity 0.2s;padding:2px 0;" class="img-tip">点击查看大图</span>
                </div>
                <div class="dish-content">
                    <div class="dish-title">${dish.dishName}</div>
                    <div class="dish-info">
                        <div>${dish.campus} - ${dish.canteen}</div>
                        <div>${dish.stall} | ￥${dish.price}</div>
                    </div>
                    <div class="dish-rating">
                        <div class="stars">${'★'.repeat(Math.round(rating))}${'☆'.repeat(5 - Math.round(rating))}</div>
                        <div class="rating-text">${rating.toFixed(1)}分 (${commentCount}条评论)</div>
                    </div>
                    <div class="dish-actions">
                        <a href="/dish-detail?id=${dish.dishId}" class="btn btn-primary btn-small">查看详情</a>
                    </div>
                </div>
            `;
    // 悬停提示
    const imgBox = card.querySelector('.dish-image');
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
    // 图片点击放大
    const img = card.querySelector('.dish-image img');
    if (img) {
        img.addEventListener('click', function (e) {
            showImagePreview(dish.imageUrl, dish.dishName);
        });
    }
    return card;
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