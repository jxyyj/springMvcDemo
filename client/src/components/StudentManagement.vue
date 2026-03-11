<script setup>
import { ref, onMounted } from 'vue';
import { studentApi, logout } from '../services/api';

const students = ref([]);
const loading = ref(false);
const error = ref('');
const showAddModal = ref(false);
const showEditModal = ref(false);

// 学生表单数据
const studentForm = ref({
  sid: '',
  sname: '',
  sage: '',
  ssex: ''
});

// 编辑时的学生ID
const editingStudentId = ref('');

// 加载学生列表
const loadStudents = async () => {
  loading.value = true;
  error.value = '';
  try {
    const response = await studentApi.getAll();
    if (response.code === 200) {
      students.value = response.data;
    } else {
      error.value = response.message || '获取学生列表失败';
    }
  } catch (err) {
    error.value = '获取学生列表失败，请检查网络连接';
  } finally {
    loading.value = false;
  }
};

// 打开添加学生模态框
const openAddModal = () => {
  studentForm.value = {
    sid: '',
    sname: '',
    sage: '',
    ssex: ''
  };
  showAddModal.value = true;
};

// 打开编辑学生模态框
const openEditModal = (student) => {
  studentForm.value = { ...student };
  editingStudentId.value = student.sid;
  showEditModal.value = true;
};

// 添加学生
const addStudent = async () => {
  loading.value = true;
  error.value = '';
  try {
    const response = await studentApi.add(studentForm.value);
    if (response.code === 200) {
      showAddModal.value = false;
      await loadStudents();
    } else {
      error.value = response.message || '添加学生失败';
    }
  } catch (err) {
    error.value = '添加学生失败，请检查网络连接';
  } finally {
    loading.value = false;
  }
};

// 更新学生
const updateStudent = async () => {
  loading.value = true;
  error.value = '';
  try {
    const response = await studentApi.update(studentForm.value);
    if (response.code === 200) {
      showEditModal.value = false;
      await loadStudents();
    } else {
      error.value = response.message || '更新学生失败';
    }
  } catch (err) {
    error.value = '更新学生失败，请检查网络连接';
  } finally {
    loading.value = false;
  }
};

// 删除学生
const deleteStudent = async (sid) => {
  if (!confirm('确定要删除这个学生吗？')) {
    return;
  }
  loading.value = true;
  error.value = '';
  try {
    const response = await studentApi.delete(sid);
    if (response.code === 200) {
      await loadStudents();
    } else {
      error.value = response.message || '删除学生失败';
    }
  } catch (err) {
    error.value = '删除学生失败，请检查网络连接';
  } finally {
    loading.value = false;
  }
};

// 登出
const handleLogout = async () => {
  try {
    await logout();
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    window.location.href = '/login';
  } catch (err) {
    // 即使登出失败，也清除本地存储并跳转
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    window.location.href = '/login';
  }
};

// 初始化加载学生列表
onMounted(() => {
  loadStudents();
});
</script>

<template>
  <div class="student-management">
    <header class="header">
      <h1>学生管理系统</h1>
      <button class="logout-btn" @click="handleLogout">登出</button>
    </header>
    
    <div v-if="error" class="error">{{ error }}</div>
    
    <div class="actions">
      <button class="add-btn" @click="openAddModal">添加学生</button>
    </div>
    
    <div class="loading" v-if="loading">加载中...</div>
    
    <table v-else class="student-table">
      <thead>
        <tr>
          <th>学号</th>
          <th>姓名</th>
          <th>年龄</th>
          <th>性别</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="student in students" :key="student.sid">
          <td>{{ student.sid }}</td>
          <td>{{ student.sname }}</td>
          <td>{{ student.sage }}</td>
          <td>{{ student.ssex }}</td>
          <td>
            <button class="edit-btn" @click="openEditModal(student)">编辑</button>
            <button class="delete-btn" @click="deleteStudent(student.sid)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
    
    <!-- 添加学生模态框 -->
    <div class="modal" v-if="showAddModal">
      <div class="modal-content">
        <h3>添加学生</h3>
        <form @submit.prevent="addStudent">
          <div class="form-group">
            <label for="sid">学号</label>
            <input type="text" id="sid" v-model="studentForm.sid" required>
          </div>
          <div class="form-group">
            <label for="sname">姓名</label>
            <input type="text" id="sname" v-model="studentForm.sname" required>
          </div>
          <div class="form-group">
            <label for="sage">年龄</label>
            <input type="number" id="sage" v-model="studentForm.sage" required>
          </div>
          <div class="form-group">
            <label for="ssex">性别</label>
            <select id="ssex" v-model="studentForm.ssex" required>
              <option value="">请选择</option>
              <option value="男">男</option>
              <option value="女">女</option>
            </select>
          </div>
          <div class="modal-actions">
            <button type="button" class="cancel-btn" @click="showAddModal = false">取消</button>
            <button type="submit" class="confirm-btn" :disabled="loading">
              {{ loading ? '添加中...' : '添加' }}
            </button>
          </div>
        </form>
      </div>
    </div>
    
    <!-- 编辑学生模态框 -->
    <div class="modal" v-if="showEditModal">
      <div class="modal-content">
        <h3>编辑学生</h3>
        <form @submit.prevent="updateStudent">
          <div class="form-group">
            <label for="edit-sid">学号</label>
            <input type="text" id="edit-sid" v-model="studentForm.sid" disabled>
          </div>
          <div class="form-group">
            <label for="edit-sname">姓名</label>
            <input type="text" id="edit-sname" v-model="studentForm.sname" required>
          </div>
          <div class="form-group">
            <label for="edit-sage">年龄</label>
            <input type="number" id="edit-sage" v-model="studentForm.sage" required>
          </div>
          <div class="form-group">
            <label for="edit-ssex">性别</label>
            <select id="edit-ssex" v-model="studentForm.ssex" required>
              <option value="">请选择</option>
              <option value="男">男</option>
              <option value="女">女</option>
            </select>
          </div>
          <div class="modal-actions">
            <button type="button" class="cancel-btn" @click="showEditModal = false">取消</button>
            <button type="submit" class="confirm-btn" :disabled="loading">
              {{ loading ? '更新中...' : '更新' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.student-management {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

h1 {
  color: #333;
  margin: 0;
}

.logout-btn {
  padding: 8px 16px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.logout-btn:hover {
  background-color: #d32f2f;
}

.error {
  color: red;
  background-color: #ffebee;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 15px;
}

.actions {
  margin-bottom: 20px;
}

.add-btn {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.add-btn:hover {
  background-color: #45a049;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}

.student-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.student-table th,
.student-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.student-table th {
  background-color: #f2f2f2;
  font-weight: 600;
}

.student-table tr:hover {
  background-color: #f5f5f5;
}

.edit-btn,
.delete-btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
}

.edit-btn {
  background-color: #2196F3;
  color: white;
}

.edit-btn:hover {
  background-color: #1976D2;
}

.delete-btn {
  background-color: #f44336;
  color: white;
}

.delete-btn:hover {
  background-color: #d32f2f;
}

/* 模态框样式 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  max-width: 90%;
}

.modal-content h3 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
  color: #555;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.cancel-btn,
.confirm-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
}

.cancel-btn {
  background-color: #9e9e9e;
  color: white;
}

.cancel-btn:hover {
  background-color: #757575;
}

.confirm-btn {
  background-color: #4CAF50;
  color: white;
}

.confirm-btn:hover {
  background-color: #45a049;
}

.confirm-btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>