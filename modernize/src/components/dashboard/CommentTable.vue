<script setup lang="ts"> //Composition API 방식 :
// setup() 내부에서는 this 가 없음, getCurrentInstance() 로 호출해야 글로벌변수 사용가능
import Comments from "@/components/shared/Comments.vue";
import { onMounted, watch} from 'vue';
import { useComment } from "@/data/Comment";
const { error, comments, getComments, handleSaveComment } = useComment();

onMounted(async () => {
  await getComments(props.getUrl);
});

// 부모에서 `props` 값이 바뀔 때마다 axios 요청
watch(() => props.getUrl, async (newData) => {
  if (newData.length > 0) {
    await getComments(newData);
  }
}, { deep: false });

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
    <Comments :title="title" :edit="edit" :list="comments"
              @update:comment="(commentData : any) => handleSaveComment(getUrl, commentData)">
      <span>{{ error }}</span>
    </Comments>
  </v-card>
</template>

<style scoped lang="scss">

</style>