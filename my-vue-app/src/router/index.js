import { createRouter, createWebHistory } from 'vue-router';
import { store } from '../store';
import HomePage from '../components/HomePage.vue';
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';
import AccountList from '../components/AccountList.vue';
import AccountDetail from '../components/AccountDetail.vue';
import CreateAccount from '../components/CreateAccount.vue';

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
        path: '/account',
        name: 'AccountList',
        component: AccountList,
        meta: { requiresAuth: true }
    },
    {
        path: '/account/new',
        name: 'CreateAccount',
        component: CreateAccount,
        meta: { requiresAuth: true }
    },
    {
        path: '/account/:userId',
        name: 'AccountDetail',
        component: AccountDetail,
        props: true,
        meta: { requiresAuth: true }
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

router.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

    if (requiresAuth && !store.getters.isAuthenticated) {
        next('/login');
    } else {
        next();
    }
});

export default router;
