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

const getBooks = async () => {
    try {
        books.value = await getData('/bookList');
    } catch (e) {
        handelError(e);
    }
}

//관리자가 도서 등록할 때
const handleSaveBook = async (getUrl : string , bookData : any ) => {
    console.debug(bookData);
    try {
        const response = await postData('/update/book', bookData);
        console.log(response);
        error.value = '저장 성공 !';
        await getBooks();
    } catch (e : any) {
        handelError(e);
    }
};

const handelError = (e : any) => {
    error.value = e.message;
}

const handelBack = (router : any) => {
    alert('데이터를 읽지 못하였습니다. 이전화면으로 돌아갑니다.');
    router.back();
}

    return { error, bookId, book, books, getParamBookId, getBook, getBooks, handleSaveBook };
}