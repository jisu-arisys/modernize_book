import { createRouter, createWebHistory } from 'vue-router';
import MainRoutes from './MainRoutes';
import AuthRoutes from './AuthRoutes';
import BasicRoutes from './BasicRoutes';
import { isLoggedIn } from "@/data/Cookies";


export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/:pathMatch(.*)*',
            component: () => import('@/views/pages/Error404.vue')
        },
        MainRoutes,
        AuthRoutes,
        BasicRoutes
    ]
});

// Navigation Guard
router.beforeEach(async (to, from, next) => {
    // 인증 불필요 페이지
    if(!to.meta.requiresAuth || from.path =='/auth/login'){
        next(); // 페이지 이동 허용

    }else {
    // 인증이 필요한 페이지
        const boolean = await isLoggedIn();
        //서버세션이 있는 경우
        if(boolean){
            next();
        }
        //로그인되지 않았거나 서버세션이 없는경우
        else{
            next({
                path: '/auth/login',
                query: { redirect: to.fullPath } // 로그인 후 원래 페이지로 리다이렉트
            });
        }
    }
});