import axios from "axios";
// 서버에 요청하는  공통된 API 요청 처리 함수
const header = { headers: { 'Content-Type': 'application/json' }}

const getData = async (url: string) => {
    try {
        const response = await axios.get('/api' + url);
        if (response.status === 200) {
            console.log(`${url} success`);
            return response.data;
        } else {
            console.log(`${url} fail`);
            throw '서버 요청 실패';
        }
    } catch (err) {
        throw err;
    }
};

const postData = async (url: string, data: any) => {
    try {
        const response = await axios.post('/api' + url, data, header);
        if (response.status === 200) {
            console.log(`${url} success`);
            return response.data;
        } else {
            console.log(`${url} fail`);
            throw '서버 요청 실패';
        }
    } catch (err) {
        throw err;
    }
};


export { getData, postData };