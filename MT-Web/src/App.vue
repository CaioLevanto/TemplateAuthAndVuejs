<template>
  <div id="app">    
    <Navbar />
    <router-view />
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  components: {Navbar},
  methods: {
    
  }
};
</script>

<style lang="scss">
  @import url('https://fonts.googleapis.com/css2?family=Sora:wght@100;300&display=swap');

  body {
    background: rgba(65, 184, 131, 0.5) !important;
    background-position: center !important;
    background-size: cover !important;
    font-family: 'Sora', sans-serif !important;
    font-weight: 300;
  }
  .nav-background {
    background: #353535;
  }
</style>