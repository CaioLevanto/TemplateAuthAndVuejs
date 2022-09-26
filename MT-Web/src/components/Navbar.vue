<template>
  <b-navbar
    class="shadow sticky-top bg-light p-2"
    id="navbar"
    toggleable="lg"
    type="light"
    variant="faded"
  >
    <b-navbar-brand>MucaTour</b-navbar-brand>
      
    <b-nav-item class="d-flex nav-item mr-auto" v-if="showAdminBoard">
      <b-button variant="warning" v-b-toggle.sidebar-right>
        Pendência
      </b-button>
      
      <b-sidebar id="sidebar-right" title="Pendências" right shadow>
        <div class="px-3 py-2">
          <b-container>
            <b-row>
              <b-col>
                
              </b-col>
            </b-row>
          </b-container>
        </div>
      </b-sidebar>
    </b-nav-item>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="w-100">
        <b-nav-item href="#" class="nav-item">
          <router-link to="/" class="nav-link">
            Página principal
          </router-link>
        </b-nav-item>

        <b-nav-item class="d-flex nav-item" v-if="showAdminBoard">
          <router-link :to="{ name: 'roteiro' }" class="nav-link">Roteiro</router-link>
        </b-nav-item>

        <b-nav-item class="ml-2 mr-2 nav-item" v-if="showModeratorBoard">
          <router-link to="/mod" class="nav-link">Moderator Board</router-link>
        </b-nav-item>

        <b-nav-item class="ml-2 mr-2 nav-item" v-if="currentUser && !showAdminBoard">
          <router-link to="/user" class="nav-link">User</router-link>
        </b-nav-item>

        <b-nav-item class="ml-2 mr-2 nav-item ml-auto" v-if="!loggedIn">
          <b-button variant="primary" @click.prevent="login">
            Acessar minha conta
          </b-button>
        </b-nav-item>

        <b-nav-item class="align-self-center ml-auto" v-if="loggedIn">
          <b-nav-item-dropdown>
            <template #button-content>
              {{ currentUser.name }}
            </template>
            <b-dropdown-item href="#" @click.prevent="profile">Perfil</b-dropdown-item>
            <b-dropdown-item href="#" v-if="showAdminBoard" @click.prevent="configurations">Configurações</b-dropdown-item>
            <b-dropdown-item href="#" @click.prevent="logOut">Deslogar</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Navbar",
  data() {
    return {
      items: [],
      routes: this.$router.options.routes
    };
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
    },
    profile() {
      this.$router.push("/profile");
    },
    login() {
      this.$router.push("/login");
    },
    configurations() {
      this.$router.push("/configurations")
    }
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
