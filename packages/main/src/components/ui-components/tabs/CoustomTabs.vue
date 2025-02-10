<script setup lang="ts">
import { ref, computed } from "vue";
import { HeartIcon, PhoneIcon, UserIcon } from "vue-tabler-icons";
const tab4 = ref(null);
const maxTabs = 5; // 최대 탭 개수 제한
const activeTab = ref<string | null>(null);

// 페이지 목록 (추가 가능)
const pageList = [
  { id: "1111", title: "Item One", icon: PhoneIcon, component: "PageOne" },
  { id: "2222", title: "Item Two", icon: HeartIcon, component: "PageTwo" },
  { id: "3333", title: "Item Three", icon: UserIcon, component: "PageThree" },
];

// 새 탭 추가 함수
const addTab = (page: any) => {
  const existingTab = tab4.value.find(tab => tab.id === page.id);
  if (existingTab) {
    activeTab.value = existingTab.id; // 이미 열려있다면 활성화
  } else {
    if (tab4.value.length >= maxTabs) return; // 최대 개수 제한
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

// 현재 활성화된 컴포넌트 찾기
const activeComponent = computed(() => {
  const tab = tab4.value.find(tab => tab.id === activeTab.value);
  return tab ? tab.component : null;
});


//최근에 열었던 페이지를 탭으로 추가 : 사용자가 메뉴를 클릭하면 새로운 탭을 생성
// 탭 클릭 시 이전 정보 유지 : keep-alive를 사용해서 탭의 상태를 유지
// 이미 열린 탭이 있으면 해당 탭으로 이동 : 같은 메뉴 클릭 시 새로운 탭을 열지 않고 기존 탭으로 이동
// 각 탭에 닫기 버튼 추가 : X 버튼을 클릭하면 해당 탭을 닫음
// 최대 탭 개수 제한 : 예를 들어, 최대 5개까지만 열 수 있도록 제한, 개수 초과 문구를 표시.
// +  '모든 탭 닫기'를 탭하면 일괄 삭제할 수 있습니다.

</script>
<template>
    <v-tabs v-model="tab4" color="primary">
        <v-tab value="1111">Item One </v-tab>
        <v-tab value="2222" disabled> Item Two </v-tab>
        <v-tab value="4444"> Item Three </v-tab>
    </v-tabs>
    <v-divider></v-divider>
    <v-card-text class="bg-grey100 mt-4 rounded-md">
        <v-window v-model="tab4">
            <v-window-item value="1111">
              item one
            </v-window-item>
            <v-window-item value="2222">
               item two
            </v-window-item>
            <v-window-item value="4444">
               item three
            </v-window-item>
        </v-window>
    </v-card-text>
</template>
