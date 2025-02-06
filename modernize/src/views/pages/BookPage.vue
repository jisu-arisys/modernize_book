<script setup lang="ts">
import BookCard from "@/components/shared/BookCard.vue";
import RegisterComment from "@/components/dashboard/RegisterComment.vue";
import Comments from "@/components/shared/Comments.vue";
import {ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useComment } from "@/data/Comment";
import { useBook } from "@/data/Book";
const { error : commentError, comments, getComments, handleSaveComment } = useComment();
const { error : bookError, bookId, book, getParamBookId, getBook  } = useBook();

const router = useRouter();
const route = useRoute();
const commentUrl = ref<string>('/commentList/' + bookId.value);

onMounted(async () => {
  getParamBookId(route, router);
  commentUrl.value = '/commentList/' + bookId.value;
});

watch(() => bookId.value, async (newData) => {
  if (newData > 0) {
    await getBook(bookId.value);
    await getComments(commentUrl.value);
  }
}, { deep: false });


</script>

<template>
    <v-row>
        <v-col cols="12" lg="3" sm="4">
          <BookCard :card=book></BookCard>
        </v-col>
        <v-col cols="12" sm="9">
          <Comments :edit="true" :list="comments" @update:comment="(commentData : any) => handleSaveComment(commentUrl, commentData)">
            <span>{{ commentError || bookError }}</span>
          </Comments>
          <RegisterComment :book-id="bookId" @insert:comment="(commentData : any) => handleSaveComment(commentUrl, commentData)"></RegisterComment>
        </v-col>
    </v-row>
</template>