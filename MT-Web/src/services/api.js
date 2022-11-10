import axios from "axios";

const instance = axios.create({
  baseURL: `http://18.228.225.27:8081/api`,
  headers: {
    "Content-Type": "application/json"
  },
});

export default instance;