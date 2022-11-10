import api from './api';
import TokenService from "./token.service";

class AuthService {
  async login({ email, password }) {
    return await api
      .post("/auth/signin", {
        email,
        password
      })
      .then((response) => {
        if (response.data.accessToken) {
          TokenService.setUser(response.data);
        }
        return response.data;
      });
  }
  
  logout() {
    let user = JSON.parse(localStorage.getItem("user"));
    api.delete("/auth/logout/" + user.id);
    TokenService.removeUser();
  }

  async register(user) {
    return await api.post("/auth/signup", user);
  }
}

export default new AuthService();