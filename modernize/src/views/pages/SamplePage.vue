<script setup lang="ts">
import UiParentCard from '@/components/shared/UiParentCard.vue';
import BookTable from '@/components/dashboard/BookTable.vue';
import CommentTable from '@/components/dashboard/CommentTable.vue';
import RegisterComment from "@/components/dashboard/RegisterComment.vue";
import BookCards from "@/components/dashboard/BookCards.vue";

</script>
<template>
    <v-row>
        <v-col cols="12" md="12">
            <UiParentCard title="Banner">
                <div class="pa-7 pt-1"><p class="text-body-1">This is a advertisement or no announcement </p></div>
            </UiParentCard>
            <br>
            <BookCards />
            <br>
<!--            <BookTable />-->
            <br>
            <CommentTable :title="'Request Comment List'" :edit="false" :get-url="'/requestList'"></CommentTable>
            <CommentTable :title="'Arrears Comment List'" :edit="false" :get-url="'/arrearsList'"></CommentTable>
            <CommentTable :title="'My Comment List'" :edit="false" :get-url="'/commentList/mine/1'"></CommentTable>
            <br>
            <RegisterComment />
        </v-col>
    </v-row>
</template>