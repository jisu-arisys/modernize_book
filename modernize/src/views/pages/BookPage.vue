<script setup lang="ts">
import UiParentCard from '@/components/shared/UiParentCard.vue';
import {ref, onMounted} from "vue";
import { useRoute, useRouter } from "vue-router";
import { getData } from "@/data/Axios";

const router = useRouter();
const route = useRoute();
const book = ref([]);
const comments = ref([]);

onMounted(async () => {
  const bookId = route.params.bookId;
  if(!bookId){
    await router.push('/');
    return;
  }

  try {
    const response = await getData('/bookDetail/' + bookId);
    console.log("getBookList success", response);
    book.value = response.book;
    comments.value = response.comments;
  } catch (err) {
    const before = document.referrer;
    await router.push(before);
  }
});
</script>
<template>
    <v-row>
        <v-col cols="12" md="12">
            <UiParentCard title="Book page">
                <div class="pa-7 pt-1"><p class="text-body-1">{{ book }}</p></div>
                <div class="pa-7 pt-1"><p class="text-body-1" v-for="comment in comments">{{ comment }}</p></div>
            </UiParentCard>
            <br>
        </v-col>
    </v-row>
</template>

<script setup>

</script>
