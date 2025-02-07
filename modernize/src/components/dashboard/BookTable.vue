<script setup>
import { onMounted } from 'vue';
import { useBook } from "@/data/Book";
import { router } from "@/router";
const { error, books, getBooks  } = useBook();

onMounted(async () => {
    await getBooks();
});

const goToBookPage = (bookId) => {
  router.push(`/book-page/${bookId}`);
};
</script>

<template>
  <v-card elevation="10" class="">
    <v-card-item class="pa-6">
      <v-card-title class="text-h5 pt-sm-2 pb-7">Book Table
        <span> {{ error }} </span>
      </v-card-title>
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
          </tr>
        </tbody>
        <tbody v-else>
          <tr class="text-center">
            data books are empty. error : {{ error }}
          </tr>
        </tbody>
      </v-table>
    </v-card-item>
  </v-card>
</template>

<style scoped lang="scss">

</style>