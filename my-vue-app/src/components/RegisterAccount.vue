<template>
  <div class="register-form">
    <h2>Register Account</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="title" required />
      </div>
      <div class="form-group">
        <label for="total">Total:</label>
        <input type="number" id="total" v-model="total" required />
      </div>
      <div class="form-group">
        <label for="income">Income:</label>
        <input type="number" id="income" v-model="income" required />
      </div>
      <div class="form-group">
        <label for="expense">Expense:</label>
        <input type="number" id="expense" v-model="expense" required />
      </div>
      <div class="form-group">
        <label for="category">Category:</label>
        <input type="text" id="category" v-model="category" required />
      </div>
      <div class="form-group">
        <label for="description">Description:</label>
        <input type="text" id="description" v-model="description" required />
      </div>
      <div class="form-group">
        <label for="date">Date:</label>
        <input type="date" id="date" v-model="date" required />
      </div>
      <div class="form-group">
        <label for="userId">User ID:</label>
        <input type="text" id="userId" v-model="userId" required />
      </div>
      <button type="submit" class="btn btn-primary">Register</button>
      <div v-if="error" class="error">{{ error }}</div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '',
      total: '',
      income: '',
      expense: '',
      category: '',
      description: '',
      date: '',
      userId: '',
      error: null,
    };
  },
  methods: {
    async handleRegister() {
      try {
        const response = await axios.post('http://localhost:8080/account', {
          title: this.title,
          total: this.total,
          income: this.income,
          expense: this.expense,
          category: this.category,
          description: this.description,
          date: this.date,
          userId: this.userId
        });
        console.log('Registration successful:', response.data);
      } catch (error) {
        this.error = 'Account registration failed. Please try again.';
        console.error('Registration error:', {
          message: error.message,
          stack: error.stack,
          response: error.response ? error.response.data : 'No response data',
          config: error.config
        });
      }
    }
  },
}
</script>

<style scoped>
.register-form {
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  text-align: center;
  margin-top: 10px;
}
</style>
