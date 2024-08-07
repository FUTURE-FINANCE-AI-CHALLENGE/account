<template>
  <div>
    <h1>Account Detail</h1>
    <div v-if="account">
      <h2>{{ account.title }}</h2>
      <p>{{ account.description }}</p>
      <p>Date: {{ new Date(account.date).toLocaleDateString() }}</p>
      <p>Amount: {{ account.amount }}</p>
      <p>Type: {{ account.type }}</p>
      <p>Category: {{ account.category }}</p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

export default {
  setup() {
    const route = useRoute();
    const store = useStore();
    const account = ref(null);

    const fetchAccount = async (userId) => {
      try {
        // Include authorization header if necessary
        const authHeader = store.getters.token ? `Bearer ${store.getters.token}` : '';

        const response = await axios.get(`http://localhost:8080/account/${userId}`, {
          headers: {
            'Authorization': authHeader
          }
        });
        account.value = response.data;
      } catch (error) {
        console.error('Failed to fetch account:', error);
      }
    };

    onMounted(() => {
      const userId = route.params.userId; // Get userId from route params
      if (userId) {
        fetchAccount(userId);
      }
    });

    return {
      account
    };
  }
};
</script>
