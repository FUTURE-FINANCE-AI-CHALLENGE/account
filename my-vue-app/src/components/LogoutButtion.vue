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
