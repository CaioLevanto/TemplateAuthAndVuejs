<template>
  <div class="responsive-body color-default">
    <b-row class="h-100 w-100 m-0" align-v="center" align-h="center">
      <FormRegister />

      <b-col class="text-center" md="12">
        <div id="redirect-login" v-if="!loggedIn">
          <p>Já sou cadastro. <a id="link" @click.prevent="redirectLogin">Entre aqui!</a>
          </p>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import FormRegister from "@/components/forms/FormRegister.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Register",
  components: { FormRegister },
  data() {
    return {};
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/");
    }
  },
  methods: {
    redirectLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>

#redirect-login > p,
#redirect-login > a{
  color: #fff;
}

#link {
  cursor: pointer;
}

#link:hover {
  border-bottom: 1px solid #fff;
}

.responsive-body {
  height: 100%;
}

@media screen and (max-width: 766px)  {
  .responsive-body {
    height: auto;
  }
}


</style>
