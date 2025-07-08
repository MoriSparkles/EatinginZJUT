// 当前登录用户信息（从localStorage获取）
let currentUser = null;
// 当前tab（random/ai）
let currentTab = 'random';

// 页面加载时初始化：检查登录状态、默认tab
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
    switchTab(currentTab);
});

// 检查登录状态，动态切换导航栏显示内容
function checkLoginStatus() {
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
        showGuestSection();
    }
}

// 显示游客状态（未登录）
function showGuestSection() {
    document.getElementById('guestButtons').style.display = 'flex';
    document.getElementById('userSection').style.display = 'none';
}

// 退出登录，清除本地用户信息并刷新页面
function logout() {
    if (confirm('确定要退出登录吗？')) {
        localStorage.removeItem('currentUser');
        showGuestSection();
        window.location.reload();
    }
}

// 切换tab，控制不同推荐模块的显示与样式
function switchTab(tab) {
    currentTab = tab;
    document.getElementById('randomModule').style.display = tab === 'random' ? 'block' : 'none';
    document.getElementById('aiModule').style.display = tab === 'ai' ? 'block' : 'none';
    document.getElementById('tabRandom').className = tab === 'random' ? 'tab-btn active' : 'tab-btn';
    document.getElementById('tabAI').className = tab === 'ai' ? 'tab-btn active' : 'tab-btn';
    // 清空结果区
    document.getElementById('resultContainer').innerHTML = '';
}

// 随机生成菜品：调用后端接口获取随机菜品，渲染卡片
async function generateRandomDish() {
    const resultContainer = document.getElementById('resultContainer');
    resultContainer.innerHTML = '<div class="loading">正在为您随机选择...</div>';
    try {
        const response = await fetch('/api/dishes/random');
        const data = await response.json();
        if (data.success && data.dish) {
            displayRandomDish(data.dish);
        } else {
            resultContainer.innerHTML = '<div class="error-message">获取随机菜品失败，请重试</div>';
        }
    } catch (error) {
        resultContainer.innerHTML = '<div class="error-message">网络错误，请重试</div>';
    }
}

// 显示随机菜品卡片，包含图片、评分、评论数、跳转详情等
function displayRandomDish(dish) {
    const rating = dish.averageRating || 0;
    const commentCount = dish.commentCount || 0;
    const stars = '★'.repeat(Math.round(rating)) + '☆'.repeat(5 - Math.round(rating));
    let priceText = dish.price;
    document.getElementById('resultContainer').innerHTML = `
                <div style="display:flex;justify-content:center;align-items:center;width:100%;height:100%;min-height:400px;">
                    <div class="dish-card-flex" style="display:flex;align-items:center;justify-content:center;gap:40px;min-height:250px;height:320px;width:80%;max-width:900px;">
                        <div class="dish-image" style="width:250px;height:250px;min-width:250px;max-width:250px;aspect-ratio:1/1;box-shadow:0 4px 16px rgba(60,80,120,0.10);border-radius:18px;overflow:hidden;background:linear-gradient(135deg,#f8fafc 0%,#e0e7ef 100%);display:flex;align-items:center;justify-content:center;position:relative;cursor:zoom-in;">
                            ${dish.imageUrl ? `<img src="${dish.imageUrl}" alt="${dish.dishName}" style="width:100%;height:100%;object-fit:cover;border-radius:18px;cursor:zoom-in;" onclick="showImagePreview('${dish.imageUrl.replace(/'/g, '\'')}', '${dish.dishName.replace(/'/g, '\'')}')">` : '🍽️'}
                            <span style="position:absolute;left:0;right:0;bottom:8px;color:#fff;background:rgba(0,0,0,0.35);font-size:13px;text-align:center;opacity:0;pointer-events:none;transition:opacity 0.2s;padding:2px 0;" class="img-tip">点击查看大图</span>
                        </div>
                        <div class="dish-info" style="flex:0 0 60%;max-width:60%;min-width:260px;display:flex;flex-direction:column;gap:14px;justify-content:center;align-items:center;height:100%;">
                            <div class="dish-title" style="font-size:26px;font-weight:600;color:#333;text-align:center;">${dish.dishName}</div>
                            <div class="dish-meta" style="display:flex;gap:16px;flex-wrap:wrap;justify-content:center;">
                                <span style="background:#e9ecef;padding:7px 18px;border-radius:10px;font-size:16px;">${dish.campus}</span>
                                <span style="background:#e9ecef;padding:7px 18px;border-radius:10px;font-size:16px;">${dish.canteen}</span>
                                <span style="background:#e9ecef;padding:7px 18px;border-radius:10px;font-size:16px;">${dish.stall}</span>
                            </div>
                            <div class="dish-price" style="color:#28a745;font-size:22px;font-weight:600;text-align:center;">${priceText}</div>
                            <div style="display:flex;align-items:center;gap:22px;white-space:nowrap;justify-content:center;">
                                <span class="stars" style="color:#ffc107;font-size:26px;display:flex;align-items:center;">${stars}</span>
                                <span class="rating-text" style="font-size:19px;color:#333;font-weight:600;display:flex;align-items:center;">${rating.toFixed(1)}分</span>
                                <span style="color:#6c757d;font-size:17px;display:flex;align-items:center;">${commentCount}条评论</span>
                            </div>
                            <a href="/dish-detail?id=${dish.dishId}" class="btn btn-primary btn-sm" style="margin-top:12px;align-self:center;">查看详情</a>
                        </div>
                    </div>
                </div>
            `;
    // 悬停时显示图片预览提示
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
}

// AI智能推荐：将用户输入发送给后端AI接口，渲染文本结果
async function aiRecommend() {
    const input = document.getElementById('aiInput').value.trim();
    const aiMessage = document.getElementById('aiMessage');
    const resultContainer = document.getElementById('resultContainer');
    aiMessage.textContent = '';
    resultContainer.innerHTML = '';
    if (!input) {
        aiMessage.textContent = '请输入你的需求，如"想吃辣的"';
        aiMessage.style.color = 'red';
        return;
    }
    aiMessage.textContent = 'AI正在为你推荐，请稍候...';
    aiMessage.style.color = '#333';
    try {
        const response = await fetch('/api/ai/simple', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ prompt: input })
        });
        const result = await response.json();
        if (result.success && result.data) {
            resultContainer.innerHTML = `<div class='result-header' style='margin-bottom:10px;'><h3 style='font-size:16px;'>AI为你推荐</h3></div><div class='ai-text-result'>${result.data.replace(/\n/g, '<br>')}</div>`;
            aiMessage.textContent = '';
        } else {
            aiMessage.textContent = result.message || '未能获得AI推荐，请换个描述试试~';
            aiMessage.style.color = 'red';
        }
    } catch (error) {
        aiMessage.textContent = 'AI推荐失败，请稍后重试';
        aiMessage.style.color = 'red';
    }
}

// 大图预览方法：点击菜品图片弹出大图，点击遮罩关闭
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