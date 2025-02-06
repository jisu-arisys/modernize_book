<script setup lang="ts">
import BookCard from "@/components/shared/BookCard.vue";
import RegisterComment from "@/components/dashboard/RegisterComment.vue";
import {ref, onMounted, watch} from "vue";
import { useRoute, useRouter } from "vue-router";
import {getData, postData} from "@/data/Axios";
import Comments from "@/components/shared/Comments.vue";

const router = useRouter();
const route = useRoute();
const book = ref([]);
const bookId = ref<number>(0);
const commentUrl = ref<string>('');
const error = ref<string>('');
const comments = ref([]);

onMounted(async () => {
  try {
    let param: string | string[] | undefined = route.params.bookId;
    if (Array.isArray(param)) {
      bookId.value = Number(param[0]);
    } else if (param !== undefined) {
      if(Number(param) < 1){
        handelError();
        return;
      }
      bookId.value = Number(param);
    }
  } catch (e) {
    handelError();
  }
});

watch(() => bookId.value, async (newData) => {
  if (newData > 0) {
    await getBook();
    await getComments();
  }
}, { deep: false });

const getBook = async () => {
  try {
    book.value = await getData('/bookDetail/' + bookId.value);
  } catch (err) {
    handelError();
  }
}

const getComments = async () => {
  commentUrl.value = '/commentList/' + bookId.value;
  if(commentUrl.value.length == 0){
    error.value = '데이터 조회 실패 :' + commentUrl.value;
    return;
  } else {
    reset();
  }

  try {
    console.debug("commentTable reLending")
    comments.value = await getData(commentUrl.value);
  } catch (e) {
    error.value = '데이터 조회 실패:'
  }
}

const handelError = () => {
  alert('데이터를 읽지 못하였습니다. 이전화면으로 돌아갑니다.');
  router.back();
}

const handleSaveComment = async (commentData : any ) => {
  console.debug(commentData);
  try {
    const response = await postData('/update/comment', commentData);
    error.value = '저장 성공:';
    await getComments();
  } catch (e) {
    error.value = '저장 실패:';
  }
};

const reset =() => {
  error.value = '';
}
</script>

<template>
    <v-row>
        <v-col cols="12" lg="3" sm="4">
          <BookCard :card=book></BookCard>
        </v-col>
        <v-col cols="12" sm="9">
          <Comments :edit="true" :list="comments" @update:comment="handleSaveComment">
            <span>{{ error }}</span>
          </Comments>
          <RegisterComment :book-id="bookId" @insert:comment="handleSaveComment"></RegisterComment>
        </v-col>
    </v-row>
</template>