<template>
  <div>
    <h1>Create Account</h1>
    <form @submit.prevent="createAccount">
      <div>
        <label for="title">Title:</label>
        <input v-model="account.title" id="title" type="text" required />
      </div>
      <div>
        <label for="description">Description:</label>
        <input v-model="account.description" id="description" type="text" />
      </div>
      <div>
        <label for="date">Date:</label>
        <input v-model="account.date" id="date" type="date" required />
      </div>
      <div>
        <label for="amount">Amount:</label>
        <input v-model.number="account.amount" id="amount" type="number" required />
      </div>
      <div>
        <label for="type">Type:</label>
        <select v-model="account.type" id="type" required>
          <option value="INCOME">Income</option>
          <option value="EXPENSE">Expense</option>
        </select>
      </div>
      <div>
        <label for="category">Category:</label>
        <input v-model="account.category" id="category" type="text" />
      </div>
      <button type="submit">Create Account</button>
    </form>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const account = ref({
      title: '',
      description: '',
      date: '',
      amount: null,
      type: 'INCOME',
      category: ''
    });

    const error = ref(null);

    const createAccount = async () => {
      try {
        // 인증된 사용자만 계정 생성 가능하도록 체크
        if (!store.state.isAuthenticated) {
          throw new Error('You must be logged in to create an account.');
        }

        // Convert date to ISO string format
        const formattedDate = new Date(account.value.date).toISOString().split('T')[0];

        // Prepare data for submission
        const accountData = {
          ...account.value,
          date: formattedDate,
          userId: store.state.user.userId // 서버에서 사용할 사용자 ID
        };

        // POST request to backend API
        const response = await axios.post('http://localhost:8080/account', accountData);

        if (response.status === 201) {
          alert('Account created successfully!');
          // Redirect to another page or clear the form
          router.push('/account');
        } else {
          throw new Error('Failed to create account.');
        }
      } catch (err) {
        error.value = 'Failed to create account: ' + err.message;
      }
    };

    return {
      account,
      createAccount,
      error
    };
  }
};
</script>
