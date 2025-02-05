<script setup lang="ts">
import {Controls} from "@braks/vue-flow";

const props = withDefaults(defineProps<{
  list?: [{
    user_name: string;
    comment_id: number;
    createDate: string;
    detail: string;
    status: string;
  }];
}>(), {
  list: () => ([{
    user_name: 'name',
    comment_id: 1,
    createDate: '2025-03-03',
    detail: 'Payment received from John Doe of $385.90',
    status: '예약'
  }])
});
// const colors = ['success', 'error', 'warning', 'info', 'primary', 'secondary'];
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
    <v-card elevation="10" class="withbg pb-0">
        <v-card-item >
            <v-card-title class="text-h5 pt-sm-2"> Comments </v-card-title>
            <div class="recent-transaction mt-10 px-3">
                <div v-for="(item, index) in list" :key="item.comment_id">
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
                        <v-col cols="5" sm="8" class="pt-0">
                            <h6 class="text-body-1 font-weight-bold">{{ item.detail }}</h6>
                        </v-col>
                    </v-row>
                </div>
            </div>
            <div class="recent-transaction mt-10 mb-3">
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
