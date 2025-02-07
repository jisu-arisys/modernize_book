import axios from "axios";
import {router} from "@/router";

// 서버에 요청하는  공통된 API 요청 처리 함수 : 인증 쿠키 포함
const header = { headers: { 'Content-Type': 'application/json' , withCredentials: true }}

const getData = async (url: string) => {
    try {
        const response = await axios.get('/api' + url, header);
        return response.data;
    } catch (err) {
        throw err;
    }
}

const postData = async (url: string, data: any) => {
    try {
        const response = await axios.post('/api' + url, data, header);
        return response.data;
    } catch (err) {
        throw err
    }
};

const loginHeader = { headers: { 'Content-Type': 'application/x-www-form-urlencoded',  withCredentials: true }}
const getAuthData = async (params: URLSearchParams) => {
    try {
        const response = await axios.post('/api/login', params, loginHeader);
        return response.data;
    } catch (err) {
        throw err;
    }
};

//권한없는 api 요청에 대해 spring 에서 리다이렉트 '/' 처리하는 걸 방지하기 위한 세션 검증절차
const checkLoginStatus = async () => {
    try {
        const response = await axios.get('/api/check/login'); // 세션 확인 요청
        return response.status === 200; // 세션이 유효하면 true 반환
    } catch (error) {
        return false; // 세션이 없으면 false 반환
    }
};

axios.interceptors.response.use(
    response => {
        if (!response.data || response.data.length === 0) {
            return Promise.reject(new Error('응답 데이터가 없습니다.'));
        } else if (typeof response.data === 'string' && response.data.trim().startsWith('<!DOCTYPE')) {
            //응답이 HTML 페이지인 경우 (리다이렉트 또는 에러 페이지)
            console.warn('HTML 응답 감지: 로그인 페이지로 리다이렉트');
            router.push('/auth/login');
            return Promise.reject(new Error('세션이 만료되었습니다. 다시 로그인하세요.'));
        }
        return response;
    },
    error => {
        if (error.response) {
            switch (error.reponse.status){
                case 401 :
                case 403 :
                    console.error('세션이 만료되었습니다. 다시 로그인하세요.');
                    router.push('/auth/login');
                    break;
                case 404 :
                    return Promise.reject(new Error('조회 결과가 없습니다.')); // 수정
                default :
                    return Promise.reject(error);
            }
        } return Promise.reject(error);
    }
);

export { getData, postData, getAuthData, checkLoginStatus };