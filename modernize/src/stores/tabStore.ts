import { defineStore } from "pinia";
import { router } from "@/router";

export const useTabStore = defineStore({
  id: "tab",
  state: () => ({
    maxTabs: 5,
    tabs: [{title:'modern', to: '/dashboards/modern'}] as { title: string; to: string }[],
    activeTab: null as string | null
  }),
  actions: {
    setActiveTab(page: { title: string; to: string }){
      console.log("to : ", page.title);
      this.activeTab = page.to;
      router.push(page.to)

    },
    addTab(page: { title: string; to: string }) {
      const existingTab = this.tabs.find(tab => tab.to === page.to);
      if (existingTab) {
        this.activeTab = existingTab.to; // 이미 열려있다면 활성화
      } else {
        if (this.tabs.length >= this.maxTabs) {
          alert(`최대 ${this.maxTabs}개의 탭만 열 수 있습니다.`);
          if (this.tabs.length > 0) {
            this.activeTab = this.tabs[0].to;
          }
          return;
        }
        this.tabs.push(page);
        this.activeTab = page.to;
      }
    },
    removeTab(tabId: string) {
      this.tabs = this.tabs.filter(tab => tab.to !== tabId);
      if (this.activeTab === tabId) {
        this.activeTab = this.tabs.length > 0 ? this.tabs[this.tabs.length - 1].to : this.tabs[0].to;
      }
    },
  },
});