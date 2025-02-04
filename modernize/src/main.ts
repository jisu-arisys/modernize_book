import { createApp } from 'vue';
import App from './App.vue'; // .ts 파일에서 .vue 파일을 인식할 수 있도록 shims-vue.d.ts 파일 생성함.
import { router } from './router';
import vuetify from './plugins/vuetify';
import '@/scss/style.scss';
import PerfectScrollbar from 'vue3-perfect-scrollbar';
import VueApexCharts from 'vue3-apexcharts';
import VueTablerIcons from 'vue-tabler-icons';
import Maska from 'maska';
import { ref, onMounted } from 'vue';

const app = createApp(App);
app.use(router);
app.use(PerfectScrollbar);
app.use(VueTablerIcons);
app.use(Maska);
app.use(VueApexCharts);
app.use(ref, onMounted);
app.use(vuetify).mount('#app');
