<template>
  <div>
    <h1>Account List</h1>
    <ul>
      <li v-for="account in accounts" :key="account.id">
        <router-link :to="`/account/${account.userId}`">
          {{ account.title }} - {{ account.amount }} {{ account.category }}
          <span v-if="account.type === 'INCOME'">(수입)</span>
          <span v-if="account.type === 'EXPENSE'">(지출)</span>
        </router-link>
        <span>{{ new Date(account.date).toLocaleDateString() }}</span>
        <button @click="deleteAccount(account.userId)">삭제</button>
      </li>
    </ul>
    <button @click="createNewAccount">추가</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      accounts: []
    };
  },
  async created() {
    await this.fetchAccounts();
  },
  methods: {
    async fetchAccounts() {
      try {
        const response = await axios.get('http://localhost:8080/account');
        this.accounts = response.data;
      } catch (error) {
        console.error('Failed to fetch accounts:', error);
      }
    },
    async deleteAccount(userId) {
      try {
        await axios.delete(`http://localhost:8080/account/${userId}`);
        await this.fetchAccounts(); // Refresh the list after deletion
      } catch (error) {
        console.error('Failed to delete account:', error);
      }
    },
    createNewAccount() {
      this.$router.push('/account/new');
    }
  }
};
</script>
