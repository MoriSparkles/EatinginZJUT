// 页面加载时初始化
document.addEventListener('DOMContentLoaded', function () {
    loadSystemStatus();
    updateSystemStats();
});

// 加载系统状态
async function loadSystemStatus() {
    try {
        // 模拟API调用获取系统状态
        const status = await fetchSystemStatus();
        updateStatusDisplay(status);
    } catch (error) {
        console.error('加载系统状态失败:', error);
        showErrorStatus();
    }
}

// 模拟获取系统状态
async function fetchSystemStatus() {
    // 这里应该是真实的API调用
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                webServer: 'running',
                database: 'connected',
                cache: 'normal',
                storage: 'normal',
                cpu: Math.floor(Math.random() * 50) + 10,
                memory: Math.floor(Math.random() * 60) + 20,
                disk: Math.floor(Math.random() * 40) + 20,
                network: Math.floor(Math.random() * 30) + 10
            });
        }, 500);
    });
}

// 更新状态显示
function updateStatusDisplay(status) {
    document.getElementById('cpuUsage').textContent = status.cpu + '%';
    document.getElementById('memoryUsage').textContent = status.memory + '%';
    document.getElementById('diskUsage').textContent = status.disk + '%';
    document.getElementById('networkLatency').textContent = status.network + 'ms';
}

// 显示错误状态
function showErrorStatus() {
    document.getElementById('webServerStatus').textContent = '错误';
    document.getElementById('webServerStatus').className = 'status-value status-error';
}

// 更新系统统计
async function updateSystemStats() {
    try {
        // 这里应该调用真实的API获取统计数据
        const stats = await fetchSystemStats();
        document.getElementById('totalUsers').textContent = stats.users.toLocaleString();
        document.getElementById('totalDishes').textContent = stats.dishes.toLocaleString();
        document.getElementById('totalComments').textContent = stats.comments.toLocaleString();
        document.getElementById('todayVisits').textContent = stats.visits.toLocaleString();
    } catch (error) {
        console.error('更新系统统计失败:', error);
    }
}

// 模拟获取系统统计
async function fetchSystemStats() {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                users: Math.floor(Math.random() * 2000) + 1000,
                dishes: Math.floor(Math.random() * 800) + 500,
                comments: Math.floor(Math.random() * 10000) + 8000,
                visits: Math.floor(Math.random() * 200) + 100
            });
        }, 300);
    });
}

// 刷新状态
function refreshStatus() {
    loadSystemStatus();
    updateSystemStats();
}

// 退出登录
function logout() {
    if (confirm('确定要退出登录吗？')) {
        localStorage.removeItem('currentUser');
        window.location.href = '/login';
    }
}