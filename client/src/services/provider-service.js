import { router } from '../main';
import { store } from '../main';
import axios from 'axios';


export default {
    getProviders(){
        return axios.get('/api/providers', {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    getProviderById(id){
        return axios.get(`/api/providers/${id}`, {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    addProvider(provider){
        return axios.post('/api/providers', {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    updateProvider(id, provider) {
        return axios.put(`/api/providers/${id}`, provider, { headers: { Authorization: `Bearer ${store.state.token}` } });
    },
    
    deleteProvider(id) {
        return axios.delete(`/api/providers/${id}`, { headers: { Authorization: `Bearer ${store.state.token}` } });
    }
}