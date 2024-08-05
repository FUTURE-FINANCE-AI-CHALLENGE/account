<template>
  <div class="account-list">
    <h2>Account List</h2>
    <button @click="fetchAccounts">Refresh</button>
    <table v-if="accounts.length">
      <thead>
      <tr>
        <th>ID</th>
        <th>User ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Date</th>
        <th>Expenses</th>
        <th>Incomes</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="account in accounts" :key="account.id">
        <td>{{ account.id }}</td>
        <td>{{ account.userId }}</td>
        <td>{{ account.title }}</td>
        <td>{{ account.description }}</td>
        <td>{{ account.date }}</td>
        <td>
          <ul>
            <li v-for="expense in account.expenses" :key="expense.id">
              {{ expense.amount }}
            </li>
          </ul>
        </td>
        <td>
          <ul>
            <li v-for="income in account.incomes" :key="income.id">
              {{ income.amount }}
            </li>
          </ul>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      accounts: [],
      error: null,
    };
  },
  methods: {
    async fetchAccounts() {
      try {
        const response = await axios.get('http://localhost:8080/account');
        this.accounts = response.data;
      } catch (error) {
        this.error = 'Failed to fetch accounts. Please try again.';
        console.error('Fetch accounts error:', error);
      }
    }
  },
  created() {
    this.fetchAccounts();
  }
}
</script>

<style scoped>
.account-list {
  max-width: 800px;
  margin: auto;
  padding: 20px;
}
button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;
}
button:hover {
  background-color: #0056b3;
}
table {
  width: 100%;
  border-collapse: collapse;
}
table, th, td {
  border: 1px solid #ddd;
}
th, td {
  padding: 8px;
  text-align: left;
}
th {
  background-color: #f4f4f4;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>
