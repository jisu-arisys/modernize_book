import { createRouter, createWebHistory } from 'vue-router';
import MainRoutes from './MainRoutes';
import AuthRoutes from './AuthRoutes';

export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/:pathMatch(.*)*',
            component: () => import('@/views/pages/Error404.vue')
        },
        MainRoutes,
        AuthRoutes
    ]
});

// Navigation Guard
router.beforeEach((to, from, next) => {
    const username = localStorage.getItem('username');
    const auth = localStorage.getItem('auth');
    const isAuthenticated = username !== null && auth !== null; // 로그인 상태 확인

    // 인증이 필요한 페이지이고 로그인되지 않은 경우
    if (to.meta.requiresAuth && !isAuthenticated) {
        next('/auth/login'); // 로그인 페이지로 리다이렉트
    } else {
        next(); // 페이지 이동 허용
    }
});
