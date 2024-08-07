import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { store } from './store';

const app = createApp(App);

app.use(store); // Vuex 스토어를 등록
app.use(router); // Vue Router를 등록
app.mount('#app');
