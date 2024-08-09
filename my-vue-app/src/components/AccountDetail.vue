<template>
  <div>
    <h1>내역 상세 정보</h1>

    <form v-if="!isEditing">
      <div>
        <label for="title">제목:</label>
        <input v-model="account.title" id="title" type="text" required />
      </div>
      <div>
        <label for="date">날짜:</label>
        <input v-model="account.date" id="date" type="date" required />
      </div>
      <div>
        <label for="amount">금액:</label>
        <input v-model.number="account.amount" id="amount" type="number" required />
      </div>
      <div>
        <label for="type">유형:</label>
        <select v-model="account.type" id="type" required>
          <option value="INCOME">수입</option>
          <option value="EXPENSE">지출</option>
        </select>
      </div>
      <div>
        <label for="category">카테고리:</label>
        <input v-model="account.category" id="category" type="text" />
      </div>
      <div>
        <label for="description">설명:</label>
        <input v-model="account.description" id="description" type="text" />
      </div>
      <button @click="startEditing" type="button">편집</button>
    </form>

    <form v-else @submit.prevent="saveChanges">
      <div>
        <label for="title">제목:</label>
        <input v-model="editAccount.title" id="title" type="text" required />
      </div>
      <div>
        <label for="date">날짜:</label>
        <input v-model="editAccount.date" id="date" type="date" required />
      </div>
      <div>
        <label for="amount">금액:</label>
        <input v-model.number="editAccount.amount" id="amount" type="number" required />
      </div>
      <div>
        <label for="type">유형:</label>
        <select v-model="editAccount.type" id="type" required>
          <option value="INCOME">수입</option>
          <option value="EXPENSE">지출</option>
        </select>
      </div>
      <div>
        <label for="category">카테고리:</label>
        <input v-model="editAccount.category" id="category" type="text" />
      </div>
      <div>
        <label for="description">설명:</label>
        <input v-model="editAccount.description" id="description" type="text" />
      </div>
      <button type="submit">변경 사항 저장</button>
      <button @click="cancelEditing" type="button">취소</button>
    </form>

    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const account = ref({
      title: '',
      description: '',
      date: '',
      amount: null,
      type: 'INCOME',
      category: ''
    });
    const editAccount = ref({ ...account.value });
    const isEditing = ref(false);
    const error = ref(null);

    const fetchAccount = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/account/${route.params.userId}`);
        account.value = response.data;
        editAccount.value = { ...response.data };
      } catch (err) {
        console.error('내역 정보를 가져오는 데 실패했습니다:', err);
        error.value = '내역 정보를 가져오는 데 실패했습니다: ' + err.message;
      }
    };

    const updateAccount = async () => {
      try {
        if (!store.state.isAuthenticated) {
          throw new Error('내역을 업데이트하려면 로그인해야 합니다.');
        }

        const formattedDate = new Date(account.value.date).toISOString().split('T')[0];
        const accountData = { ...account.value, date: formattedDate };

        const response = await axios.put(`http://localhost:8080/account/${route.params.userId}`, accountData);

        if (response.status === 200) {
          alert('내역을 성공적으로 업데이트되었습니다!');
          account.value = { ...accountData };
          isEditing.value = false;
          router.push(`/account/${route.params.userId}`); // 성공 후 페이지 이동
        } else {
          throw new Error('내역 업데이트에 실패했습니다.');
        }
      } catch (err) {
        error.value = '내역 업데이트에 실패했습니다: ' + err.message;
      }
    };

    const saveChanges = async () => {
      try {
        await updateAccount();
      } catch (err) {
        console.error('변경 사항 저장에 실패했습니다:', err);
        error.value = '변경 사항 저장에 실패했습니다: ' + err.message;
      }
    };

    const startEditing = () => {
      isEditing.value = true;
    };

    const cancelEditing = () => {
      isEditing.value = false;
      router.push(`/account/${route.params.userId}`); // 편집 취소 후 페이지 이동
    };

    onMounted(fetchAccount);

    return {
      account,
      editAccount,
      isEditing,
      error,
      saveChanges,
      startEditing,
      cancelEditing
    };
  }
};
</script>

<style scoped>
div {
  margin-bottom: 16px;
}

label {
  display: block;
  margin-bottom: 4px;
}

input,
select {
  width: 100%;
  padding: 8px;
  margin-bottom: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  color: #fff;
  background-color: #007bff;
  cursor: pointer;
  margin-right: 8px;
}

button:hover {
  background-color: #0056b3;
}

p {
  color: red;
  font-weight: bold;
}
</style>
