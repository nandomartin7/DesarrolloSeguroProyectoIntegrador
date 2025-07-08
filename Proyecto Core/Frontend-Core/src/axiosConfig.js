import axios from 'axios';
import keycloak from './keycloak';

const api = axios.create({ baseURL: 'http://localhost:8081/' });

api.interceptors.request.use(cfg => {
  if (keycloak.authenticated) {
    cfg.headers.Authorization = `Bearer ${keycloak.token}`;
  }
  return cfg;
});

export default api;
