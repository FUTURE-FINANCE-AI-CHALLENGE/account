<template>
  <div class="register-form">
    <h2>회원가입</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="userId">아이디:</label>
        <input type="text" id="userId" v-model="userId" required />
      </div>
      <div class="form-group">
        <label for="password">비밀번호:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div class="form-group">
        <label for="confirmPassword">비밀번호 확인:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>
      <div class="form-group">
        <label for="name">이름:</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      <div class="form-group">
        <label for="email">이메일:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div class="form-group">
        <label for="phoneNumber">핸드폰번호:</label>
        <input type="text" id="phoneNumber" v-model="phoneNumber" required />
      </div>
      <div class="form-group">
        <label for="role">직업:</label>
        <input type="text" id="role" v-model="role" required />
      </div>
      <div class="form-group">
        <label for="birthYear">생년월일:</label>
        <input type="text" id="birthYear" v-model="birthYear" required />
      </div>

      <!-- 성별 선택 부분 -->
      <div class="form-group">
        <label for="gender">성별:</label>
        <div class="gender-options">
          <label>
            <input type="radio" value="M" v-model="gender" /> 남성
          </label>
          <label>
            <input type="radio" value="F" v-model="gender" /> 여성
          </label>
        </div>
      </div>

      <button type="submit">회원가입완료</button>
      <button type="button" @click="handleCancel">취소</button>
      <div v-if="error" class="error">{{ error }}</div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      userId: '',
      password: '',
      confirmPassword: '', // 비밀번호 확인을 위한 데이터 변수
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
      if (this.password !== this.confirmPassword) {
        this.error = '비밀번호와 비밀번호 확인이 일치하지 않습니다.';
        return;
      }
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

        // 회원가입 완료 후 로그인 페이지로 리디렉션
        this.$router.push('/login');
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
      // 뒤로가기 기능 추가
      window.history.back();
    }
  },
  setup() {
    const router = useRouter();
    return { router };
  }
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

/* 성별 옵션을 가로로 배치 */
.gender-options {
  display: flex;
  justify-content: flex-start; /* 옵션을 왼쪽으로 정렬 */
  margin-top: 5px;
}

/* 라디오 버튼과 텍스트 크기 조정 */
.gender-options label {
  margin-right: 15px;
  font-size: 14px; /* 텍스트 크기 조정 */
}

.gender-options input {
  margin-right: 5px; /* 라디오 버튼과 텍스트 사이 간격 */
}

.error {
  color: red;
  margin-top: 10px;
}
</style>
