<script setup lang="ts">
import { ref, computed } from "vue";
const editingItemId = ref<number | null>(null);
const editableDetail = ref<string>('');
const error = ref<string>('');

const editItem = (item: any) => {
  //본인검증
  const editor = localStorage.getItem('username');
  console.debug(editor, item);
  if(item.userName === editor) {
    error.value = '';
    editingItemId.value = item.commentId;
    editableDetail.value = item.detail;
  } else {
    error.value = '본인 댓글이 아닙니다.';
  }
};

// 부모로 이벤트를 전달할 때 사용할 emits 정의
const emit = defineEmits<{
  (e: 'update:detail', updatedItem: any): void;
}>();

// computed property 를 사용해 실시간 양방향 바인딩을 구현하고자 할때.
const detailModel = computed<string>({
  get() {
    const comment  = props.list.find(item => item.commentId === editingItemId.value);
    return comment  ? comment.detail : '';
  },
  set(newVal: string) {
    // 부모 컴포넌트에 업데이트 이벤트를 전달하여, 부모에서 해당 데이터를 갱신하도록 함
    emit('update:detail', newVal);
  }
});


const props = withDefaults(defineProps<{
  list?: [{
    userName: string;
    commentId: number;
    createDate: string;
    detail: string;
    status: string;
  }];
}>(), {
  list: () => ([{
    userName: 'name',
    commentId: 1,
    createDate: '2025-03-03',
    detail: 'Payment received from John Doe of $385.90',
    status: '예약'
  }])
});

const statusColors : Record<string, string> = {
  '예약' : 'success',
  '대출' : 'warning',
  '연체' : 'error',
  '반납' : 'info'
};

const getStatusColor = (status : string) => {
  return statusColors[status] || "primary"; // 만약 매칭되는 값이 없으면 기본값
};
</script>
<template>
    <v-card elevation="10" class="withbg">
        <v-card-item class="pb-0">
            <v-card-title class="text-h5 pt-sm-2"> Comments <span class="px-6 text-body-1 textSecondary text-no-wrap  text-error">{{ error }}</span> </v-card-title>
            <div class="recent-transaction mt-10 px-3">
                <div v-for="(item, index) in list" :key="item.commentId">
                    <v-row class="d-flex mb-4">
                        <v-col cols="3" lg="3" md="auto" sm="auto" class="px-0 pt-0 pb-1 d-flex align-start">
                            <h6 class="text-body-1 textSecondary text-no-wrap">{{ item.createDate }}</h6>
                        </v-col>
                        <v-col cols="2" sm="1" class="px-0 text-center pt-0 pb-0">
                            <v-chip  :class="'bg-' + getStatusColor(item.status) " color="white"  size="small" >
                              {{ item.status }}
                            </v-chip>
                            <div v-if="index !== list.length - 1" class="line mx-auto bg-grey100"></div>
                        </v-col>
                        <v-col cols="7" sm="8" class="pt-0">
                            <div @click="editItem(item)" style="cursor: pointer" class="mb-2">
                              <!-- 편집 중 -->
                              <template v-if="editingItemId === item.commentId">
                                <v-row>
                                  <v-text-field v-model="detailModel" variant="outlined" hide-details color="primary"></v-text-field>
                                </v-row>
                              </template>
                              <!-- 텍스트 표시 -->
                              <template v-else>
                                <h6 class="text-body-1 font-weight-bold">{{ item.detail }}</h6>
                              </template>
                            </div>
                            <div class="mt-n2">
                              <p class="text-body-1 text-primary text-decoration-none">
                                {{ item.userName }}</p>
                            </div>
                        </v-col>
                    </v-row>
                </div>
            </div>
            <div class="recent-transaction mt-6 mb-6">
                <slot />
            </div>
        </v-card-item>
    </v-card>
</template>
<style lang="scss">
.recent-transaction {
    .line {
        width: 2px;
        height: 35px;
    }
}
</style>
