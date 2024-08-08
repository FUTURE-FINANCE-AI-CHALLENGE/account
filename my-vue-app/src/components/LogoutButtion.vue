<!-- src/components/LogoutButton.vue -->
<template>
  <button @click="handleLogout">Logout</button>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    const handleLogout = () => {
      store.dispatch('logout');
      router.push('/login');
    };

    return {
      handleLogout
    };
  }
};
</script>
<style scoped>
button {
  padding: 10px 20px;
  font-size: 16px;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
