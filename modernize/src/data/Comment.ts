import {getData, postData} from "@/data/Axios";
import {ref} from "vue";

export function useComment() {

const today = new Date();
const error = ref<string>('');
const comments = ref([]);
const comment = ref({
    bookId: 0,
    userName: '',
    commentId: -1,
    startLending: null,
    endLending: null,
    createDate: today,
    update_date: today,
    detail: '',
    status: '예약'
});

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
    } catch (e) {
        handelError(e);
    }
}

const createComment = ( bookId : number, username : string, detail : string) => {
    comment.value.bookId = bookId;
    comment.value.userName = username;
    comment.value.detail = detail;
    return comment;
}

const handleSaveComment = async (getUrl : string , commentData : any ) => {
    console.debug(commentData);
    try {
        const response = await postData('/update/comment', commentData);
        console.log(response);
        error.value = '저장 성공 !';
        await getComments(getUrl);
    } catch (e) {
        handelError(e);
    }
};

const handelError = (e : any) => {
    error.value = e.message;
}

    return { error, comment, comments, getComments, createComment, handleSaveComment };
}