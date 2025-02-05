<script setup lang="ts">
import UiParentCard from '@/components/shared/UiParentCard.vue';
import BookCard from "@/components/shared/BookCard.vue";
import Comments from "@/components/shared/Comments.vue";
import {ref, onMounted, computed} from "vue";
import { useRoute, useRouter } from "vue-router";
import { getData } from "@/data/Axios";

const router = useRouter();
const route = useRoute();
const book = ref([]);
const comments = ref([]);
const showAll = ref(false);

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

const visibleComments = computed(() =>
    showAll.value ? comments.value : comments.value.slice(0, 3)
);
</script>
<template>
<!--    <v-row>-->
<!--        <v-col cols="12" md="12">-->
<!--            <UiParentCard title="Book page">-->
<!--                <div class="pa-7 pt-1"><p class="text-body-1">{{ book }}</p></div>-->
<!--                <div class="pa-7 pt-1"><p class="text-body-1" v-for="comment in comments">{{ comment }}</p></div>-->
<!--            </UiParentCard>-->
<!--        </v-col>-->
<!--    </v-row>-->
    <v-row>
        <v-col cols="12" sm="9">
          <Comments :list=visibleComments>
            <div>
              <h6 class="text-h6 text-muted font-weight-medium d-flex justify-center align-center mt-4">
                <a @click.prevent="showAll = !showAll" class="text-primary text-decoration-none text-body-1 opacity-1 font-weight-medium pl-2">
                  <span v-if = !showAll> 더보기 </span>
                  <span v-else> 감추기 </span>

                </a>
              </h6>
            </div>
          </Comments>
        </v-col>
        <v-col cols="12" lg="3" sm="4">
          <BookCard :card=book></BookCard>
        </v-col>
    </v-row>
</template>

<script setup>

</script>
