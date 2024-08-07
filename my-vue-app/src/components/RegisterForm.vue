<template>
  <div class="register-form">
    <h2>Register</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="userId">아이디:</label>
        <input type="text" id="userId" v-model="userId" required />
      </div>
      <div class="form-group">
        <label for="password">비밀번호:</label>
        <input type="password" id="password" v-model="password" required/>
      </div>
      <div class="form-group">
        <label for="name">이름:</label>
        <input type="text" id="name" v-model="name" required/>
      </div>
      <div class="form-group">
        <label for="email">이메일:</label>
        <input type="email" id="email" v-model="email" required/>
      </div>
      <div class="form-group">
        <label for="phoneNumber">핸드폰번호:</label>
        <input type="text" id="phoneNumber" v-model="phoneNumber" required/>
      </div>
      <div class="form-group">
        <label>성별:</label>
        <div>
          <label>
            <input type="radio" value="M" v-model="gender"/> 남성
          </label>
          <label>
            <input type="radio" value="F" v-model="gender"/> 여성
          </label>
        </div>
      </div>
      <div class="form-group">
        <label for="role">직업:</label>
        <input type="text" id="role" v-model="role" required/>
      </div>
      <div class="form-group">
        <label for="birthYear">생년월일:</label>
        <input type="text" id="birthYear" v-model="birthYear" required/>
      </div>
      <button type="submit">회원가입완료</button>
      <button type="button" @click="handleCancel">취소</button>
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
      gender: '', // 성별을 위한 데이터 변수
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
          gender: this.gender, // 성별 값을 서버로 전송
          role: this.role,
          email: this.email,
          birthYear: this.birthYear
        });
        console.log('Registration successful:', response.data);
      } catch (error) {
        this.error = '회원가입에 실패했습니다. 다시 시도해 주세요.';
        console.error('Registration error:', {
          message: error.message,
          stack: error.stack,
          response: error.response ? error.response.data : '응답 데이터 없음',
          config: error.config
        });
      }
    },
    handleCancel() {
      // 폼을 초기화
      this.userId = '';
      this.password = '';
      this.name = '';
      this.email = '';
      this.phoneNumber = '';
      this.gender = '';
      this.role = '';
      this.birthYear = '';

      // 또는 페이지를 다른 경로로 리디렉션할 수도 있습니다.
      // 예: this.$router.push('/home'); // 예를 들어 홈으로 리디렉션
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
  margin-right: 10px; /* Add some spacing between buttons */
}

button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>
