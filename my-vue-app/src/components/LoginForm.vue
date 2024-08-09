<!-- src/components/LoginForm.vue -->
<template>
  <div>
    <h1>로그인</h1>
    <form @submit.prevent="handleLogin">
      <input v-model="userId" placeholder="회원 아이디" required />
      <input v-model="password" type="password" placeholder="비밀번호" required />
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
        await store.dispatch('login', { userId: userId.value, password: password.value });
        router.push('/account');
      } catch (err) {
        error.value = '아이디나 비밀번호가 틀렸습니다 ' ;
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
<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

h1 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #42b983;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

input {
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

button {
  padding: 10px;
  font-size: 16px;
  color: #fff;
  background-color: #42b983;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #369a6e;
}

.error {
  color: red;
  text-align: center;
  margin-top: 10px;
}
</style>