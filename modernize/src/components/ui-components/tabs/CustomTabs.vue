<script setup lang="ts">
import { useTabStore } from '@/stores/tabStore';
const tabStore = useTabStore();
import { watch } from "vue";
import { router } from "@/router";

//  라우터 감지해서 자동으로 탭 추가
watch(() => router.currentRoute.value, (newRoute) => {
  console.log('newRoute', newRoute);
  if (newRoute && newRoute.path) {
    tabStore.addTab({ to: newRoute.path, title: (newRoute.name as string) || 'New Tab' });
  }
}, { immediate: true });

</script>
<template>
    <v-tabs v-model="tabStore.activeTab" color="primary">
        <v-tab v-for="(tab, index) in tabStore.tabs" :key="tab.to" :value="tab.to" @click="tabStore.setActiveTab(tab)">
          {{ tab.title}}
          <v-btn @click.stop="tabStore.removeTab(tab.to)" icon size="x-small" style="--v-btn-height: 8px;" class="ma-1">
            <XIcon stroke-width="1.5" width="12" />
          </v-btn>
        </v-tab>
    </v-tabs>
    <v-divider></v-divider>
    <slot/>
</template>
