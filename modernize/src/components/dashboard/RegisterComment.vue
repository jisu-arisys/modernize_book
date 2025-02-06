<script setup lang="ts">
import { ref, computed } from 'vue';

const error = ref('');
const createDetail = ref<string>('');
const username =  computed(()=> {
  return localStorage.getItem('username');
});
const ableCreate = computed(()=>{
  if (props.bookId == 0){
    error.value = '도서 정보가 없습니다.'
    return false;
  }

  const auth : string | null = localStorage.getItem('auth');
  const able : boolean = ( auth === 'USER' || auth === 'ADMIN' ) ? true : false;
  if(!able) {
    error.value = '새로운 댓글을 작성할 권한이 없습니다.'
  }
  reset();
  return able;
});

const reset =() => {
  error.value = '';
  createDetail.value = '';
}

const emit = defineEmits<{
  (e: 'insert:comment', commentData: any): void;
}>();

const createComment = () => {
  const today = new Date();
  let comment ={
    bookId: props.bookId,
    userName: username.value,
    commentId: -1,
    startLending: null,
    endLending: null,
    createDate: today,
    update_date: today,
    detail: createDetail.value,
    status: '예약'
  };
  emit('insert:comment', comment);
}

const props = withDefaults(defineProps<{
  bookId?: number;
}>(), {
  bookId: 0
});

</script>
<template>
  <v-card elevation="10" class="withbg">
    <v-card-item class="pb-0">
      <v-card-title class="text-h5 pt-sm-2"> Register Comment
        <span class="px-6 text-body-1 textSecondary text-no-wrap  text-error">{{ error }}
          <slot />
        </span>
      </v-card-title>
      <v-row class="ma-3">
          <v-col cols="10">
            <v-label class="text-primary font-weight-bold mb-1">{{ username }}</v-label>
            <v-text-field v-model="createDetail" variant="outlined" hide-details color="primary"
                          :disabled="!ableCreate">
            </v-text-field>
          </v-col>
          <v-col cols="2" sm="2" class="d-flex justify-center align-center">
            <v-label class="text-muted font-weight-bold mb-1">{{ bookId }}</v-label>
            <v-btn @click="createComment" :disabled="!ableCreate" color="primary" size="large" flat>Sign up</v-btn>
          </v-col>
      </v-row>
    </v-card-item>
  </v-card>
</template>
