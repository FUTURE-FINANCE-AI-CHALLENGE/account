<template>
  <div>
    <h1>Account Manager</h1>

    <div>
      <h2>Create Account</h2>
      <form @submit.prevent="createAccount">
        <input v-model="newAccount.title" placeholder="Title" required />
        <input v-model="newAccount.description" placeholder="Description" />
        <input v-model="newAccount.date" type="date" required />
        <input v-model="newAccount.userId" placeholder="User ID" required />
        <button type="submit">Create</button>
      </form>
    </div>

    <div>
      <h2>Account List</h2>
      <input v-model="searchUserId" placeholder="Search User ID" />
      <button @click="fetchAccounts">Search</button>
      <ul>
        <li v-for="account in accounts" :key="account.id">
          <div>
            <p><strong>Title:</strong> {{ account.title }}</p>
            <p><strong>Description:</strong> {{ account.description }}</p>
            <p><strong>Date:</strong> {{ account.date }}</p>
            <p><strong>User ID:</strong> {{ account.userId }}</p>
            <button @click="editAccount(account)">Edit</button>
            <button @click="deleteAccount(account.userId)">Delete</button>
          </div>
        </li>
      </ul>
    </div>

    <div v-if="selectedAccount">
      <h2>Edit Account</h2>
      <form @submit.prevent="updateAccount">
        <input v-model="selectedAccount.title" placeholder="Title" required />
        <input v-model="selectedAccount.description" placeholder="Description" />
        <input v-model="selectedAccount.date" type="date" required />
        <input v-model="selectedAccount.userId" placeholder="User ID" required />
        <button type="submit">Update</button>
        <button @click="cancelEdit">Cancel</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      newAccount: {
        title: '',
        description: '',
        date: '',
        userId: ''
      },
      selectedAccount: null,
      accounts: [],
      searchUserId: ''
    };
  },
  methods: {
    async createAccount() {
      try {
        await axios.post('http://localhost:8080/account', this.newAccount);
        this.newAccount = {
          title: '',
          description: '',
          date: '',
          userId: ''
        };
        this.fetchAccounts();
      } catch (error) {
        console.error("Failed to create account:", error);
      }
    },
    async fetchAccounts() {
      try {
        const response = await axios.get(`http://localhost:8080/account?userId=${this.searchUserId}`);
        this.accounts = response.data; // assuming the response data is a list of accounts
      } catch (error) {
        console.error("Failed to fetch accounts:", error);
      }
    },
    async editAccount(account) {
      this.selectedAccount = { ...account };
    },
    async updateAccount() {
      try {
        await axios.put('http://localhost:8080/account', this.selectedAccount);
        this.selectedAccount = null;
        this.fetchAccounts();
      } catch (error) {
        console.error("Failed to update account:", error);
      }
    },
    async deleteAccount(userId) {
      try {
        await axios.delete(`http://localhost:8080/account/${userId}`);
        this.fetchAccounts();
      } catch (error) {
        console.error("Failed to delete account:", error);
      }
    },
    cancelEdit() {
      this.selectedAccount = null;
    }
  }
};
</script>

<style scoped>
/* 스타일 추가 */
</style>
