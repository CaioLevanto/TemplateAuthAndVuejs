import api from '@/services/api';

class RoteiroService {

  getStatus() {
    return api.get("/travel/status");
  }

}

export default new RoteiroService();