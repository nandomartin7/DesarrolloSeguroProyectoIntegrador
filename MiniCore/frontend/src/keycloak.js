import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8080',      // URL de tu Keycloak
  realm: 'ProyectoFinal',            // Nombre exacto del realm
  clientId: 'minicore-front'         // El clientId que creaste en Keycloak
});

export default keycloak;
