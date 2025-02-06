<script setup lang="ts">
import {onMounted, computed, ref} from "vue";
import {useRouter} from "vue-router";
const router = useRouter();
const editingItemId = ref<number | null>(null);
const editableDetail = ref<string>('');
const error = ref<string>('');
const showAll = ref(false);
const auth = ref<string | null>(null);

onMounted(async () => {
  auth.value = localStorage.getItem('auth');
  console.debug(isAdmin());
  console.debug(props);
});

const visibleComments = computed(() =>
    showAll.value ? props.list : props.list.slice(0, 3)
);

const handleClick = (item: any) => {
  reset();
  if(props.edit){
    editItem(item);
  }else {
    goToBookPage(item.bookId);
  }
}

const goToBookPage = (bookId : number) => {
  console.debug(bookId);
  router.push(`/book-page/${bookId}`);
};

const editItem = (item: any) => {
  //본인검증
  const editor = localStorage.getItem('username');
  console.debug(editor, item);
  if(item.userName === editor) {
    editingItemId.value = item.commentId;
    editableDetail.value = item.detail;
  } else {
    editingItemId.value = null;
    error.value = '본인 댓글이 아닙니다.';
  }
};

const saveDetail = (item: any) => {
  reset();
  if(confirm('댓을내용을 수정하시겠습니까?')) {
    item.detail = editableDetail.value;
    emit('update:comment', item);
  }
  editingItemId.value = null;
};

const editStatus = (item : any) => {
  reset();
  if(!isAdmin()){
    error.value = '관리자가 아닙니다.';
    return;
  }

  const nextStatus = (() => {
    switch (item.status) {
      case '예약': return '대출';
      case '대출':
      case '연체': return '반납';
      default: return null;
    }
  })();

  if(nextStatus != null) {
    if(confirm(item.bookId + '에 대한 ' + item.userName + '의 예약상태를 ['
        + item.status +']에서 ['+ nextStatus + ']로 수정하시겠습니까?')) {
      item.status = nextStatus;
      emit('update:comment', item);
    }
  }
}

// 부모로 이벤트를 전달할 때 사용할 emits 정의
const emit = defineEmits<{
  (e: 'update:comment', commentData: any): void;
}>();

const props = withDefaults(defineProps<{
  title?: string;
  edit?: boolean;
  list?: [{
    bookId: number;
    userName: string;
    commentId: number;
    createDate: string;
    detail: string;
    status: string;
  }];
}>(), {
  title: 'title',
  edit: true,
  list: () => ([{
    bookId: 1,
    userName: 'name',
    commentId: 1,
    createDate: '2025-03-03',
    detail: '대출예약',
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

const isAdmin = () => {
  switch (auth.value) {
    case 'ADMIN' : return true;
    case 'USER' || 'LOCK' : return false;
    default : return false;
  }
}

const reset =() => {
  error.value = '';
  editingItemId.value = null;
}
</script>
<template>
    <v-card elevation="10" class="withbg">
        <v-card-item class="pb-0">
            <v-card-title class="text-h5 pt-sm-2"> {{ title }}
              <span class="px-6 text-body-1 textSecondary text-no-wrap  text-error">{{ error }}
                <slot />
              </span>
            </v-card-title>
            <div class="recent-transaction mt-10 px-3">
                <div v-for="(item, index) in visibleComments" :key="item.commentId">
                    <v-row class="d-flex mb-4">
                        <v-col cols="3" lg="3" md="auto" sm="auto" class="px-0 pt-0 pb-1 d-flex align-start">
                            <h6 class="text-body-1 textSecondary text-no-wrap">{{ item.createDate }}</h6>
                        </v-col>
                        <v-col cols="2" sm="1" class="px-0 text-center pt-0 pb-0" @click="editStatus(item)">
                            <v-chip  :class="'bg-' + getStatusColor(item.status) " color="white"  size="small" >
                              {{ item.status }}
                            </v-chip>
                            <div v-if="index !== list.length - 1" class="line mx-auto bg-grey100"></div>
                        </v-col>
                        <v-col cols="7" sm="8" class="pt-0">
                            <div @click="handleClick(item)" style="cursor: pointer" class="mb-2">
                              <!-- 편집 중 -->
                              <template v-if="editingItemId === item.commentId">
                                <v-row>
                                  <v-text-field v-model="editableDetail" class="mx-2" variant="outlined" density="compact" max-width="10" hide-details color="primary"></v-text-field>
                                  <v-btn class="v-btn bg-primary v-btn--variant-tonal mx-2" small @click.stop="saveDetail(item)">저장</v-btn>
<!--                                  <v-btn class="v-btn bg-error v-btn&#45;&#45;variant-tonal" small @click.stop="resetDetail(item)">취소</v-btn>-->
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
                <div class="d-flex justify-center align-center ">
                  <a @click.prevent="showAll = !showAll" class="text-primary text-decoration-none text-body-1 opacity-1 font-weight-medium">
                    <span v-if = !showAll> 더보기 </span>
                    <span v-else> 감추기 </span>
                  </a>
                </div>
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
