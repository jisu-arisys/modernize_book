<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useBook } from "@/data/Book";
import BookCard from "@/components/shared/BookCard.vue";
const { error, books, getBooks } = useBook();

onMounted(async ()=> {
  await getBooks();
});

</script>
<template>
    <v-row>
        <span>{{ error }}</span>
        <slot />
        <v-col cols="12" lg="3" sm="6" v-if="books" v-for="book in books" :key="book.bookId">
            <v-card elevation="10" class="withbg" rounded="md">
                <RouterLink :to="'/book-page/' + book.bookId">
                    <BookCard :card="book"></BookCard>
                </RouterLink>
            </v-card>
        </v-col>
    </v-row>
</template>
