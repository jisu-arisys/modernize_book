<script setup> //Composition API 방식 :
// setup() 내부에서는 this 가 없음, getCurrentInstance() 로 호출해야 글로벌변수 사용가능
import { getData } from "@/data/Axios";
import { ref, onMounted } from 'vue';
import { router } from "@/router";

const books = ref([]);
const error = ref(null);

onMounted(async () => {
  try{
    books.value = await getData('/bookList');
  } catch (err) {
    error.value = err;
  }
});

const goToBookPage = (bookId) => {
  router.push(`/book-page/${bookId}`);
};
</script>

<template>
  <v-card elevation="10" class="">
    <v-card-item class="pa-6">
      <v-card-title class="text-h5 pt-sm-2 pb-7">Book Table</v-card-title>
      <v-table class="month-table">
        <thead>
        <tr>
          <th class="text-subtitle-1 font-weight-bold">Image</th>
          <th class="text-subtitle-1 font-weight-bold">Name</th>
          <th class="text-subtitle-1 font-weight-bold">Author</th>
          <th class="text-subtitle-1 font-weight-bold">Publisher</th>
          <th class="text-subtitle-1 font-weight-bold text-right">Lended</th>
        </tr>
        </thead>
        <tbody v-if="books.length > 0">
          <tr v-for="book in books" :key="book.bookId" class="month-item" @click="goToBookPage(book.bookId)">
<!--            <router-link :to="`/book-page/${book.bookId}`">-->
              <td>
                <div class="d-flex ga-3 align-center">
                  <div class="v-theme--BLUE_THEME v-avatar--density-default" style="width: 80px; height: 80px;">
                    <img :src="book.imgLink ? book.imgLink : '/assets/images/products/1.jpg'" :alt="book.imgLink" width="80"><!---->
                    <span class="v-avatar__underlay"></span>
                  </div>
                </div>
              </td>
              <td>
                <div class="">
                  <h6 class="text-subtitle-1 font-weight-bold">{{ book.bookName }}</h6>
                </div>
              </td>
              <td>
                <h6 class="text-body-1 text-muted">{{ book.author }}</h6>
              </td>
              <td>
                <h6 class="text-body-1 text-muted">{{ book.publisher }}</h6>
              </td>
              <td>
                <h6 class="text-h6 text-right">{{ book.totalLending }}</h6>
              </td>
<!--            </router-link>-->
          </tr>
        </tbody>
        <tbody v-else>
          <tr class="text-center">
            data books are empty.
            <br>
            <span>error : {{ error }}</span>
          </tr>
        </tbody>
      </v-table>
    </v-card-item>
  </v-card>
</template>

<style scoped lang="scss">

</style>