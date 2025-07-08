import { createApp } from 'vue';
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
      if      (roles.includes('admin'))     router.replace('/admin/inicio');
      else if (roles.includes('empleado'))  router.replace('/empleado/');
      else if (roles.includes('cliente'))   router.replace('/cliente/');
    }

    // 3) Auto-refresh (renueva si faltan <60 s)
    setInterval(() => keycloak.updateToken(60), 30_000);
  })
  .catch(err => console.error('Keycloak init error', err));
