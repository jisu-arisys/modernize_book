import { defineStore } from "pinia";

export const useTabStore = defineStore({
  id: "tab",
  state: () => ({
    maxTabs: 5,
    tabs: [] as { id: string; title: string }[],
    activeTab: null as string | null
  }),
  actions: {
    addTab(page: { id: string; title: string }) {
      const existingTab = this.tabs.find(tab => tab.id === page.id);
      if (existingTab) {
        this.activeTab = existingTab.id; // 이미 열려있다면 활성화
      } else {
        if (this.tabs.length >= this.maxTabs) {
          alert(`최대 가능한 탭 개수(${this.maxTabs})를 초과하였습니다.`);
          if (this.tabs.length > 0) {
            this.activeTab = this.tabs[0].id;
          }
          return;
        }
        this.tabs.push(page);
        this.activeTab = page.id;
      }
    },
    removeTab(tabId: string) {
      this.tabs = this.tabs.filter(tab => tab.id !== tabId);
      if (this.activeTab === tabId) {
        this.activeTab = this.tabs.length > 0 ? this.tabs[0].id : null;
      }
    },
  },
});