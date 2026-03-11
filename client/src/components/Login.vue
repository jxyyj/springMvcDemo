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
  <div class="login-container">
    <h2>登录</h2>
    <div v-if="error" class="error">{{ error }}</div>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">用户名</label>
        <input 
          type="text" 
          id="username" 
          v-model="username" 
          required 
          :disabled="loading"
        >
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input 
          type="password" 
          id="password" 
          v-model="password" 
          required 
          :disabled="loading"
        >
      </div>
      <button type="submit" :disabled="loading">
        {{ loading ? '登录中...' : '登录' }}
      </button>
    </form>
  </div>
</template>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.error {
  color: red;
  text-align: center;
  margin-bottom: 15px;
  padding: 10px;
  background-color: #ffebee;
  border-radius: 4px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
  color: #555;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

input:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #45a049;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>