<script setup lang="ts">
import BookCard from "@/components/shared/BookCard.vue";
import CommentTable from "@/components/dashboard/CommentTable.vue";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getData } from "@/data/Axios";

const router = useRouter();
const route = useRoute();
const book = ref([]);
const bookId = ref<number>(0);
const commentUrl = ref<string>('');
const error = ref<string>('');

onMounted(async () => {
  try {
    let param: string | string[] | undefined = route.params.bookId;
    if (Array.isArray(param)) {
      bookId.value = Number(param[0]);
    } else if (param !== undefined) {
      bookId.value = Number(param);
    }
  } catch (e) {
    handelError();
  }

  if(bookId.value == 0){
    handelError();
  }

  commentUrl.value = '/commentList/' + bookId.value;
  try {
    const response = await getData('/bookDetail/' + bookId.value);
    console.log("getBookList success", response);
    book.value = response.book;
  } catch (err) {
    handelError();
  }
});

const handelError = () => {
  alert('데이터를 읽지 못하였습니다. 이전화면으로 돌아갑니다.');
  router.back()
}
</script>

<template>
    <v-row>
        <!--        변수 생성시점까지 CommentTable 컴포넌트의 랜딩을 지연시킴-->
        <v-col cols="12" sm="9">
          <CommentTable v-bind:get-url="commentUrl">
          </CommentTable>
        </v-col>
        <v-col cols="12" lg="3" sm="4">
          <BookCard :card=book></BookCard>
        </v-col>
    </v-row>
</template>