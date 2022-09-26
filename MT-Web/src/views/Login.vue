<template>
  <div class="h-100 color-default">
    <b-row class="w-100 h-100" align-v="center" align-h="center">
      <b-col md="8" sm="12">
        <FormLogin />
        
        <b-row align-h="center">
          <b-col class="text-center" md="8" sm="12">
            <div id="redirect-register">
              <p>
                Ainda não é cadastrado?
                <a id="link" @click.prevent="redirectRegister">Cadastre-se!</a>
              </p>
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import FormLogin from "@/components/forms/FormLogin.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  components: {
    FormLogin,
  },
  data() {
    return {};
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  methods: {
    redirectRegister() {
      this.$router.push("/register");
    },
  },
};
</script>

<style lang="scss" scoped>
#redirect-register > p,
#redirect-register > a {
  color: #fff;
}

#link {
  cursor: pointer;
}

#link:hover {
  border-bottom: 1px solid #fff;
}
</style>
