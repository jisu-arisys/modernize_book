import {getData, postData} from "@/data/Axios";
import {ref} from "vue";

export function useComment() {

const error = ref<string>('');
const comments = ref([]);

const getComments = async (getUrl: string) => {
    if(getUrl.length == 0){
        error.value = '데이터 조회 실패 :' + getUrl;
        return;
    } else {
        error.value = '';
    }

    try {
        console.debug("commentTable reLending")
        comments.value = await getData(getUrl);
    } catch (e : any) {
        if (e.response?.status === 404) {
            error.value = '조회 결과가 없습니다.';
        } else {
            error.value = '오류 발생 : ' + (e.response?.status || '알 수 없는 오류');
        }
    }
}

const handleSaveComment = async (getUrl : string , commentData : any ) => {
    console.debug(commentData);
    try {
        const response = await postData('/update/comment', commentData);
        console.log(response);
        error.value = '저장 성공 !';
        await getComments(getUrl);
    } catch (e : any) {
        handelError(e);
    }
};

const handelError = (e : any) => {
    if (e.response?.status === 404) {
        error.value = '조회 결과가 없습니다.';
    } else {
        error.value = '오류 발생 : ' + (e.response?.status || '알 수 없는 오류');
    }
}

    return { error, comments, getComments, handleSaveComment };
}