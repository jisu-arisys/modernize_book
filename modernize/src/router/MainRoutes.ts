const MainRoutes = {
    path: '/main',
    meta: {
        requiresAuth: true
    },
    redirect: '/main',
    component: () => import('@/layouts/full/FullLayout.vue'),
    children: [
        {
            name: 'Starter',
            path: '/sample-page',
            component: () => import('@/views/pages/SamplePage.vue')
        },
        {
            name: 'BookDetail',
            path: '/book-page/:bookId',
            component: () => import('@/views/pages/BookPage.vue')
        },
        {
            name: 'BookCard',
            path: '/book-cards',
            component: () => import('@/components/dashboard/BookCards.vue')
        }
    ]
};

export default MainRoutes;
