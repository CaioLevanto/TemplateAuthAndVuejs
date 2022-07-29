<template>
  <b-container>    
    <b-row align-h="center" class="mt-5">
      <b-col lg="6" md="8" sm="12">
        <FormLogin />
      </b-col>
      <b-col class="text-center" md="12">
        <div id="redirect-register">
          <p>Ainda não é cadastrado? <a id="link" @click.prevent="redirectRegister">Cadastre-se!</a></p>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import FormLogin from '@/components/forms/FormLogin.vue'


export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  components: {
    FormLogin
  },
  data() {
    return {
    };
  },
  methods: {
    redirectRegister() {
        this.$router.push('/register')
    }
  }
};
</script>

<style scoped>

#redirect-register > p,
#redirect-register > a{
  color: #fff;
}

#link {
  cursor: pointer;
}

#link:hover {
  border-bottom: 1px solid #fff;
}

</style>