<script setup>
import { ref, onMounted } from 'vue';
import Login from './components/Login.vue';
import StudentManagement from './components/StudentManagement.vue';

const isLoggedIn = ref(false);

// 检查用户是否已登录
const checkLoginStatus = () => {
  const user = localStorage.getItem('user');
  isLoggedIn.value = !!user;
};

// 初始化检查登录状态
onMounted(() => {
  checkLoginStatus();
});
</script>

<template>
  <div class="app">
    <Login v-if="!isLoggedIn" />
    <StudentManagement v-else />
  </div>
</template>

<style>
/* 全局样式 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  color: #333;
  background-color: #f5f5f5;
}

.app {
  min-height: 100vh;
}
</style>
