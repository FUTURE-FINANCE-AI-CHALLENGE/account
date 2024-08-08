<template>
  <div class="container">
    <h1>목록 생성</h1>
    <form @submit.prevent="createAccount">
      <div class="form-group">
        <label for="title">제목:</label>
        <input v-model="account.title" id="title" type="text" required class="form-control"/>
      </div>
      <div class="form-group">
        <label for="description">설명:</label>
        <input v-model="account.description" id="description" type="text" class="form-control"/>
      </div>
      <div class="form-group">
        <label for="date">날짜:</label>
        <input v-model="account.date" id="date" type="date" required class="form-control"/>
      </div>
      <div class="form-group">
        <label for="amount">금액:</label>
        <input v-model.number="account.amount" id="amount" type="number" required class="form-control"/>
      </div>
      <div class="form-group">
        <label for="type">유형:</label>
        <select v-model="account.type" id="type" required class="form-control">
          <option value="INCOME">수입</option>
          <option value="EXPENSE">지출</option>
        </select>
      </div>
      <div class="form-group">
        <label for="category">카테고리:</label>
        <input v-model="account.category" id="category" type="text" class="form-control"/>
      </div>
      <button type="submit" class="btn btn-primary">계정 생성</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
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
        if (!store.state.isAuthenticated) {
          throw new Error('계정을 생성하려면 로그인해야 합니다.');
        }

        const formattedDate = new Date(account.value.date).toISOString().split('T')[0];

        const accountData = {
          ...account.value,
          date: formattedDate,
          userId: store.state.user.userId
        };

        const response = await axios.post('http://localhost:8080/account', accountData);

        if (response.status === 201) {
          alert('내역이 성공적으로 추가 되었습니다');
          router.push('/account');
        } else {
          throw new Error('내역 추가에 실패했습니다.');
        }
      } catch (err) {
        error.value = '계정 생성 실패: ' + err.message;
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

<style scoped>
.container {
  max-width: 500px;
  margin: 40px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
  font-size: 24px;
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

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.form-control:focus {
  border-color: #42b983;
  box-shadow: 0 0 5px rgba(66, 185, 131, 0.5);
}

.btn {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #42b983;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

.btn:hover {
  background-color: #369a6e;
}

.error {
  color: red;
  text-align: center;
  margin-top: 20px;
}
</style>
