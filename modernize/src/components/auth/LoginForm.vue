<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAuthData } from "@/data/Axios";
import { useRouter } from 'vue-router';
const checkbox = ref(true);

const router = useRouter()
const username = ref('');
const password = ref('');

onMounted(async () => {
  //로그아웃처리
  localStorage.setItem('username','');
  localStorage.setItem('auth', '');

  //저장된 id 조회
  let savedName = localStorage.getItem('savedName');
  if(savedName){
    username.value = savedName;
  }
});

const login = async () => {
  try {
    //id 저장
    if(checkbox.value){
      localStorage.setItem('savedName', username.value);
    }

    const params = new URLSearchParams();
    params.append("username", username.value);
    params.append("password", password.value);
    const response = await getAuthData(params);

    // 로그인 성공 시 처리
    console.log('Login successful:', response);
    localStorage.setItem('username', response.userName);
    localStorage.setItem('auth', response.authName);
    await router.push('/sample-page');

  } catch (error) {
    // 로그인 실패 시 처리
    await router.push('/');
  }
};
</script>

<template>
    <v-row class="d-flex mb-3">
        <v-col cols="12">
            <v-label class="font-weight-bold mb-1">Username</v-label>
            <v-text-field variant="outlined" hide-details color="primary" v-model="username"></v-text-field>
        </v-col>
        <v-col cols="12">
            <v-label class="font-weight-bold mb-1">Password</v-label>
            <v-text-field variant="outlined" type="password" hide-details color="primary"
                          v-model="password"
            ></v-text-field>
        </v-col>
        <v-col cols="12" class="pt-0">
            <div class="d-flex flex-wrap align-center ml-n2">
                <v-checkbox v-model="checkbox"  color="primary" hide-details>
                    <template v-slot:label class="text-body-1">Remeber this Device</template>
                </v-checkbox>
                <div class="ml-sm-auto">
                    <RouterLink to="/"
                        class="text-primary text-decoration-none text-body-1 opacity-1 font-weight-medium">Forgot
                        Password ?</RouterLink>
                </div>
            </div>
        </v-col>
        <v-col cols="12" class="pt-0">
            <v-btn v-on:click="login" color="primary" size="large" block   flat>Sign in</v-btn>
        </v-col>
    </v-row>
</template>
