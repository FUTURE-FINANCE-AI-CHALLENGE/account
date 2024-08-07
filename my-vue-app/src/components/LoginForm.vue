<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
      <input v-model="userId" placeholder="User ID" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
      <p v-if="error" style="color: red;">{{ error }}</p>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const userId = ref('');
    const password = ref('');
    const error = ref(null);

    const handleLogin = async () => {
      try {
        // 로그인 요청을 /users/login으로 수정
        const response = await store.dispatch('login', { userId: userId.value, password: password.value });
        if (response) {
          router.push('/account');
        }
      } catch (err) {
        error.value = 'Login failed: ' + err.message;
      }
    };

    return {
      userId,
      password,
      error,
      handleLogin
    };
  }
};
</script>
