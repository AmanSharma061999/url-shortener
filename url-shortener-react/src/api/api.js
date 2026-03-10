import axios from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL;

const api = axios.create({
  baseURL,
  withCredentials: true,
  headers: { "Content-Type": "application/json" },
});

// TEMP DEBUG: remove after fixing
api.interceptors.request.use((config) => {
  console.log("➡️ API REQUEST:", (config.baseURL || "") + config.url);
  return config;
});

export default api;