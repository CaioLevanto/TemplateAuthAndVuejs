import api from './api';
const API_URL = 'http://52.67.244.102:8081/api/auth/';

class AuthService {
  async login(user) {
    return await api
      .post(API_URL + 'signin', {
        email: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }
  
  logout() {
    localStorage.removeItem('user');
  }

  async register(user) {
    return await api.post(API_URL + 'signup', {
      email: user.email,
      password: user.password
    });
  }
}

export default new AuthService();