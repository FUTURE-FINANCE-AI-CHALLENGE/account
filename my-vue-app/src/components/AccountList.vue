<template>
  <div>
    <h1>Account List</h1>
    <ul>
      <li v-for="account in accounts" :key="account.id">
        <router-link :to="`/account/${account.id}`">
          {{ account.title }} - {{ account.amount }} {{ account.category }}
          <span v-if="account.type === 'INCOME'">(수입)</span>
          <span v-if="account.type === 'EXPENSE'">(지출)</span>
        </router-link>
        <span>{{ new Date(account.date).toLocaleDateString() }}</span>
        <button @click="deleteAccount(account.id)">삭제</button>
      </li>
    </ul>
    <button @click="createNewAccount">추가</button>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const accounts = ref([]);
    const error = ref(null);

    const fetchAccounts = async () => {
      try {
        // Check if the user is authenticated
        if (!store.getters.isAuthenticated) {
          throw new Error('You must be logged in to view the account list.');
        }

        // Include authorization header if necessary
        const authHeader = store.getters.token ? `Bearer ${store.getters.token}` : '';

        // Get accounts from backend
        const response = await axios.get('http://localhost:8080/account', {
          headers: {
            'Authorization': authHeader
          }
        });
        if (Array.isArray(response.data)) {
          accounts.value = response.data;
        } else {
          throw new Error('Unexpected response format.');
        }
      } catch (err) {
        error.value = 'Failed to fetch accounts: ' + err.message;
        console.error(err);
      }
    };

    const deleteAccount = async (id) => {
      try {
        // Include authorization header if necessary
        const authHeader = store.getters.token ? `Bearer ${store.getters.token}` : '';

        await axios.delete(`http://localhost:8080/account/${id}`, {
          headers: {
            'Authorization': authHeader
          }
        });
        await fetchAccounts(); // Refresh the list after deletion
      } catch (err) {
        error.value = 'Failed to delete account: ' + err.message;
        console.error(err);
      }
    };

    const createNewAccount = () => {
      router.push('/account/new');
    };

    onMounted(fetchAccounts);

    return {
      accounts,
      deleteAccount,
      createNewAccount,
      error
    };
  }
};
</script>
