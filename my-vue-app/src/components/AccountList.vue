<template>
  <div class="container">
    <h1>내역</h1>
    <AccountChart :accounts="accounts" />
    <button @click="createNewAccount" class="btn btn-primary">추가</button>
    <ul class="account-list">
      <li v-for="account in accounts" :key="account.id" class="account-item">
        <span class="account-date">{{ new Date(account.date).toLocaleDateString() }}</span>
        <router-link :to="`/account/${account.id}`" class="account-link">
          <div class="account-info">
            <span class="account-title">{{ account.title }}</span>
            <span class="account-amount">{{ account.amount }} {{ account.category }}</span>
            <span v-if="account.type === 'INCOME'" class="account-type">(수입)</span>
            <span v-if="account.type === 'EXPENSE'" class="account-type">(지출)</span>
          </div>
        </router-link>
        <button @click="deleteAccount(account.id)" class="btn btn-danger">삭제</button>
      </li>
    </ul>

    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import AccountChart from '@/components/AccountChart.vue'; // 그래프 컴포넌트 가져오기

export default {
  components: {
    AccountChart
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const accounts = ref([]);
    const error = ref(null);

    const fetchAccounts = async () => {
      try {
        if (!store.getters.isAuthenticated) {
          throw new Error('내역 목록을 보려면 로그인해야 합니다.');
        }

        const authHeader = store.getters.token ? `Bearer ${store.getters.token}` : '';

        const response = await axios.get('http://localhost:8080/account', {
          withCredentials: true,
          headers: {
            'Authorization': authHeader
          }
        });
        if (Array.isArray(response.data)) {
          accounts.value = response.data;
        } else {
          throw new Error('예상치 못한 응답 형식입니다.');
        }
      } catch (err) {
        error.value = '소비 및 지출 목록을 가져오지 못했습니다: ' + err.message;
        console.error(err);
      }
    };

    const deleteAccount = async (id) => {
      try {
        const authHeader = store.getters.token ? `Bearer ${store.getters.token}` : '';

        await axios.delete(`http://localhost:8080/account/${id}`, {
          headers: {
            'Authorization': authHeader
          }
        });
        await fetchAccounts();
      } catch (err) {
        error.value = '내역을 삭제하지 못했습니다: ' + err.message;
        console.error(err);
      }
    };

    const createNewAccount = () => {
      router.push('/account/new');
    };

    onMounted(fetchAccounts);

    return {
      accounts,
      deleteAccount,
      createNewAccount,
      error
    };
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.account-list {
  list-style-type: none;
  padding: 0;
}

.account-item {
  display: flex;
  align-items: center;
  padding: 15px 10px;
  border-bottom: 1px solid #ddd;
}

.account-date {
  width: 120px;
  color: #777;
  font-size: 14px;
  text-align: left;
  margin-right: 10px;
}

.account-link {
  text-decoration: none;
  color: #333;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex: 1;
}

.account-info {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.account-title {
  font-weight: bold;
  font-size: 16px;
}

.account-amount {
  color: #555;
  margin-top: 5px;
}

.account-type {
  color: #999;
  font-size: 14px;
  margin-top: 5px;
}

button {
  margin-left: 10px;
  padding: 5px 10px;
  font-size: 14px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
}

.btn-danger {
  background-color: #dc3545;
  color: #fff;
}

.btn:hover {
  opacity: 0.8;
}

.error {
  color: red;
  text-align: center;
  margin-top: 20px;
}
</style>
