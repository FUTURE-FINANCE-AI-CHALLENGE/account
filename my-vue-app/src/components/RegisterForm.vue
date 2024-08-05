<template>
  <div class="register-form">
    <h2>Register</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="userId">User ID:</label>
        <input type="text" id="userId" v-model="userId" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div class="form-group">
        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" v-model="phoneNumber" required />
      </div>
      <div class="form-group">
        <label for="gender">Gender:</label>
        <input type="text" id="gender" v-model="gender" required />
      </div>
      <div class="form-group">
        <label for="role">Role:</label>
        <input type="text" id="role" v-model="role" required />
      </div>
      <div class="form-group">
        <label for="birthYear">Birth Year:</label>
        <input type="text"   id="birthYear" v-model="birthYear" required />
      </div>
      <button type="submit">Register</button>
      <div v-if="error" class="error">{{ error }}</div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      password: '',
      name: '',
      email: '',
      phoneNumber: '',
      gender: '',
      role: '',
      birthYear: '',
      error: null,
    };
  },
  methods: {
    async handleRegister() {
      try {
        const response = await axios.post('http://localhost:8080/users/register', {
          userId: this.userId,
          password: this.password,
          name: this.name,
          phoneNumber: this.phoneNumber,
          gender: this.gender,
          role: this.role,
          email: this.email,
          birthYear: this.birthYear
        });
        console.log('Registration successful:', response.data);
      } catch (error) {
        this.error = '회원가입에 실패했습니다. 다시 시도해 주세요.';
        // 에러 정보를 자세히 로깅
        console.error('Registration error:', {
          message: error.message,
          stack: error.stack,
          response: error.response ? error.response.data : '응답 데이터 없음',
          config: error.config
        });
      }
    }
  },
}
</script>

<style scoped>
.register-form {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  display: block;
  margin-bottom: 5px;
}
.form-group input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
.error {
  color: red;
  margin-top: 10px;
}
</style>