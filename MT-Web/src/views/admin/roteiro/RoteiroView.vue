<template>
  <div class="background-dm">
    <div class="w-100 h-100 d-flex flex-column justify-content-center">
      <b-container fluid id="container-view">
        <router-link
          v-for="opt in menuOptions"
          :key="opt.name"
          :to="opt.path + idRoteiro"
          class="opt-card text-center nav-link align-items-center"
        >
          <b-card class="w-100 h-100 p-2 border-0">
            <b-card-body
              class="h-100 d-flex flex-column justify-content-center align-items-center"
            >
              <font-awesome-icon :icon="['fas', opt.icon]" />
              <h5 class="text-router">{{ opt.name }}</h5>
            </b-card-body>
          </b-card>
        </router-link>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RoteiroView",
  data() {
    return {
      content: "",
      menuOptions: [
        { name: "Itinerário", icon: "plane", path: "/itinerario/" },
        { name: "Pagamento", icon: "money-bill", path: "/pagamento/" },
        { name: "Informações", icon: "user", path: "/cliente/" },
        { name: "Chat", icon: "comment", path: "/chat/" },
      ],
      idRoteiro: this.$route.params.id,
    };
  },
};
</script>

<style scoped>
#container-view {
  width: 80%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.opt-card {
  font-size: 20px;
  width: 30%;
  margin: 45px;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 6%);
  transition: 200ms;
  border: 1px solid rgba(0, 0, 0, 0.175);
  border-radius: 0.375rem;
  color: #3d3692;
}

@media screen and (max-width: 1800px) {
  .opt-card {
    margin: 30px;
  }
}

@media screen and (max-width: 1300px) {
  .opt-card {
    margin: 20px;
  }
}

@media screen and (max-width: 800px) {
  .opt-card {
    width: 80%;
    margin: 15px;
  }

  #container-view {
    display: inline-flex;
    align-items: center;
  }
}

@media screen and (max-width: 550px) {
  .opt-card {
    width: 100%;
    margin: 10px;
  }

  #container-view {
    display: inline-flex;
    align-items: center;
  }
}

.opt-card svg {
  font-size: 40px;
}

.opt-card:hover {
  transition: 200ms;
  transform: scale(1.02);
}

.text-router {
  text-decoration: none;
  margin: 0;
  padding-top: 10px;
}
</style>
