import {getData, postData} from "@/data/Axios";
import {ref} from "vue";

export function useBook() {

const error = ref<string>('');
const books = ref([]);
const book = ref();
const bookId = ref<number>(0);

const getParamBookId =  (route : any, router : any) => {
    try {
        let param: string | string[] | undefined = route.params.bookId;
        if (Array.isArray(param)) {
            bookId.value = Number(param[0]);
        } else if (param !== undefined) {
            if(Number(param) < 1){
                handelBack(router);
                return;
            }

            bookId.value = Number(param);
        }
    } catch (e) {
        handelBack(router);
    }
}

const getBook = async (bookId : number) => {
    try {
        book.value = await getData('/bookDetail/' + bookId);
    } catch (e) {
        handelError(e);
    }
}

// const getBooks = async (getUrl: string) => {
//     if(getUrl.length == 0){
//         error.value = '데이터 조회 실패 :' + getUrl;
//         return;
//     } else {
//         error.value = '';
//     }
//
//     try {
//         console.debug("commentTable reLending")
//         comments.value = await getData(getUrl);
//     } catch (e : any) {
//         if (e.response?.status === 404) {
//             error.value = '조회 결과가 없습니다.';
//         } else {
//             error.value = '오류 발생 : ' + (e.response?.status || '알 수 없는 오류');
//         }
//     }
// }

// const handleSaveComment = async (getUrl : string , commentData : any ) => {
//     console.debug(commentData);
//     try {
//         const response = await postData('/update/comment', commentData);
//         console.log(response);
//         error.value = '저장 성공 !';
//         await getComments(getUrl);
//     } catch (e : any) {
//         handelError(e);
//     }
// };

const handelError = (e: any) => {
    if (e.response?.status === 404) {
        error.value = '조회 결과가 없습니다.';
    } else {
        error.value = '오류 발생 : ' + (e.response?.status || '알 수 없는 오류');
    }
}

const handelBack = (router : any) => {
    alert('데이터를 읽지 못하였습니다. 이전화면으로 돌아갑니다.');
    router.back();
}

    return { error, bookId, book, getParamBookId, getBook };
}