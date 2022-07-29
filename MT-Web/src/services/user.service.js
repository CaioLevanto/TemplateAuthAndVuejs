import api from './api';
import authHeader from './auth-header';
const API_URL = 'http://52.67.244.102:8081/api/test/';

class UserService {

  getPublicContent() {
    return api.get(API_URL + 'all');
  }

  getUserBoard() {
    return api.get(API_URL + 'user', { headers: authHeader() });
  }

  getModeratorBoard() {
    return api.get(API_URL + 'mod', { headers: authHeader() });
  }

  getAdminBoard() {
    return api.get(API_URL + 'admin', { headers: authHeader() });
  }

}

export default new UserService();