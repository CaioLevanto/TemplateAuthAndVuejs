<template>
  <b-navbar
    class="shadow sticky-top bg-light p-2"
    id="navbar"
    toggleable="lg"
    type="light"
    variant="faded"
  >
    <b-navbar-brand>MucaTour</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#" class="nav-item">
          <router-link to="/home" class="nav-link">
            Página principal
          </router-link>
        </b-nav-item>

        <b-nav-item href="#" class="nav-item" v-if="showAdminBoard">
          <router-link to="/admin" class="nav-link">Admin Board</router-link>
        </b-nav-item>

        <b-nav-item
          href="#"
          class="ml-2 mr-2 nav-item"
          v-if="showModeratorBoard"
        >
          <router-link to="/mod" class="nav-link">Moderator Board</router-link>
        </b-nav-item>

        <b-nav-item href="#" class="ml-2 mr-2 nav-item" v-if="currentUser">
          <router-link to="/user" class="nav-link">User</router-link>
        </b-nav-item>

        <b-nav-item href="#" class="ml-2 mr-2 nav-item" v-if="!loggedIn">
          <b-button variant="primary" @click.prevent="login">
            Acessar minha conta
          </b-button>
        </b-nav-item>

        <b-navbar-nav class="align-self-center mr-auto">

          <b-nav-item-dropdown v-if="loggedIn">
            <template #button-content>
              {{ currentUser.name }}
            </template>
            <b-dropdown-item href="#" @click.prevent="profile">Perfil</b-dropdown-item>
            <b-dropdown-item href="#" @click.prevent="logOut">Deslogar</b-dropdown-item>
          </b-nav-item-dropdown>

        </b-navbar-nav>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Navbar",
  data() {
    return {};
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_ADMIN");
      }
      return false;
    },
    showModeratorBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_MODERATOR");
      }
      return false;
    },
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/home");
    },
    profile() {
      this.$router.push("/profile");
    },
    login() {
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400&display=swap");

nav a {
  font-family: "Sora", sans-serif !important;
  font-weight: 400;
  font-style: normal !important;
}
</style>
