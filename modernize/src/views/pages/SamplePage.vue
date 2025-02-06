<script setup lang="ts">
import UiParentCard from '@/components/shared/UiParentCard.vue';
import BookTable from '@/components/dashboard/BookTable.vue';
import CommentTable from '@/components/dashboard/CommentTable.vue';

</script>
<template>
    <v-row>
        <v-col cols="12" md="12">
            <UiParentCard title="Book list">
                <div class="pa-7 pt-1"><p class="text-body-1">This is a Book list </p></div>
            </UiParentCard>
            <br>
            <BookTable />
            <br>
            <CommentTable :title="'Request Comment List'" :edit="false" :get-url="'/requestList'"></CommentTable>
            <CommentTable :title="'Arrears Comment List'" :edit="false" :get-url="'/arrearsList'"></CommentTable>
            <CommentTable :title="'Book 1 Comment List'" :edit="false" :get-url="'/commentList/1'"></CommentTable>
            <CommentTable :title="'Book 2 Comment List'" :edit="false" :get-url="'/commentList/2'"></CommentTable>
        </v-col>
    </v-row>
</template>