<template>
  <div>
    <h1>Home Page</h1>
    <router-link to="/login">Login</router-link>
    <router-link to="/register">Register</router-link>

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
h1 {
  color: #42b983;
}
</style>