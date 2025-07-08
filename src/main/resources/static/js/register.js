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

function validatePhone() {
    const phone = document.getElementById('phone').value.trim();
    if (phone && /[^0-9]/.test(phone)) {
        showError('手机号必须为数字');
    }
}

document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    const phone = document.getElementById('phone').value.trim();
    const registerBtn = document.getElementById('registerBtn');
    const registerBtnText = document.getElementById('registerBtnText');

    // 隐藏之前的消息
    hideMessages();

    // 验证输入
    if (!username || !password) {
        showError('请填写所有必填字段');
        return;
    }
    if (password.length < 6) {
        showError('密码长度至少6位');
        return;
    }
    if (phone && /[^0-9]/.test(phone)) {
        showError('手机号必须为数字');
        return;
    }
    if (phone && !/^\d{11}$/.test(phone)) {
        showError('手机号必须为11位数字');
        return;
    }

    // 显示加载状态
    registerBtn.disabled = true;
    registerBtnText.innerHTML = '<span class="loading"></span>注册中...';

    try {
        const response = await fetch('/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: username,
                password: password,
                phone: phone
            }),
            credentials: 'include'
        });

        const data = await response.json();

        if (data.success) {
            showSuccess('注册成功！正在跳转到登录页面...');
            setTimeout(() => {
                window.location.href = '/login';
            }, 2000);
        } else {
            showError(data.message || '注册失败，请重试');
        }
    } catch (error) {
        console.error('注册请求失败:', error);
        showError('网络错误，请重试');
    } finally {
        registerBtn.disabled = false;
        registerBtnText.textContent = '注册';
    }
});

// 密码强度检测
document.getElementById('password').addEventListener('input', function () {
    const password = this.value;
    const strengthDiv = document.getElementById('passwordStrength');

    if (password.length === 0) {
        strengthDiv.textContent = '';
        return;
    }

    let strength = 0;
    let feedback = '';

    if (password.length >= 6) strength++;
    if (password.length >= 8) strength++;
    if (/[a-z]/.test(password)) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;

    if (strength <= 2) {
        feedback = '密码强度：弱';
        strengthDiv.className = 'password-strength strength-weak';
    } else if (strength <= 4) {
        feedback = '密码强度：中等';
        strengthDiv.className = 'password-strength strength-medium';
    } else {
        feedback = '密码强度：强';
        strengthDiv.className = 'password-strength strength-strong';
    }

    strengthDiv.textContent = feedback;
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