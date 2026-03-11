<script setup>
import { ref } from 'vue';
import { login } from '../services/api';

const username = ref('');
const password = ref('');
const error = ref('');
const loading = ref(false);

const handleLogin = async () => {
  error.value = '';
  loading.value = true;
  try {
    const response = await login(username.value, password.value);
    // 检查响应是否是token（长度较长的字符串）
    if (typeof response === 'string' && response.length > 100) {
      // 登录成功，存储token和用户信息
      localStorage.setItem('token', response);
      localStorage.setItem('user', JSON.stringify(username.value));
      // 跳转到学生管理页面
      window.location.href = '/';
    } else {
      // 登录失败
      error.value = response || '登录失败';
    }
  } catch (err) {
    error.value = '登录失败，请检查网络连接';
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="login-page">
    <!-- 左侧背景区域 -->
    <div class="login-bg">
      <div class="bg-header">
        <div class="logo">学生管理系统</div>
      </div>
      <div class="illustration">
        <div class="character character-1"></div>
        <div class="character character-2"></div>
        <div class="character character-3"></div>
      </div>
      <div class="bg-footer">
        <a href="#">隐私政策</a>
        <span>•</span>
        <a href="#">服务条款</a>
      </div>
    </div>
    
    <!-- 右侧登录表单 -->
    <div class="login-container">
      <div class="login-header">
        <h1>欢迎回来！</h1>
        <p>请登录您的账户继续管理学生信息</p>
      </div>
      <div v-if="error" class="error">{{ error }}</div>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">电子邮箱</label>
          <input 
            type="email" 
            id="username" 
            v-model="username" 
            required 
            :disabled="loading"
            placeholder="your@email.com"
          >
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <div class="password-input">
            <input 
              type="password" 
              id="password" 
              v-model="password" 
              required 
              :disabled="loading"
              placeholder="••••••••"
            >
          </div>
        </div>
        <div class="form-options">
          <div class="remember-me">
            <input type="checkbox" id="remember">
            <label for="remember">记住我</label>
          </div>
          <a href="#" class="forgot-password">忘记密码？</a>
        </div>
        <button type="submit" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
        <div class="divider">
          <span>或</span>
        </div>
        <button type="button" class="google-btn">
          <span class="google-icon">G</span>
          使用 Google 登录
        </button>
        <div class="register-link">
          还没有账号？ <a href="#">立即注册</a>
        </div>
      </form>
    </div>
  </div>
</template>

<style>
/* 全局重置 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  margin: 0;
  padding: 0;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  overflow: hidden;
}
</style>

<style scoped>
/* 登录页面整体布局 */
.login-page {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
  margin: 0;
  padding: 0;
  border: none;
  box-sizing: border-box;
  position: relative;
}

/* 左侧背景区域 */
.login-bg {
  flex: 0 0 50%;
  min-width: 50%;
  max-width: 50%;
  background-color: #f3f4f6;
  display: flex;
  flex-direction: column;
  padding: 40px;
  position: relative;
  overflow: hidden;
  animation: fadeInLeft 0.8s ease;
  justify-content: space-between;
}

/* 背景头部 */
.bg-header {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px 0;
}

/* 品牌标识 */
.logo {
  font-size: 24px;
  font-weight: 700;
  color: #374151;
}

/* 插图容器 */
.illustration {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 40px 0;
}

/* 卡通角色 */
.character {
  position: relative;
  border-radius: 12px;
  animation: float 6s ease-in-out infinite;
}

.character-1 {
  width: 120px;
  height: 140px;
  background-color: #8b5cf6;
  margin-right: -20px;
  z-index: 3;
  animation-delay: 0s;
}

.character-2 {
  width: 100px;
  height: 120px;
  background-color: #111827;
  margin-right: -20px;
  z-index: 2;
  animation-delay: 1s;
}

.character-3 {
  width: 80px;
  height: 100px;
  background-color: #f97316;
  z-index: 1;
  animation-delay: 2s;
}

/* 角色眼睛 */
.character::before,
.character::after {
  content: '';
  position: absolute;
  width: 12px;
  height: 12px;
  background-color: white;
  border-radius: 50%;
  top: 30px;
}

.character-1::before {
  left: 30px;
}

.character-1::after {
  right: 30px;
}

.character-2::before {
  left: 25px;
}

.character-2::after {
  right: 25px;
}

.character-3::before {
  left: 20px;
}

.character-3::after {
  right: 20px;
}

/* 角色嘴巴 */
.character-1::after {
  content: '';
  position: absolute;
  width: 20px;
  height: 4px;
  background-color: white;
  border-radius: 2px;
  top: 60px;
  left: 50%;
  transform: translateX(-50%);
}

.character-2::after {
  content: '';
  position: absolute;
  width: 20px;
  height: 4px;
  background-color: white;
  border-radius: 2px;
  top: 50px;
  left: 50%;
  transform: translateX(-50%);
}

.character-3::after {
  content: '';
  position: absolute;
  width: 16px;
  height: 4px;
  background-color: white;
  border-radius: 2px;
  top: 40px;
  left: 50%;
  transform: translateX(-50%);
}

/* 背景底部链接 */
.bg-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
  font-size: 14px;
  color: #6b7280;
  padding: 20px 0;
}

.bg-footer a {
  color: #6b7280;
  text-decoration: none;
  transition: color 0.3s ease;
  font-weight: 400;
}

.bg-footer a:hover {
  color: #374151;
  text-decoration: underline;
}

/* 右侧登录容器 */
.login-container {
  flex: 0 0 50%;
  min-width: 50%;
  max-width: 50%;
  padding: 80px;
  background-color: white;
  animation: slideInRight 0.8s ease;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.login-container > * {
  width: 100%;
  max-width: 400px;
}

/* 登录头部 */
.login-header {
  margin-bottom: 48px;
  text-align: center;
}

.login-header h1 {
  margin-bottom: 12px;
  color: #111827;
  font-size: 32px;
  font-weight: 700;
  letter-spacing: -0.02em;
}

.login-header p {
  color: #6b7280;
  font-size: 16px;
  margin: 0;
  line-height: 1.5;
}

/* 错误提示样式 */
.error {
  color: #dc2626;
  text-align: left;
  margin-bottom: 24px;
  padding: 16px 20px;
  background-color: #fef2f2;
  border-radius: 8px;
  border-left: 4px solid #dc2626;
  box-shadow: 0 2px 4px rgba(220, 38, 38, 0.1);
  animation: fadeIn 0.3s ease;
  font-size: 14px;
  line-height: 1.4;
}

/* 表单组样式 */
.form-group {
  margin-bottom: 24px;
}

/* 标签样式 */
label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #374151;
  font-size: 14px;
  letter-spacing: 0.01em;
}

/* 输入框样式 */
input {
  width: 100%;
  padding: 14px 16px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  font-size: 16px;
  transition: all 0.3s ease;
  background-color: white;
  font-family: inherit;
}

/* 输入框聚焦样式 */
input:focus {
  outline: none;
  border-color: #8b5cf6;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
  background-color: white;
}

/* 输入框禁用样式 */
input:disabled {
  background-color: #f3f4f6;
  cursor: not-allowed;
  opacity: 0.7;
}

/* 密码输入框容器 */
.password-input {
  position: relative;
}

/* 表单选项 */
.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

/* 记住我选项 */
.remember-me {
  display: flex;
  align-items: center;
  gap: 8px;
}

.remember-me input[type="checkbox"] {
  width: 16px;
  height: 16px;
  cursor: pointer;
  accent-color: #8b5cf6;
}

.remember-me label {
  margin: 0;
  font-weight: 400;
  cursor: pointer;
  color: #6b7280;
  font-size: 14px;
}

/* 忘记密码链接 */
.forgot-password {
  color: #8b5cf6;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s ease;
  font-weight: 500;
}

.forgot-password:hover {
  color: #7c3aed;
  text-decoration: underline;
}

/* 登录按钮 */
button {
  width: 100%;
  padding: 14px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  font-family: inherit;
  letter-spacing: 0.01em;
}

/* 主登录按钮 */
button[type="submit"] {
  background-color: #8b5cf6;
  color: white;
  margin-bottom: 24px;
  position: relative;
  overflow: hidden;
}

button[type="submit"]:hover {
  background-color: #7c3aed;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(139, 92, 246, 0.3);
}

button[type="submit"]:active {
  transform: translateY(0);
}

/* 按钮禁用样式 */
button:disabled {
  background-color: #9ca3af;
  cursor: not-allowed;
  opacity: 0.7;
  transform: none;
  box-shadow: none;
}

/* 分隔线 */
.divider {
  position: relative;
  text-align: center;
  margin: 24px 0;
}

.divider span {
  background-color: white;
  padding: 0 16px;
  font-size: 14px;
  color: #6b7280;
  position: relative;
  z-index: 1;
  font-weight: 500;
}

.divider::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background-color: #e5e7eb;
  z-index: 0;
}

