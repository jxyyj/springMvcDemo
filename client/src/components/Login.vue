<script setup>
import { ref } from 'vue';
import { login } from '../services/api';

const username = ref('');
const password = ref('');
const error = ref('');
const loading = ref(false);
const showPassword = ref(false);
const isTyping = ref(false);

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

const handleGoogleSignIn = async () => {
  // 这里可以实现Google登录逻辑
  alert('Google登录功能暂未实现');
};
</script>

<template>
  <div class="login-page">
    <!-- 左侧背景区域 -->
    <div class="login-bg">
      <!-- 品牌标识 -->
      <div class="bg-header">
        <div class="logo">
          <div class="logo-icon">S</div>
          <span>学生管理系统</span>
        </div>
      </div>
      
      <!-- 动画角色 -->
      <div class="illustration">
        <div class="character character-1"></div>
        <div class="character character-2"></div>
        <div class="character character-3"></div>
      </div>
      
      <!-- 底部链接 -->
      <div class="bg-footer">
        <a href="#">隐私政策</a>
        <span>•</span>
        <a href="#">服务条款</a>
      </div>
      
      <!-- 装饰性元素 -->
      <div class="decorative-grid"></div>
      <div class="decorative-circle circle-1"></div>
      <div class="decorative-circle circle-2"></div>
    </div>
    
    <!-- 右侧登录表单 -->
    <div class="login-container">
      <!-- 移动端品牌标识 -->
      <div class="mobile-logo">
        <div class="logo-icon">S</div>
        <span>学生管理系统</span>
      </div>
      
      <!-- 登录头部 -->
      <div class="login-header">
        <h1>欢迎回来！</h1>
        <p>请填写您的详细信息</p>
      </div>
      
      <!-- 错误提示 -->
      <div v-if="error" class="error">{{ error }}</div>
      
      <!-- 登录表单 -->
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="username">电子邮箱</label>
          <input 
            type="email" 
            id="username" 
            v-model="username" 
            required 
            :disabled="loading"
            placeholder="you@example.com"
            @focus="isTyping = true"
            @blur="isTyping = false"
          >
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <div class="password-input">
            <input 
              :type="showPassword ? 'text' : 'password'" 
              id="password" 
              v-model="password" 
              required 
              :disabled="loading"
              placeholder="••••••••"
            >
            <button 
              type="button" 
              class="password-toggle" 
              @click="showPassword = !showPassword"
            >
              {{ showPassword ? '👁️' : '👁️‍🗨️' }}
            </button>
          </div>
        </div>
        <div class="form-options">
          <div class="remember-me">
            <input type="checkbox" id="remember">
            <label for="remember">记住30天</label>
          </div>
          <a href="#" class="forgot-password">忘记密码？</a>
        </div>
        <button type="submit" :disabled="loading" class="login-btn">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>
      
      <!-- Google登录按钮 -->
      <div class="google-login">
        <button type="button" class="google-btn" @click="handleGoogleSignIn">
          <svg class="google-icon" viewBox="0 0 488 512">
            <path fill="currentColor" d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 126 23.4 172.9 61.9l-76.2 76.2C322.3 113.2 289.4 96 248 96c-88.8 0-160.1 71.9-160.1 160.1s71.3 160.1 160.1 160.1c98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 26.9 3.9 41.4z"></path>
          </svg>
          使用 Google 登录
        </button>
      </div>
      
      <!-- 注册链接 -->
      <div class="register-link">
        还没有账号？ <a href="#">立即注册</a>
      </div>
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
  background-color: #f9fafb;
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
  background: linear-gradient(to bottom right, #e5e7eb, #f3f4f6, #f9fafb);
  display: flex;
  flex-direction: column;
  padding: 48px;
  position: relative;
  overflow: hidden;
  animation: fadeInLeft 0.8s ease;
  justify-content: space-between;
}

/* 背景头部 */
.bg-header {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  z-index: 20;
}

/* 品牌标识 */
.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 18px;
  font-weight: 600;
  color: #374151;
}

.logo-icon {
  width: 32px;
  height: 32px;
  background-color: #8b5cf6;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  font-size: 16px;
}

