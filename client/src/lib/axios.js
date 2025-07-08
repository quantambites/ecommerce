// src/lib/axios.js
import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL, // from GitHub secret
  withCredentials: true, // enables sending cookies (like token)
});

export default axiosInstance;
