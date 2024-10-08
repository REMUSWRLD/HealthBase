import {router} from '../main';
import {store} from '../main';
import axios from 'axios';

export default {
    getPatients(){
        return axios.get('/api/patients', {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    getPatientsByPCP(id){
        return axios.get(`/api/patients/pcp/${id}`, {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    getPatientById(id){
        return axios.get(`/api/patients/${id}`, {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    addPatient(patient){
        return axios.post('/api/patients', patient, {headers: { Authorization: `Bearer ${store.state.token}`}});
    },

    updatePatient(id, patient) {
        return axios.put(`/api/patients/${id}`, patient, { headers: { Authorization: `Bearer ${store.state.token}` } });
    },
    
    deletePatient(id) {
        return axios.delete(`/api/patients/${id}`, { headers: { Authorization: `Bearer ${store.state.token}` } });
    },

    //Patient Contact Detail Service

    getContact(id){
        return axios.get(`/api/patients/${id}/contact`, {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    updateContact(id, contact){
        return axios.put(`/api/patients/${id}/contact`, contact, {headers:{Authorization: `Bearer ${store.state.token}`}});
    },

    //Demographic Service

    getDemographics(id){
        return axios.get(`/api/patients/${id}/demographics`, {headers:{Authorization: `Bearer ${store.state.token}`}});
    }
}
