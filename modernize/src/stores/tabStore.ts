import { defineStore } from "pinia";
import { router } from "@/router";
import config from "@/config";

export const useTabStore = defineStore({
  id: "tab",
  state: () => ({
    maxTabs: config.maxTabs,
    tabs: [{title:'modern', to: '/dashboards/modern'}] as { title: string; to: string }[],
    activeTab: '/dashboards/modern' as string | null
  }),
  actions: {
    setActiveTab(page: { title: string; to: string }){
      console.log("to : ", page.title);
      this.activeTab = page.to;
      router.push(page.to);
    },
    addTab(page: { title: string; to: string }) {
      const existingTab = this.tabs.find(tab => tab.to === page.to);
      if (existingTab) {
        this.activeTab = existingTab.to; // 이미 열려있다면 활성화
      } else {
        if (this.tabs.length >= this.maxTabs) {
          alert(`최대 ${this.maxTabs}개의 탭만 열 수 있습니다.`);
          if (this.tabs.length > 0) {
            this.setActiveTab(this.tabs[0]);
          }
        } else {
          this.tabs.push(page);
          this.activeTab = page.to;
        }
      }
    },
    removeTab(tabId: string) {
      const index = this.tabs.findIndex(tab => tab.to === tabId);
      if (index === -1) return; // 이미 없는 탭이면 종료
      // 활성화된 탭이 삭제된 경우, 적절한 탭으로 이동
      if (this.activeTab === tabId) {
        this.setActiveTab(this.tabs[index-1]);
      }
      // 배열에서 해당 탭 삭제
      this.tabs.splice(index, 1);
      console.log(this.tabs);
    },
  },
  getters: {
    isActive: (state) => (path: string) => {
      return state.activeTab === path;
    },
  }
});