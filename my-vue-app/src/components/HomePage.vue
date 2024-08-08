<template>
  <div class="container">
    <h1>AI 가계부</h1>
    <p class="intro">당신의 재정을 관리하는 스마트한 방법</p>
    <div class="link-container">
      <router-link to="/login" class="btn btn-primary">로그인</router-link>
      <router-link to="/register" class="btn btn-secondary">회원가입</router-link>
    </div>
    <p v-if="message" class="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HomePage',
  data() {
    return {
      message: ''
    };
  },
  mounted() {
    axios.get('http://localhost:8080/')
        .then(response => {
          this.message = response.data;
        })
        .catch(error => {
          console.error(error);
        });
  }
};
</script>

<style scoped>
.container {
  text-align: center;
  padding: 20px;
  background-color: #f0f4f8;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  color: #42b983;
  font-size: 36px;
  margin-bottom: 10px;
}

.intro {
  font-size: 18px;
  color: #555;
  margin-bottom: 30px;
}

.link-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 20px;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
}

.btn-primary {
  background-color: #42b983;
  color: #fff;
}

.btn-secondary {
  background-color: #3498db;
  color: #fff;
}

.btn:hover {
  opacity: 0.8;
}

.message {
  margin-top: 20px;
  font-size: 16px;
  color: #333;
}
</style>
