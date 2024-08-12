import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import About from '../views/About.vue';
import PatientCard from '../views/Patient.vue';
import ProviderCard from '../views/Provider.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import { store } from '../main';

export function createAppRouter() {
    const router = createRouter({
        history: createWebHistory(),
        routes: [
            {
                path: '/',
                name: 'Home',
                component: Home,
                meta: {
                    requireAuth: true,
                }
            },
            {
                path: '/about',
                name: 'About',
                component: About,
                meta: {
                    requireAuth: true,
                }
            },
            {
                path: '/patient/:id',
                name: 'PatientCard',
                component: PatientCard,
                meta: {
                    requireAuth: true,
                }
            },
            {
                path: '/provider/:id',
                name: 'ProviderCard',
                component: ProviderCard,
                meta: {
                    requireAuth: true,
                }
            },
            {
                path: '/login',
                name: 'Login',
                component: Login,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/register',
                name: 'Register',
                component: Register,
                meta: {
                    requireAuth: false,
                }
            }
        ]
    });

    router.beforeEach((to, from, next) => {
        if (to.meta.requireAuth  && store.state.token === null) {
            next('/login');
        } else {
            next();
        }
    });
    
    return router;
}