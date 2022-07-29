import api from './api';

class CepService {

  findByCep(nrCep) {
    return api.get('https://brasilapi.com.br/api/cep/v1/' + nrCep);
  }

}

export default new CepService();