import Keycloak from 'keycloak-js';

export default new Keycloak({
  url: 'http://localhost:8080',      // URL de tu Keycloak
  realm: 'ProyectoFinal',            // Nombre exacto del realm
  clientId: 'core-front'         // El clientId que creaste en Keycloak
});

