import api from './api';

class TravelService {

    postTravelForm(travel){
        return api.post('/user/solicitaViagem', travel);
    }

}

export default new TravelService();