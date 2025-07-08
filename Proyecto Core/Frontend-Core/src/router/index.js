import { createRouter, createWebHistory } from 'vue-router';
//Admin
import Index from '../components/Index.vue';
import InicioAdmin from '@/components/InicioAdmin.vue';
//Cliente
import LoginCliente from '@/components/LoginCliente.vue';
import InicioCliente from '@/components/InicioCliente.vue';
import ContratarPlanCliente from '@/components/ContratarPlanCliente.vue';
import MisContratos from '@/components/MisContratos.vue';
//Empleado
import LoginEmpleado from '@/components/LoginEmpleado.vue';
import InicioEmpleado from '@/components/InicioEmpleado.vue';
import PlanesSeguroEmpleado from '@/components/PlanesSeguroEmpleado.vue';
import ContratosEmpleado from '@/components/ContratosEmpleado.vue';
import UsosSeguroEmpleado from '@/components/UsosSeguroEmpleado.vue';
import AnalisisContrato from '@/components/AnalisisContrato.vue';
import FiltrarGastos from '@/components/FiltrarGastos.vue';

import keycloak from '@/keycloak';


const routes = [
  {
    path: '/',
    name: 'LoginPage',
    component: Index
  },
  {
    path: '/admin/inicio',
    name: 'InicioAdmin',
    component: InicioAdmin,
    meta: { requiresAuth: true, role: 'admin' }
  },
  {
    path: '/login-cliente',
    name: 'LoginCliente',
    component: LoginCliente
  },
  {
    path: '/cliente/',
    name: 'InicioCliente',
    component: InicioCliente,
    meta: { requiresAuth: true, role: 'cliente' }
  },
  {
    path: '/cliente/contratar-plan',
    name: 'ContratarPlanCliente',
    component: ContratarPlanCliente,
    meta: { requiresAuth: true, role: 'cliente' }
  },
  {
    path: '/cliente/mis-contratos',
    name: 'MisContratos',
    component: MisContratos,
    meta: { requiresAuth: true, role: 'cliente' }
  },

  {
    path: '/login-empleado',
    name: 'LoginEmpleado',
    component: LoginEmpleado
  },
  {
    path: '/empleado/',
    name: 'InicioEmpleado',
    component: InicioEmpleado,
    meta: { requiresAuth: true, role: 'empleado' }
  },
  {
    path: '/empleado/planes-seguro',
    name: 'PlanesSeguroEmpleado',
    component: PlanesSeguroEmpleado,
    meta: { requiresAuth: true, role: 'empleado' }
  },
  {
    path: '/empleado/contratos',
    name: 'ContratosEmpleado',
    component: ContratosEmpleado,
    meta: { requiresAuth: true, role: 'empleado' }
  },
  {
    path: '/empleado/usos-seguro',
    name: 'UsosSeguroEmpleado',
    component: UsosSeguroEmpleado,
    meta: { requiresAuth: true, role: 'empleado' }
  },
  {
    path: '/empleado/analisis-contrato',
    name: 'AnalisisContrato',
    component: AnalisisContrato,
    meta: { requiresAuth: true, role: 'empleado' }
  },
  {
    path: '/empleado/filtrar-gastos',
    name: 'FiltrarGastos',
    component: FiltrarGastos,
    meta: { requiresAuth: true, role: 'empleado' }
  },
];

const router = createRouter({
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

export default router;