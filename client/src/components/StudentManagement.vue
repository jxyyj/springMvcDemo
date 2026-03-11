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
/* 全局样式 */
.student-management {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  min-height: 80vh;
}

/* 头部样式 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e9ecef;
}

h1 {
  color: #2c3e50;
  margin: 0;
  font-size: 28px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 10px;
}

.logout-btn {
  padding: 10px 20px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(220, 53, 69, 0.2);
}

.logout-btn:hover {
  background-color: #c82333;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(220, 53, 69, 0.3);
}

/* 错误提示样式 */
.error {
  color: #dc3545;
  background-color: #f8d7da;
  padding: 12px 16px;
  border-radius: 6px;
  margin-bottom: 20px;
  border-left: 4px solid #dc3545;
  box-shadow: 0 2px 4px rgba(220, 53, 69, 0.1);
  animation: fadeIn 0.3s ease;
}

/* 操作按钮区域 */
.actions {
  margin-bottom: 25px;
}

.add-btn {
  padding: 12px 24px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(40, 167, 69, 0.2);
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.add-btn:hover {
  background-color: #218838;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(40, 167, 69, 0.3);
}

/* 加载状态 */
.loading {
  text-align: center;
  padding: 40px;
  color: #6c757d;
  font-size: 16px;
  background-color: #f8f9fa;
  border-radius: 8px;
  margin: 20px 0;
  animation: pulse 1.5s infinite;
}

/* 表格样式 */
.student-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.student-table th,
.student-table td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #e9ecef;
  transition: all 0.2s ease;
}

.student-table th {
  background-color: #343a40;
  color: white;
  font-weight: 600;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.student-table tr {
  transition: all 0.3s ease;
}

.student-table tr:hover {
  background-color: #f8f9fa;
  transform: translateX(2px);
}

.student-table tr:last-child td {
  border-bottom: none;
}

/* 操作按钮 */
.edit-btn,
.delete-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.edit-btn {
  background-color: #007bff;
  color: white;
}

.edit-btn:hover {
  background-color: #0069d9;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 123, 255, 0.3);
}

.delete-btn {
  background-color: #dc3545;
  color: white;
}

.delete-btn:hover {
  background-color: #c82333;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(220, 53, 69, 0.3);
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
  animation: fadeIn 0.3s ease;
}

.modal-content {
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  width: 450px;
  max-width: 90%;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  animation: slideIn 0.3s ease;
}

.modal-content h3 {
  margin-top: 0;
  margin-bottom: 25px;
  color: #2c3e50;
  font-size: 20px;
  font-weight: 600;
  text-align: center;
  padding-bottom: 15px;
  border-bottom: 1px solid #e9ecef;
}

/* 表单样式 */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #495057;
  font-size: 14px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 12px;
  border: 1px solid #ced4da;
  border-radius: 6px;
  font-size: 16px;
  transition: all 0.3s ease;
  background-color: #f8f9fa;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #28a745;
  box-shadow: 0 0 0 3px rgba(40, 167, 69, 0.2);
  background-color: white;
}

.form-group input:disabled {
  background-color: #e9ecef;
  cursor: not-allowed;
  opacity: 0.7;
}

/* 模态框按钮区域 */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #e9ecef;
}

.cancel-btn,
.confirm-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-left: 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.cancel-btn {
  background-color: #6c757d;
  color: white;
}

.cancel-btn:hover {
  background-color: #5a6268;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(108, 117, 125, 0.3);
}

.confirm-btn {
  background-color: #28a745;
  color: white;
}

.confirm-btn:hover {
  background-color: #218838;
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(40, 167, 69, 0.3);
}

.confirm-btn:disabled {
  background-color: #6c757d;
  cursor: not-allowed;
  opacity: 0.7;
  transform: none;
  box-shadow: none;
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideIn {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes pulse {
  0% {
    opacity: 0.6;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0.6;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .student-management {
    padding: 15px;
    margin: 10px;
  }
  
  .header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .student-table {
    font-size: 14px;
  }
  
  .student-table th,
  .student-table td {
    padding: 10px;
  }
  
  .edit-btn,
  .delete-btn {
    padding: 6px 12px;
    font-size: 12px;
  }
  
  .modal-content {
    padding: 20px;
    width: 95%;
  }
}
</style>