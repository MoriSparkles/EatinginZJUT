// 检查登录状态
document.addEventListener('DOMContentLoaded', function () {
    checkLoginStatus();
    document.getElementById('username').focus();
});

// 检查登录状态
async function checkLoginStatus() {
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
        try {
            const user = JSON.parse(userStr);
            document.getElementById('userName').textContent = user.name || '用户';
            document.getElementById('guestButtons').style.display = 'none';
            document.getElementById('userSection').style.display = 'flex';
        } catch (error) {
            console.error('解析用户信息失败:', error);
            localStorage.removeItem('currentUser');
        }
    } else {
        document.getElementById('guestButtons').style.display = 'flex';
        document.getElementById('userSection').style.display = 'none';
    }
}

// 退出登录
async function logout() {
    if (confirm('确定要退出登录吗？')) {
        try {
            await fetch('/api/users/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include'
            });
        } catch (error) {
            console.error('退出登录API调用失败:', error);
        }

        localStorage.removeItem('currentUser');
        window.location.reload();
    }
}

function setLoginType(type) {
    document.getElementById('loginType').value = type;
    if (type === 'username') {
        document.getElementById('tabUsername').classList.add('active');
        document.getElementById('tabPhone').classList.remove('active');
        document.getElementById('usernameGroup').style.display = '';
        document.getElementById('phoneGroup').style.display = 'none';
    } else {
        document.getElementById('tabUsername').classList.remove('active');
        document.getElementById('tabPhone').classList.add('active');
        document.getElementById('usernameGroup').style.display = 'none';
        document.getElementById('phoneGroup').style.display = '';
    }
}

document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    const loginType = document.getElementById('loginType').value;
    const username = document.getElementById('username').value;
    const phone = document.getElementById('phone').value;
    const password = document.getElementById('password').value;
    const loginBtn = document.getElementById('loginBtn');
    const loginBtnText = document.getElementById('loginBtnText');
    // 显示加载状态
    loginBtn.disabled = true;
    loginBtnText.innerHTML = '<span class="loading"></span>登录中...';
    // 隐藏之前的消息
    hideMessages();
    try {
        let body = { password };
        if (loginType === 'phone') {
            if (!/^\d{11}$/.test(phone)) {
                showError('手机号必须为11位数字');
                loginBtn.disabled = false;
                loginBtnText.textContent = '登录';
                return;
            }
            body.phone = phone;
        } else {
            if (!username) {
                showError('请输入用户名');
                loginBtn.disabled = false;
                loginBtnText.textContent = '登录';
                return;
            }
            body.username = username;
        }
        const response = await fetch('/api/users/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
            credentials: 'include'
        });
        const data = await response.json();
        if (data.success) {
            showSuccess('登录成功！正在跳转...');
            localStorage.setItem('currentUser', JSON.stringify(data.user));
            setTimeout(() => { window.location.href = '/'; }, 1000);
        } else {
            showError(data.message || '登录失败，请检查用户名/手机号和密码');
        }
    } catch (error) {
        console.error('登录请求失败:', error);
        showError('网络错误，请重试');
    } finally {
        loginBtn.disabled = false;
        loginBtnText.textContent = '登录';
    }
});

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}

function showSuccess(message) {
    const successDiv = document.getElementById('successMessage');
    successDiv.textContent = message;
    successDiv.style.display = 'block';
}

function hideMessages() {
    document.getElementById('errorMessage').style.display = 'none';
    document.getElementById('successMessage').style.display = 'none';
} 