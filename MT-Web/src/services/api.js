import axios from "axios";

const instance = axios.create({
  baseURL: `http://52.67.244.102:8081/api`,
  headers: {
    "Content-Type": "application/json"
  },
});

export default instance;