<template>
  <b-container>
    <b-row align-h="center" class="mt-5">
      <b-col>
        <FormRegister />
      </b-col>
      <b-col class="text-center" md="12">
        <div id="redirect-login">
          <p>Já sou cadastro. <a id="link" @click.prevent="redirectLogin">Entre aqui!</a>
          </p>
        </div>
      </b-col>
    </b-row>
  </b-container>
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
      this.$router.push("/profile");
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

</style>
