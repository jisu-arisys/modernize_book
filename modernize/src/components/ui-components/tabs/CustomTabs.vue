<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

import {ref, onMounted, watchEffect, computed} from "vue";
import {PhoneIcon, HeartIcon, UserIcon } from "vue-tabler-icons";
import {router} from "@/router";
const tab4 = ref<{ id: string; title: string; icon: any; component: string }[]>([]);
const maxTabs = 4; // 최대 탭 개수 제한
const activeTab = ref(tab4.value.length > 0 ? tab4.value[0].id : null);
onMounted( async ()=>{
  tab4.value = [
  { id: "1111", title: "Item One", icon: PhoneIcon, component: "PageOne" },
  { id: "2222", title: "Item Two", icon: HeartIcon, component: "PageTwo" },
  { id: "3333", title: "Item Three", icon: UserIcon, component: "PageThree" },
  { id: "4444", title: "Item Four", icon: UserIcon, component: "PageFour" },
];
})

const defaultTab = null; // 플러스 탭 미사용시.
// const defaultTab = { id: "0000", title: "Item New", icon: PhoneIcon, component: "PageNew" };

// 새 탭 추가 함수
const addTab = (page: any) => {
  const existingTab = tab4.value.find(tab => tab.id === page.id);
  if (existingTab) {
    activeTab.value = existingTab.id; // 이미 열려있다면 활성화
  } else {

    console.log(tab4.value.length);
    if (tab4.value.length == maxTabs){
        alert('최대 가능한 탭의 갯수(' + maxTabs + ')를 초과하였습니다. 기존 탭을 닫고 다시 시도해주세요.');
        if(tab4.value != null){
          activeTab.value = tab4.value[0].id;
        }
        return; // 최대 개수 제한
    }
    tab4.value.push(page);
    activeTab.value = page.id;
  }
};

// 탭 닫기 함수
const closeTab = (id: string) => {
  const index = tab4.value.findIndex(tab => tab.id === id);
  if (index !== -1) {
    tab4.value.splice(index, 1);
    if (activeTab.value === id) {
      activeTab.value = tab4.value.length ? tab4.value[Math.max(0, index - 1)].id : null;
    }
  }
};

watchEffect(() => {
  if (tab4.value.length > 0 && activeTab.value == null) {
    activeTab.value = tab4.value[0].id;
  }
});

// 활성화된 탭의 경로 찾기
const getPathByComponentName = computed(() => {
  const actTab = tab4.value.find(tab => tab.id === activeTab.value);
  const route = router.getRoutes().find(r => r.name ===actTab?.component);
  return route?.path;
});

//최근에 열었던 페이지를 탭으로 추가 : 사용자가 메뉴를 클릭하면 새로운 탭을 생성
// 탭 클릭 시 이전 정보 유지 : keep-alive를 사용해서 탭의 상태를 유지
// 이미 열린 탭이 있으면 해당 탭으로 이동 : 같은 메뉴 클릭 시 새로운 탭을 열지 않고 기존 탭으로 이동
// 각 탭에 닫기 버튼 추가 : X 버튼을 클릭하면 해당 탭을 닫음
// 최대 탭 개수 제한 : 예를 들어, 최대 5개까지만 열 수 있도록 제한, 개수 초과 문구를 표시.
// +  '모든 탭 닫기'를 탭하면 일괄 삭제할 수 있습니다.

</script>
<template>
    <v-tabs v-model="activeTab" color="primary">
        <v-tab v-for="(tab, index) in tab4" :key="tab.id" :value="tab.id">
          {{ tab.title}}
          <v-btn @click.stop="closeTab(tab.id)" icon size="x-small" style="--v-btn-height: 8px;" class="ma-1">
            <XIcon stroke-width="1.5" width="12" />
          </v-btn>
        </v-tab>
        <v-tab v-if="defaultTab" min-width="10" class="x-small">
          <v-btn @click.stop="addTab(defaultTab)" icon style="--v-btn-height: 8px;">
            <PlusIcon stroke-width="1.5" width="20"/>
          </v-btn>
        </v-tab>
    </v-tabs>
    <v-divider></v-divider>
    <v-card-text class="bg-grey100 mt-4 rounded-md">
        <v-window v-model="activeTab">
            <v-window-item v-for="tab in tab4" :value="tab.id">
              {{ tab.title }} {{ getPathByComponentName }}
            </v-window-item>
        </v-window>
    </v-card-text>
</template>
