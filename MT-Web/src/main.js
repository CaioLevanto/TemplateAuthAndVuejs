import Vue from 'vue';
import App from './App.vue';

// Importação fixa direto no fonte do bootstrap
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

import { router } from './router';
import store from './store';

// Criação dos icones
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons'
library.add(fas);
Vue.component('font-awesome-icon', FontAwesomeIcon);

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';
Vue.use(IconsPlugin);
Vue.use(BootstrapVue);

import Vuelidate from 'vuelidate';
Vue.use(Vuelidate);

import VueMask from 'v-mask'
Vue.use(VueMask);

import { VueMaskDirective } from 'v-mask'
Vue.directive('mask', VueMaskDirective);

import { VueMaskFilter } from 'v-mask'
Vue.filter('VMask', VueMaskFilter)

Vue.config.productionTip = false;

import '@/css/main.css';

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');