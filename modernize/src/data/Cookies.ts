import {router} from "@/router";
import {checkLoginStatus} from "@/data/Axios";

const checkSession = () => {
    const cookies = document.cookie;
    if (!cookies.includes('JSESSIONID')) {
        alert('api 요청을 위해 로그인이 필요합니다.');
        router.push('/auth/login'); // 로그인 페이지 또는 메인 페이지로 이동
    }
    return true;
};

const hasSession = () => {
    const cookies = document.cookie;
    if (!cookies.includes('JSESSIONID')) {
        return true;
    }
    return false;
};

// 로그인 상태 확인
const isLoggedIn = async () => {
    if(localStorage.getItem('username') == null){
        return false;
    }
    if(localStorage.getItem('auth') == null){
        return false;
    }
    const hasCookies = await checkLoginStatus();

    return hasCookies;
}

export { checkSession, hasSession, isLoggedIn };