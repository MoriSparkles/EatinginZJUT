let currentPage = 1;
let totalPages = 1;
let currentFilters = {};

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
    loadCampuses();
    loadDishes();
});

// 检查登录状态
function checkLoginStatus() {
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            const user = JSON.parse(userStr);
            document.getElementById('userName').textContent = user.name || '用户';
            document.getElementById('guestButtons').style.display = 'none';
            document.getElementById('userSection').style.display = 'flex';
            document.getElementById('addDishBtn').style.display = 'inline-block';
        } catch (error) {
            console.error('解析用户信息失败:', error);
        }
    } else {
        document.getElementById('guestButtons').style.display = 'flex';
        document.getElementById('userSection').style.display = 'none';
        document.getElementById('addDishBtn').style.display = 'none';
    }
}

// 显示添加菜品模态框
function showAddDishModal() {
    // 检查用户是否已登录
    const userStr = localStorage.getItem('currentUser');
    if (!userStr) {
        alert('请先登录后再添加菜品');
        window.location.href = '/login';
        return;
    }

    document.getElementById('addDishModal').style.display = 'block';
    loadModalCampuses();
    resetAddDishForm();
}

// 关闭添加菜品模态框
function closeAddDishModal() {
    document.getElementById('addDishModal').style.display = 'none';
    resetAddDishForm();
}

// 重置添加菜品表单
function resetAddDishForm() {
    document.getElementById('addDishForm').reset();
    document.getElementById('successMessage').style.display = 'none';
    document.getElementById('errorMessage').style.display = 'none';
}

// 加载模态框中的校区选项
async function loadModalCampuses() {
    try {
        const response = await fetch('/api/dishes/campuses');
        const campuses = await response.json();

        const campusSelect = document.getElementById('modalCampus');
        campusSelect.innerHTML = '<option value="">请选择校区</option>';

        campuses.forEach(campus => {
            const option = document.createElement('option');
            option.value = campus;
            option.textContent = campus;
            campusSelect.appendChild(option);
        });
    } catch (error) {
        console.error('加载校区失败:', error);
    }
}

// 加载模态框中的食堂选项
async function loadModalCanteens() {
    const campus = document.getElementById('modalCampus').value;
    if (!campus) {
        document.getElementById('modalCanteen').innerHTML = '<option value="">请选择食堂</option>';
        return;
    }

    try {
        const response = await fetch(`/api/dishes/canteens?campus=${encodeURIComponent(campus)}`);
        const canteens = await response.json();

        const canteenSelect = document.getElementById('modalCanteen');
        canteenSelect.innerHTML = '<option value="">请选择食堂</option>';

        canteens.forEach(canteen => {
            const option = document.createElement('option');
            option.value = canteen;
            option.textContent = canteen;
            canteenSelect.appendChild(option);
        });
    } catch (error) {
        console.error('加载食堂失败:', error);
    }
}

// 添加菜品表单提交
document.getElementById('addDishForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const dishData = {
        dishName: formData.get('dishName'),
        price: formData.get('price'),
        campus: formData.get('campus'),
        canteen: formData.get('canteen'),
        stall: formData.get('stall'),
        imageUrl: formData.get('imageUrl'),
        description: formData.get('description')
    };

    try {
        const response = await fetch('/api/dishes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dishData)
        });

        const data = await response.json();

        if (data.success) {
            showSuccess('菜品添加成功！');
            closeAddDishModal();
            loadDishes(); // 重新加载菜品列表
        } else {
            showError(data.message || '添加失败');
        }
    } catch (error) {
        console.error('添加菜品失败:', error);
        showError('网络错误，请重试');
    }
});

// 加载校区选项
async function loadCampuses() {
    try {
        const response = await fetch('/api/dishes/campuses');
        const campuses = await response.json();

        const campusSelect = document.getElementById('campus');
        campusSelect.innerHTML = '<option value="">全部校区</option>';

        campuses.forEach(campus => {
            const option = document.createElement('option');
            option.value = campus;
            option.textContent = campus;
            campusSelect.appendChild(option);
        });
    } catch (error) {
        console.error('加载校区失败:', error);
    }
}

// 加载食堂选项
async function loadCanteens() {
    const campus = document.getElementById('campus').value;
    if (!campus) {
        document.getElementById('canteen').innerHTML = '<option value="">全部食堂</option>';
        document.getElementById('stall').innerHTML = '<option value="">全部档口</option>';
        return;
    }

    try {
        const response = await fetch(`/api/dishes/canteens?campus=${encodeURIComponent(campus)}`);
        const canteens = await response.json();

        const canteenSelect = document.getElementById('canteen');
        canteenSelect.innerHTML = '<option value="">全部食堂</option>';

        canteens.forEach(canteen => {
            const option = document.createElement('option');
            option.value = canteen;
            option.textContent = canteen;
            canteenSelect.appendChild(option);
        });

        // 清空档口选择
        document.getElementById('stall').innerHTML = '<option value="">全部档口</option>';
    } catch (error) {
        console.error('加载食堂失败:', error);
    }
}

// 加载档口选项
async function loadStalls() {
    const campus = document.getElementById('campus').value;
    const canteen = document.getElementById('canteen').value;

    if (!campus || !canteen) {
        document.getElementById('stall').innerHTML = '<option value="">全部档口</option>';
        return;
    }

    try {
        const response = await fetch(`/api/dishes/stalls?campus=${encodeURIComponent(campus)}&canteen=${encodeURIComponent(canteen)}`);
        const stalls = await response.json();

        const stallSelect = document.getElementById('stall');
        stallSelect.innerHTML = '<option value="">全部档口</option>';

        stalls.forEach(stall => {
            const option = document.createElement('option');
            option.value = stall;
            option.textContent = stall;
            stallSelect.appendChild(option);
        });
    } catch (error) {
        console.error('加载档口失败:', error);
    }
}

