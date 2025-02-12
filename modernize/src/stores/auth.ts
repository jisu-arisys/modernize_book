import { defineStore } from 'pinia';
import { router } from '@/router';
import { fetchWrapper } from '@/utils/helpers/fetch-wrapper';
import {getAuthData} from "@/data/Axios";

const baseUrl = `${import.meta.env.VITE_API_URL}/users`;
const apiUrl = `/api`;

export const useAuthStore = defineStore({
    id: 'auth',
    state: () => ({
        // initialize state from local storage to enable user to stay logged in
        // @ts-ignore
        user: JSON.parse(localStorage.getItem('user')),
        returnUrl: null
    }),
    actions: {
        async login(username: string, password: string) {
            // const user = await fetchWrapper.post(`${baseUrl}/authenticate`, { username, password });
            const params = new URLSearchParams();
            params.append("username", username);
            params.append("password", password);
            const response = await getAuthData(params);

            // update pinia state
            this.user = response.userName;

            // store user details and jwt in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(response.userName));
            localStorage.setItem('username', response.userName);
            localStorage.setItem('auth', response.authName);
            // redirect to previous url or default to home page
            router.push(this.returnUrl || '/dashboards/modern');
        },
        logout() {
            this.user = null;
            localStorage.removeItem('user');
            router.push('/');
        }
    }
});
