import Vue from 'vue';
import Router from 'vue-router';
import Home from '../src/views/Home.vue';
import Login from '../src/views/Login.vue';
import Register from '../src/views/Register.vue';


let usr = localStorage.getItem('user');

Vue.use(Router);

export const router = new Router({
  routes: [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/configurations', name: 'configurations',  component:  () => import('../src/views/admin/Configurations.vue') },
    { path: '/profile', name: 'profile', component: () => import('../src/views/Profile.vue') },
    { path: '/roteiro', name: 'roteiro', component: () => import('../src/views/admin/roteiro/Roteiro.vue') },
    { path: '/lista', name: 'chat', component: () => import('../src/views/admin/roteiro/ListaRoteiro.vue') },
    { path: '/visualizar/:id', component: () => import('../src/views/admin/roteiro/RoteiroView.vue') },
    { path: '/itinerario/:id', name: 'itinerario', component: () => import('../src/views/admin/Itinerario.vue') },
    { path: '/cliente/:id', name: 'cliente', component: () => import('../src/views/admin/Cliente.vue') },
    { path: '/pagamento/:id', name: 'pagamento', component: () => import('../src/views/admin/Pagamento.vue') },
    { path: '/chat/:id', name: 'chat', component: () => import('../src/views/admin/Chat.vue') },
    { path: '/user', name: 'user', component: () => import('../src/views/user/BoardUser.vue') }
  ],
  mode: 'history'
});

router.beforeEach((to, from, next) => {
  const publicPages = ['/', '/login', '/register'];
  const authRequired = !publicPages.includes(to.path);

  if (authRequired && !usr) {
    next('/');
  } else {
    next();
  }
});

function validateRole() {
  if (usr != null) {
    let role = JSON.parse(usr).roles;

    for (let key in role) {
      switch (role[key]) {
        case "ROLE_ADMIN":
          for (let admin in adminRoutes) {
            defaultRoute.push(adminRoutes[admin]);
          }
          break;

        case "ROLE_USER":
          for (let user in userRoutes) {
            defaultRoute.push(userRoutes[user]);
          }
          break;
      }
    }
  }

  return defaultRoute;
}