/* Google 登录按钮 */
.google-btn {
  background-color: white;
  color: #374151;
  border: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  margin-bottom: 24px;
  transition: all 0.3s ease;
}

.google-btn:hover {
  background-color: #f9fafb;
  border-color: #d1d5db;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

/* Google 图标 */
.google-icon {
  width: 20px;
  height: 20px;
  background-color: #4285f4;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 12px;
}

/* 注册链接 */
.register-link {
  text-align: center;
  font-size: 14px;
  color: #6b7280;
  margin-top: 16px;
}

.register-link a {
  color: #8b5cf6;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s ease;
}

.register-link a:hover {
  color: #7c3aed;
  text-decoration: underline;
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInLeft {
  from {
    opacity: 0;
    transform: translateX(-50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes slideInRight {
  from {
    opacity: 0;
    transform: translateX(50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  25% {
    transform: translateY(-10px) rotate(2deg);
  }
  50% {
    transform: translateY(0) rotate(0deg);
  }
  75% {
    transform: translateY(10px) rotate(-2deg);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-page {
    flex-direction: column;
  }
  
  .login-bg {
    width: 100%;
    min-height: 400px;
    padding: 40px 24px;
  }
  
  .bg-header {
    margin-bottom: 40px;
  }
  
  .illustration {
    margin: 20px 0;
  }
  
  .character-1 {
    width: 100px;
    height: 120px;
  }
  
  .character-2 {
    width: 80px;
    height: 100px;
  }
  
  .character-3 {
    width: 60px;
    height: 80px;
  }
  
  .login-container {
    width: 100%;
    padding: 48px 24px;
  }
  
  .login-header h1 {
    font-size: 28px;
  }
  
  .login-header p {
    font-size: 14px;
  }
  
  input {
    padding: 12px 14px;
  }
  
  button {
    padding: 12px;
  }
  
  .form-options {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .forgot-password {
    align-self: flex-end;
  }
  
  .divider {
    margin: 20px 0;
  }
}

/* 加载动画 */
button[type="submit"]:disabled::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 20px;
  height: 20px;
  margin: -10px 0 0 -10px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: #fff;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>