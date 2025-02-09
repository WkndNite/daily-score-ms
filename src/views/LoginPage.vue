<template>
  <div class="login-page">
    <login-form :type/>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import LoginForm from '../components/login/LoginForm.vue'
const type = ref('accountLogin')
</script>

<style scoped>
.login-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(90deg, #adc0fe, #ecebe6);
}
</style>
