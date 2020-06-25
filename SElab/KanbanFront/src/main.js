import Vue from 'vue'
import App from './App.vue'
import Vuikit from 'vuikit'
import VuikitIcons from '@vuikit/icons'
import axios from "axios";
import VueAxios from "vue-axios";

import '@vuikit/theme'

Vue.use(Vuikit)
Vue.use(VuikitIcons)
Vue.use(VueAxios, axios);
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
