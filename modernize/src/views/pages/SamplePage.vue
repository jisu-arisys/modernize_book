<script setup lang="ts">
import UiParentCard from '@/components/shared/UiParentCard.vue';
import BookTable from '@/components/dashboard/BookTable.vue';
</script>
<template>
    <v-row>
        <v-col cols="12" md="12">
            <UiParentCard title="Book list">
                <div class="pa-7 pt-1"><p class="text-body-1">This is a Book list </p></div>
            </UiParentCard>
            <br>
            <BookTable />
        </v-col>
    </v-row>
</template>

<script setup>

</script>
