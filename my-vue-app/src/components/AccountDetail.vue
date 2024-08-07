<template>
  <div>
    <h1>Account Detail</h1>
    <div v-if="account">
      <h2>{{ account.title }}</h2>
      <p>{{ account.description }}</p>
      <p>Date: {{ account.date }}</p>
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

export default {
  props: {
    userId: String // URL 파라미터로 전달된 userId
  },
  data() {
    return {
      account: null
    };
  },
  async created() {
    if (this.userId) {
      await this.fetchAccount(this.userId);
    }
  },
  methods: {
    async fetchAccount(userId) {
      try {
        const response = await axios.get(`http://localhost:8080/account/${userId}`);
        this.account = response.data;
      } catch (error) {
        console.error('Failed to fetch account:', error);
      }
    }
  }
};
</script>
