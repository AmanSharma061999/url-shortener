import axios from "axios";

const baseURL =
  import.meta.env.VITE_API_BASE_URL ||
  "http://ec2-3-86-58-131.compute-1.amazonaws.com:8081";

const api = axios.create({
  baseURL,
  withCredentials: true,
  headers: { "Content-Type": "application/json" },
});

export default api;
