<script setup lang="ts">
const props = withDefaults(defineProps<{
  card?: {
    // link?: string;
    imgLink?: string;
    bookName?: string;
    author?: string;
    publisher?: string;
    totalLending?: number;
  };
}>(), {
  card: () => ({
    // link: "/",
    imgLink: "/assets/images/products/3.jpg",
    bookName: "title",
    author: "author",
    publisher: "publisher",
    totalLending: 0
  })
});

const searchUrl = 'https://search.naver.com/search.naver?query=';
</script>
<template>
    <v-card elevation="10" class="withbg" rounded="md">
        <v-img :src="card.imgLink" height="100%" class="rounded-t-md"></v-img>
        <div class="d-flex justify-end mr-4 mt-n5">
            <v-btn size="40" icon class="bg-primary d-flex" target="_blank" :href="searchUrl + card.bookName">
                <v-avatar size="30" class="text-white">
                    <BasketIcon size="15" />
                </v-avatar>
                <v-tooltip
                    activator="parent"
                    location="bottom"
                >Search On Naver
                </v-tooltip>
            </v-btn>
        </div>
        <v-card-item class="pt-0">
            <h6 class="text-h6" v-text="card.bookName"></h6>
            <div class="d-flex align-center justify-space-between mt-1">
                <div>
                    <span class="text-h6" v-text="card.author"></span>
                    <span class="text-body-1 ml-2 text-medium-emphasis" v-text="card.publisher"></span>
                </div>
                <v-rating density="compact" color="warning" size="small" :model-value="Math.min(5, card.totalLending)" readonly></v-rating>
            </div>
        </v-card-item>
    </v-card>
</template>