// 搜索菜品
function searchDishes() {
    currentPage = 1;
    loadDishes();
}

// 重置筛选条件
function resetFilters() {
    document.getElementById('campus').value = '';
    document.getElementById('canteen').value = '';
    document.getElementById('stall').value = '';
    document.getElementById('searchInput').value = '';
    currentPage = 1;
    loadDishes();
}

// 加载菜品列表
async function loadDishes() {
    const campus = document.getElementById('campus').value;
    const canteen = document.getElementById('canteen').value;
    const stall = document.getElementById('stall').value;
    const searchInput = document.getElementById('searchInput').value;

    // 构建查询参数
    const params = new URLSearchParams();
    if (campus) params.append('campus', campus);
    if (canteen) params.append('canteen', canteen);
    if (stall) params.append('stall', stall);
    if (searchInput) params.append('search', searchInput);
    params.append('page', currentPage);
    params.append('size', 12);

    try {
        const response = await fetch(`/api/dishes?${params.toString()}`);
        const data = await response.json();

        if (data.content && data.content.length > 0) {
            displayDishes(data.content);
            displayPagination(data.totalPages, data.currentPage);
        } else {
            document.getElementById('dishesContainer').innerHTML =
                '<div class="no-dishes">没有找到符合条件的菜品</div>';
            document.getElementById('pagination').style.display = 'none';
        }
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
                <div class="dish-image">
                    ${dish.imageUrl ? `<img src="${dish.imageUrl}" alt="${dish.dishName}">` : '🍽️'}
                </div>
                <div class="dish-content">
                    <div class="dish-title">${dish.dishName}</div>
                    <div class="dish-info">
                        <div class="dish-location">
                            <span>${dish.campus}</span>
                            <span>${dish.canteen}</span>
                            <span>${dish.stall}</span>
                        </div>
                        <div>价格：${dish.price || '暂无'}</div>
                        ${dish.description ? `<div>${dish.description}</div>` : ''}
                    </div>
                    <div class="dish-rating">
                        <div class="stars">${'★'.repeat(Math.round(rating))}${'☆'.repeat(5 - Math.round(rating))}</div>
                        <div class="rating-text">${rating.toFixed(1)}分 (${commentCount}条评论)</div>
                    </div>
                    <div class="dish-actions">
                        <a href="/dish-detail?id=${dish.dishId}" class="btn btn-primary btn-small">查看详情</a>
                        <button class="btn btn-secondary btn-small favorite-btn" data-dish-id="${dish.dishId}" style="margin-left:8px;">
                            <span class="icon-heart">♡</span> <span class="favorite-text">收藏</span>
                        </button>
                    </div>
                </div>
            `;
    // 新增：图片点击放大预览
    const img = card.querySelector('.dish-image img');
    if (img) {
        img.addEventListener('click', function (e) {
            showImagePreview(dish.imageUrl, dish.dishName);
        });
    }
    // 新增：收藏按钮初始化
    const favBtn = card.querySelector('.favorite-btn');
    if (favBtn) initFavoriteBtnList(favBtn, dish.dishId);
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

// 显示分页
function displayPagination(totalPages, currentPageFromAPI) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';
    pagination.style.display = 'flex';

    // 更新全局变量
    totalPages = totalPages;
    currentPage = currentPageFromAPI;

    // 上一页按钮
    const prevBtn = document.createElement('button');
    prevBtn.textContent = '上一页';
    prevBtn.disabled = currentPage <= 1;
    prevBtn.onclick = () => {
        if (currentPage > 1) {
            currentPage--;
            loadDishes();
        }
    };
    pagination.appendChild(prevBtn);

    // 页码按钮
    for (let i = 1; i <= totalPages; i++) {
        const pageBtn = document.createElement('button');
        pageBtn.textContent = i;
        pageBtn.className = i === currentPage ? 'active' : '';
        pageBtn.onclick = () => {
            currentPage = i;
            loadDishes();
        };
        pagination.appendChild(pageBtn);
    }

    // 下一页按钮
    const nextBtn = document.createElement('button');
    nextBtn.textContent = '下一页';
    nextBtn.disabled = currentPage >= totalPages;
    nextBtn.onclick = () => {
        if (currentPage < totalPages) {
            currentPage++;
            loadDishes();
        }
    };
    pagination.appendChild(nextBtn);
}

// 获取当前用户
function getCurrentUser() {
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            return JSON.parse(userStr);
        } catch (error) {
            console.error('解析用户信息失败:', error);
            return null;
        }
    }
    return null;
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

// 列表页收藏按钮初始化
async function initFavoriteBtnList(btn, dishId) {
    const icon = btn.querySelector('.icon-heart');
    const text = btn.querySelector('.favorite-text');
    const userStr = localStorage.getItem('currentUser');
    let userId = null;
    if (userStr) {
        try { userId = JSON.parse(userStr).userId || JSON.parse(userStr).id; } catch { userId = null; }
    }
    if (!userId) {
        btn.onclick = () => { window.location.href = '/login'; };
        return;
    }
    // 查询收藏状态
    const res = await fetch(`/api/favorites/check?dishId=${dishId}`);
    const data = await res.json();
    let favorited = data.favorited;
    updateBtn();
    btn.onclick = async function () {
        if (!favorited) {
            const resp = await fetch('/api/favorites', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ dishId })
            });
            if ((await resp.json()).success) favorited = true;
        } else {
            const resp = await fetch(`/api/favorites/${dishId}`, { method: 'DELETE' });
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