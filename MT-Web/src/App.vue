<template>
  <div class="app">
    <Navbar />
    <div id="components">
      <router-view />
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import EventBus from "@/common/EventBus";
import Home from "./views/Home.vue";

export default {
  components: { Navbar, Home },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/");
    },
  },
  mounted() {
    EventBus.on("logout", () => {
      this.logOut();
    });
  },
  beforeDestroy() {
    EventBus.remove("logout");
  }
};
</script>