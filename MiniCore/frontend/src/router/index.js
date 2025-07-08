import { createRouter, createWebHistory } from 'vue-router';
import FiltrarGastos from '@/components/FiltrarGastos.vue';
import LoginEmpleado from '@/components/LoginEmpleado.vue';
import keycloak from '@/keycloak';


const routes = [
  {
    path: '/',
    name: 'LoginEmpleado',
    component: LoginEmpleado,
  },
  {
    path: '/empleado/filtrar/',
    name: 'FiltrarGastos',
    component: FiltrarGastos,
    meta: { requiresAuth: true, role: 'empleado' }
  },
];

/*const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

// Proteger las rutas
router.beforeEach((to, _from, next) => {
  if (!to.meta.requiresAuth) return next();

  const roles = keycloak.tokenParsed?.realm_access?.roles || [];

  if (!keycloak.authenticated) {
    // usuario no logueado → pide login y vuelve a la ruta
    keycloak.login({ redirectUri: window.location.origin + to.fullPath });
  } else if (to.meta.role && !roles.includes(to.meta.role)) {
    // logueado pero sin rol suficiente
    next('/');
  } else {
    next();            // acceso concedido
  }
});

export default router;*/

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  // si la ruta no requiere auth, la dejamos pasar
  if (!to.meta.requiresAuth) {
    return next();
  }

  // ruta protegida: chequeamos autenticación
  if (!keycloak.authenticated) {
    // no logueado → redirige a login de KC y vuelve aquí
    return keycloak.login({ redirectUri: window.location.origin + to.fullPath });
  }

  // ya está logueado, ahora chequeamos rol
  const roles = keycloak.tokenParsed?.realm_access?.roles || [];
  if (to.meta.role && !roles.includes(to.meta.role)) {
    // logueado pero sin permiso → forzamos logout
    return keycloak.logout({ redirectUri: window.location.origin });
  }

  // todo OK
  next();
});

export default router;