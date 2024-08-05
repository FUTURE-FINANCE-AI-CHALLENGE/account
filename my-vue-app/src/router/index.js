import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex'; // Vuex 스토어 임포트
import HomePage from '../components/HomePage.vue';
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import AccountManager from '../components/AccountManager.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomePage,
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginForm,
    },
    {
        path: '/register',
        name: 'Register',
        component: RegisterForm,
    },
    {
        path: '/account-manager',
        name: 'AccountManager',
        component: AccountManager,
        meta: { requiresAuth: true } // 인증이 필요한 라우트
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

// 라우터 가드 설정
router.beforeEach((to, from, next) => {
    const store = useStore(); // Vuex 스토어 가져오기
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

    if (requiresAuth && !store.getters.isAuthenticated) {
        next('/login'); // 인증되지 않은 경우 로그인 페이지로 리디렉션
    } else {
        next();
    }
});

export default router;