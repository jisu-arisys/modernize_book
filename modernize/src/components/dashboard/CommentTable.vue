<script setup lang="ts"> //Composition API 방식 :
// setup() 내부에서는 this 가 없음, getCurrentInstance() 로 호출해야 글로벌변수 사용가능
import Comments from "@/components/shared/Comments.vue";
import {getData, postData} from "@/data/Axios";
import {ref, onMounted, watch} from 'vue';

const error = ref<string>('');
const comments = ref([]);

onMounted(async () => {
  await getComments(props.getUrl);
});

// 부모에서 `props` 값이 바뀔 때마다 axios 요청
watch(() => props.getUrl, async (newData) => {
  if (newData.length > 0) {
    await getComments(newData);
  }
}, { deep: false });

const getComments = async (getUrl: string) => {
  if(getUrl.length == 0){
    error.value = '데이터 조회 실패 :' + getUrl;
    return;
  } else {
    error.value = '';
  }

  try {
    console.debug("commentTable reLending")
    comments.value = await getData(getUrl);
  } catch (e) {
    error.value = '데이터 조회 실패:'
  }
}

const handleSaveDetail = async (commentData : any ) => {
  console.debug(commentData);
  try {
    const response = await postData('/update/comment', commentData);
    error.value = '저장 성공:';
    comments.value = response;
  } catch (e) {
    error.value = '저장 실패:';
  }
};

const props = withDefaults(defineProps<{
  title?: string;
  edit?: boolean;
  getUrl?: string;
}>(), {
  title: 'Comment List',
  edit: true,
  getUrl: '/commentList'
});

</script>

<template>
  <v-card elevation="10" class="">
    <Comments :title="title" :edit="edit" :list="comments" @update:comment="handleSaveDetail">
      <span>{{ error }}</span>
    </Comments>
  </v-card>
</template>

<style scoped lang="scss">

</style>