<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="login">
      <input v-model="userId" placeholder="User ID" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import { useStore } from 'vuex'; // Vuex 스토어 임포트
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      password: ''
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('http://localhost:8080/users/login', {
          userId: this.userId,
          password: this.password
        });

        this.$store.dispatch('login', response.data); // Vuex에 사용자 정보 저장
        this.$router.push('/account-manager'); // 로그인 후 리디렉션
      } catch (error) {
        console.error('Login failed:', error);
      }
    }
  },
  setup() {
    const store = useStore(); // Vuex 스토어 가져오기
    return { store };
  }
};
</script>
