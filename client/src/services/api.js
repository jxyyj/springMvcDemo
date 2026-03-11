import axios from 'axios';

// 创建axios实例
const api = axios.create({
  baseURL: 'http://localhost:8080', // 后端API地址
  timeout: 10000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
});

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    // 处理401错误（未授权）
    if (error.response && error.response.status === 401) {
      // 清除localStorage中的token
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      // 重定向到登录页面
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 登录API
export const login = (username, password) => {
  return api.post('/login', { username, password });
};

// 登出API
export const logout = () => {
  return api.get('/logout');
};

// 学生管理API
export const studentApi = {
  // 获取所有学生
  getAll: () => {
    return api.get('/student/getAll');
  },
  // 根据ID获取学生
  getById: (sid) => {
    return api.get(`/student/getById/${sid}`);
  },
  // 添加学生
  add: (student) => {
    return api.post('/student/add', student);
  },
  // 更新学生
  update: (student) => {
    return api.post('/student/update', student);
  },
  // 删除学生
  delete: (sid) => {
    return api.delete(`/student/delete`, { params: { sid } });
  }
};

export default api;