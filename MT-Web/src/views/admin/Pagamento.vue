<template>
  <b-container fluid>
    <b-row class="mt-5">
      <div class="buttons">
          <b-button variant="success" class="add-payment"  @click.prevent="createPayment">Adicionar uma novo pagamento</b-button>
      </div>
    </b-row>
  </b-container>
</template>
<script>
import UserService from '../../services/user.service';
import EventBus from "@/common/EventBus";

export default {

  name: 'Admin',
  data() {
    return {

    };
  },
  mounted() {
    UserService.getAdminBoard().then(
      response => {
        this.content = response.data;
      },
      error => {
        this.content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();
        if (error.response && error.response.status === 403) {
          EventBus.dispatch("logout");
        }
      }
    );
  },
  methods:{
    createPayment() {
      this.$router.push("/criarPagamento");
    },
  }
};
</script>
<style scoped>
.add-payment {
  margin: 0 2.5vh;
  float: right;
}
.buttons{
  width: 100%;
}
</style>