/* 插图容器 */
.illustration {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 20;
  height: 500px;
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
  gap: 32px;
  font-size: 14px;
  color: #6b7280;
  z-index: 20;
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

/* 装饰性元素 */
.decorative-grid {
  position: absolute;
  inset: 0;
  background-image: linear-gradient(to right, rgba(0, 0, 0, 0.05) 1px, transparent 1px),
                    linear-gradient(to bottom, rgba(0, 0, 0, 0.05) 1px, transparent 1px);
  background-size: 20px 20px;
  z-index: 10;
}

.decorative-circle {
  position: absolute;
  border-radius: 50%;
  background-color: rgba(139, 92, 246, 0.1);
  filter: blur(3xl);
  z-index: 10;
}

.circle-1 {
  top: 25%;
  right: 25%;
  width: 256px;
  height: 256px;
}

.circle-2 {
  bottom: 25%;
  left: 25%;
  width: 384px;
  height: 384px;
  background-color: rgba(249, 115, 22, 0.1);
}

/* 右侧登录容器 */
.login-container {
  flex: 0 0 50%;
  min-width: 50%;
  max-width: 50%;
  padding: 48px;
  background-color: white;
  animation: slideInRight 0.8s ease;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

/* 移动端品牌标识 */
.mobile-logo {
  display: none;
  align-items: center;
  justify-content: center;
  gap: 12px;
  font-size: 18px;
  font-weight: 600;
  color: #374151;
  margin-bottom: 48px;
}

.mobile-logo .logo-icon {
  width: 32px;
  height: 32px;
  background-color: #8b5cf6;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  font-size: 16px;
}

/* 登录头部 */
.login-header {
  margin-bottom: 40px;
  text-align: center;
  width: 100%;
  max-width: 420px;
}

.login-header h1 {
  margin-bottom: 8px;
  color: #111827;
  font-size: 32px;
  font-weight: 700;
  letter-spacing: -0.02em;
}

.login-header p {
  color: #6b7280;
  font-size: 14px;
  margin: 0;
  line-height: 1.5;
}

/* 登录表单 */
.login-form {
  width: 100%;
  max-width: 420px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 错误提示样式 */
.error {
  color: #dc2626;
  text-align: left;
  padding: 12px 16px;
  background-color: #fef2f2;
  border-radius: 8px;
  border: 1px solid #fecaca;
  font-size: 14px;
  line-height: 1.4;
  width: 100%;
  max-width: 420px;
  margin-bottom: 20px;
}

/* 表单组样式 */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

/* 标签样式 */
label {
  display: block;
  font-weight: 500;
  color: #374151;
  font-size: 14px;
  letter-spacing: 0.01em;
}

/* 输入框样式 */
input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 16px;
  transition: all 0.3s ease;
  background-color: white;
  font-family: inherit;
  height: 48px;
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

/* 密码显示/隐藏按钮 */
.password-toggle {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  padding: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: auto;
  height: auto;
  box-shadow: none;
  margin: 0;
}

/* 表单选项 */
.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
.login-btn {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  font-family: inherit;
  letter-spacing: 0.01em;
  background-color: #8b5cf6;
  color: white;
  height: 48px;
  position: relative;
  overflow: hidden;
}

.login-btn:hover {
  background-color: #7c3aed;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(139, 92, 246, 0.3);
}

.login-btn:active {
  transform: translateY(0);
}

/* 按钮禁用样式 */
.login-btn:disabled {
  background-color: #9ca3af;
  cursor: not-allowed;
  opacity: 0.7;
  transform: none;
  box-shadow: none;
}

/* Google登录按钮 */
.google-login {
  width: 100%;
  max-width: 420px;
  margin-top: 24px;
}

.google-btn {
  width: 100%;
  padding: 12px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  font-family: inherit;
  letter-spacing: 0.01em;
  background-color: white;
  color: #374151;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  height: 48px;
}

.google-btn:hover {
  background-color: #f9fafb;
  border-color: #d1d5db;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

/* Google图标 */
.google-icon {
  width: 20px;
  height: 20px;
  color: #4285f4;
}

/* 注册链接 */
.register-link {
  text-align: center;
  font-size: 14px;
  color: #6b7280;
  margin-top: 32px;
  width: 100%;
  max-width: 420px;
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

/* 加载动画 */
.login-btn:disabled::after {
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

/* 响应式设计 */
@media (max-width: 1024px) {
  .login-bg {
    padding: 32px;
  }
  
  .login-container {
    padding: 32px;
  }
  
  .illustration {
    height: 400px;
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
}

@media (max-width: 768px) {
  .login-page {
    flex-direction: column;
  }
  
  .login-bg {
    width: 100%;
    min-height: 400px;
    padding: 32px 24px;
    display: none;
  }
  
  .mobile-logo {
    display: flex;
  }
  
  .login-container {
    width: 100%;
    min-width: 100%;
    max-width: 100%;
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
  
  .login-btn,
  .google-btn {
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
}
</style>