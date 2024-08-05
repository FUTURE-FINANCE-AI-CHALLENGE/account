<template>
  <div>
    <h1>Vue.js App</h1>
    <p>Status: {{ status }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      status: 'Loading...'
    };
  },
  created() {
    axios.get('/api/status')
        .then(response => {
          this.status = response.data.status;
        })
        .catch(error => {
          console.error('Error fetching status:', error);
          this.status = 'Error';
        });
  }
}
</script>

<style scoped>
/* 스타일 추가 */
</style>