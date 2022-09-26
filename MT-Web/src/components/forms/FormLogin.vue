<template>
  <b-container>
    <b-card class="border-0 shadow p-3 mb-5 bg-white rounded">
      <b-card-body>
        <b-form name="form" @submit.stop.prevent>
          <b-form-group label-for="email" class="mb-4 py-2">
            <b-form-input
              v-model="user.email"
              @change="$v.user.email.$touch()"
              type="email"
              aria-describedby="input-live-help input-live-feedback"
              :class="[{ 'is-invalid': $v.user.email.$error }, 'form-control']"
              placeholder="E-mail"
              name="email"
            />
            <b-form-invalid-feedback id="input-live-feedback">
              Informe um e-mail no formato correto.
            </b-form-invalid-feedback>
          </b-form-group>

          <b-form-group label-for="password" class="mb-4 py-2">
            <b-form-input
              v-model="user.password"
              @change="$v.user.password.$touch()"
              type="password"
              placeholder="Senha"
              aria-describedby="input-live-help input-live-feedback"
              :class="[
                { 'is-invalid': $v.user.password.$error },
                'form-control',
              ]"
              name="password"
            />
            <b-form-invalid-feedback id="input-live-feedback">
              Senha deve conter no mínimo 6 dígitos.
            </b-form-invalid-feedback>
          </b-form-group>

          <b-form-group>
            <b-button
              variant="outline-success"
              type="submit"
              @click.prevent="$v.$invalid ? $v.$touch() : handleLogin()"
              :disabled="loading"
              style="width: 150px"
            >
              <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
              ></span>
              <span> Entrar</span>
            </b-button>
          </b-form-group>
        </b-form>
      </b-card-body>
    </b-card>
  </b-container>
</template>

<script>
import User from "@/models/user";
import { required, email, minLength } from "vuelidate/lib/validators";

export default {
  name: "FormLogin",
  data() {
    return {
      user: new User(),
      loading: false,
      message: "",
    };
  },
  validations: {
    user: {
      email: {
        required,
        email,
      },
      password: {
        required,
        minLength: minLength(6),
      },
    },
  },
  methods: {
    handleLogin() {
      this.loading = true;

      this.$store.dispatch("auth/login", this.user).then(
        () => {
          this.$router.push("/");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
        }
      );
    },
    redirectRegister() {
      this.$router.push("/register");
    },
  },
};
</script>
