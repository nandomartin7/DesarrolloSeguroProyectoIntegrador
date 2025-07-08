/*import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import keycloak from './keycloak';

keycloak
  .init({
    onLoad: 'login-required',      // redirige a login si no hay sesión
    pkceMethod: 'S256',
    checkLoginIframe: false
  })
  .then(() => {
    // 1) Montamos Vue SOLO cuando Keycloak ya autenticó
    const app = createApp(App);
    app.config.globalProperties.$keycloak = keycloak;
    app.use(router);
    app.mount('#app');

    // 2) Redirección a la home correcta según rol
    if (router.currentRoute.value.path === '/') {
      const roles = keycloak.tokenParsed?.realm_access?.roles || [];
      if (roles.includes('empleado'))  router.replace('/empleado/filtrar');
    }

    // 3) Auto-refresh (renueva si faltan <60 s)
    setInterval(() => keycloak.updateToken(60), 30_000);
  })
  .catch(err => console.error('Keycloak init error', err));
*/


import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import keycloak from './keycloak';

keycloak
  .init({
    onLoad: 'login-required',
    pkceMethod: 'S256',
    checkLoginIframe: false
  })
  .then(authenticated => {
    // 1) Si NO autenticó o NO tiene rol "empleado" → logout inmediato
    const roles = keycloak.tokenParsed?.realm_access?.roles || [];
    if (!authenticated || !roles.includes('empleado')) {
      // puedes mostrar un toast aquí si quieres
      return keycloak.logout({ redirectUri: window.location.origin });
    }

    // 2) Montar la app solo si pasamos la validación
    const app = createApp(App);
    app.config.globalProperties.$keycloak = keycloak;
    app.use(router);
    app.mount('#app');

    // **aquí** rediriges al dashboard de empleado    
    router.replace('/empleado/filtrar/');

    // 3) Auto‐refresh del token
    setInterval(() => keycloak.updateToken(60), 30_000);
  })
  .catch(err => console.error('Keycloak init error', err));

