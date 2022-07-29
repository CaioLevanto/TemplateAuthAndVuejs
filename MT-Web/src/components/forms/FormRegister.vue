<!-- eslint-disable vue/no-duplicate-attributes -->
<template>
  <b-container>
    <b-alert
      id="message-error-callback"
      fade
      variant="danger"
      class="shadow"
      :show="dismissCountDown"
      @dismiss-count-down="countDownChanged"
      >{{ this.errorMessage }}</b-alert
    >
    <b-progress 
      :value="this.progress" 
      :max="2" 
      class="mb-3"
      striped 
      :animated="animate"
    />
    <b-form name="register" @submit.stop.prevent>
      <b-card
        v-if="card === 'info'"
        title="Informações Iniciais"
        class="border-0 shadow p-3 mb-5 bg-white rounded"
      >
        <b-card-body>
          <b-row>
            <b-col class="mt-2" lg="4" md="6" sm="12">
              <b-form-group label-for="name" class="mb-2 py-2">
                <template v-slot:label>
                  Primeiro nome<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.firstName.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.firstName.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="name"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Primeiro Nome é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" lg="4" md="6" sm="12">
              <b-form-group label-for="name" class="mb-2 py-2">
                <template v-slot:label>
                  Sobrenome<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.secondName.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.firstName.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="name"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Nome é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" lg="4" md="12" sm="12">
              <b-form-group label-for="email" class="mb-2 py-2">
                <template v-slot:label>
                  E-mail<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.user.email.$model"
                  type="email"
                  :class="[
                    { 'is-invalid': $v.user.email.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="email"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  E-mail é um campo obrigatório.
                </b-form-invalid-feedback>
                <b-form-text id="input-live-help">
                  Informe e-mail válido.
                </b-form-text>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col class="mt-2" lg="4" md="6" sm="12">
              <b-form-group label-for="phone" class="mb-2 py-2">
                <template v-slot:label>
                  Telefone<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.user.phone.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.user.phone.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="phone"
                  v-mask="'(##) #####-####'"
                  @keypress="filtroTeclas"
                  placeholder="(00) 00000-0000"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Telefone é um campo obrigatório.
                </b-form-invalid-feedback>
                <b-form-text id="input-live-help">
                  Insira um número de telefone válido.
                </b-form-text>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" lg="4" md="6" sm="12">
              <b-form-group label-for="password" class="mb-2 py-2">
                <template v-slot:label>
                  Senha<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.password.$model"
                  type="password"
                  :class="[
                    { 'is-invalid': $v.password.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="password"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  A senha informada não é válida.
                </b-form-invalid-feedback>
                <b-form-text id="input-live-help">
                  A senha deve conter de 6 á 40 digitos.
                </b-form-text>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" lg="4" md="6" sm="12">
              <b-form-group label-for="password-validate" class="mb-2 py-2">
                <template v-slot:label>
                  Confirme sua Senha<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.confirmation_password.$model"
                  type="password"
                  :class="[
                    { 'is-invalid': $v.confirmation_password.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="confirmation_password"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  As senhas não se coincidem.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row class="mt-3" align-h="end">
            <b-col md="2" sm="2">
              <b-button
                class="btn-prox right"
                variant="outline-success"
                @click.prevent="proxCard('personal')"
                id="btn-info"
              >
                Próximo
              </b-button>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>

      <b-card
        v-if="card === 'personal'"
        title="Dados pessoais"
        class="border-0 shadow p-3 mb-5 bg-white rounded"
      >
        <b-card-body>
          <b-row>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="cpf" class="mb-2 py-2">
                <template v-slot:label>
                  CPF<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.personal.cpf.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.personal.cpf.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="cpf"
                  v-mask="'###.###.###-##'"
                  @keypress="filtroTeclas"
                  placeholder="000.000.000-00"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  CPF é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="birthday" class="mb-2 py-2">
                <template v-slot:label>
                  Data de Nascimento<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.personal.birthday.$model"
                  placeholder="Informe sua data de nascimento."
                  :class="[
                    { 'is-invalid': $v.personal.birthday.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  v-mask="'##/##/####'"
                  name="birthday"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Data de nascimento é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="rg" class="mb-2 py-2">
                <template v-slot:label>
                  RG<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.personal.rg.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.personal.rg.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  @keypress="filtroTeclas"
                  name="rg"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  RG é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row class="mt-2">
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="passportNumber" class="mb-2 py-2">
                <template v-slot:label>
                  Número de Passaporte<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.personal.passportNumber.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.personal.passportNumber.$error },
                    'form-control',
                  ]"
                  name="passportNumber"
                />
              </b-form-group>
              <b-form-invalid-feedback id="input-live-feedback">
                Número de Passaporte é um campo obrigatório.
              </b-form-invalid-feedback>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="radio-inline" class="mb-2 py-2">
                <template v-slot:label>
                  Carteira de motorista<span class="text-danger">*</span>
                </template>
                <b-form-radio-group
                  v-model="$v.personal.driverPass.$model"
                  @change="clearDriverNumberNotHasPerm"
                  name="plain-inline"
                  class="mt-2"
                  plain
                >
                  <b-form-radio name="driverPass" :value="true"
                    >Sim</b-form-radio
                  >
                  <b-form-radio name="driverPass" :value="false"
                    >Não</b-form-radio
                  >
                </b-form-radio-group>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12" v-if="personal.driverPass">
              <b-form-group label-for="driverNumber" class="mb-2 py-2">
                <template v-slot:label>
                  Número da Carteira de Motorista<span class="text-danger"
                    >*</span
                  >
                </template>
                <b-form-input
                  v-model="$v.personal.driverNumber.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.personal.driverNumber.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  @keypress="filtroTeclas"
                  name="driverNumber"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Número da Carteira de Motorista é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row align-h="end">
            <b-col class="mt-3">
              <b-button
                variant="outline-primary"
                @click.prevent="proxCard('info')"
                id="btn-return-info"
              >
                Voltar
              </b-button>

              <b-button
                variant="outline-success"
                @click.prevent="proxCard('address')"
                id="btn-personal"
                class="right"
              >
                Próximo
              </b-button>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>

      <b-card
        v-if="card === 'address'"
        title="Endereço"
        class="border-0 shadow p-3 mb-5 bg-white rounded"
      >
        <b-card-body>
          <!-- outros campos -->
          <b-row>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="cep" class="mb-2 py-2">
                <template v-slot:label> CEP </template>
                <b-form-input
                  v-model="$v.address.cep.$model"
                  type="text"
                  class="form-control"
                  name="cep"
                  @keypress="filtroTeclas"
                  @blur="getCEP"
                />
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="city" class="mb-2 py-2">
                <template v-slot:label>
                  Cidade<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.address.city.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.address.city.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="city"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Cidade é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="state" class="mb-2 py-2">
                <template v-slot:label>
                  Estado<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.address.state.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.address.state.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="state"
                  autocomplete="off"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Estado é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col class="mt-2" md="8" sm="12">
              <b-form-group label-for="addressName" class="mb-2 py-2">
                <template v-slot:label>
                  Endereço<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.address.addressName.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.address.addressName.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="addressName"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Endereço é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="numberAddress" class="mb-2 py-2">
                <template v-slot:label>
                  Número do Endereço<span class="text-danger">*</span>
                </template>
                <b-form-input
                  v-model="$v.address.numberAddress.$model"
                  type="text"
                  :class="[
                    { 'is-invalid': $v.address.numberAddress.$error },
                    'form-control',
                  ]"
                  aria-describedby="input-live-help input-live-feedback"
                  name="numberAddress"
                />
                <b-form-invalid-feedback id="input-live-feedback">
                  Número do endereço é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row class="mt-4" align-h="end">
            <b-col>
              <b-button
                variant="outline-primary"
                @click.prevent="proxCard('personal')"
                id="btn-return-personal"
              >
                Voltar
              </b-button>
            </b-col>

            <b-col>
              <b-button 
                class="right" 
                variant="outline-success" 
                type="submit"
                @click.prevent="$v.$invalid ? $v.$touch() : handleRegister()"
              >
                Registrar-se
              </b-button>
            </b-col>
          </b-row>
        </b-card-body>
      </b-card>
    </b-form>
  </b-container>
</template>

<script>
import CepService from "@/services/cep.service";
import Address from "@/models/address";
import Personal from "@/models/personal";
import User from "@/models/user";
import { validaVazio, tratarErroCep } from "@/utils";

import {
  required,
  email,
  sameAs,
  minLength,
  numeric,
  requiredIf,
  maxLength,
} from "vuelidate/lib/validators";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "FormRegister",
  data: () => ({
    personal: new Personal(),
    address: new Address(),
    user: new User(),
    firstName: "",
    secondName: "",
    password: "",
    confirmation_password: "",
    successful: false,
    card: "info",
    errorMessage: "",
    errorRequest: false,
    dismissSecs: 5,
    dismissCountDown: 0,
    progress: 0,
  }),
  validations: {
    user: {
      email: {
        required,
        email,
      },
      phone: {
        required,
      },
    },
    personal: {
      cpf: {
        required,
      },
      birthday: {
        required,
      },
      rg: {
        required,
        maxLength: maxLength(20),
      },
      passportNumber: {},
      driverPass: {
        required,
      },
      driverNumber: {
        required: requiredIf(function () {
          return this.personal.driverPass;
        }),
        numeric,
      },
    },
    address: {
      cep: {},
      city: {
        required,
      },
      state: {
        required,
      },
      addressName: {
        required,
      },
      numberAddress: {
        required,
      },
    },
    password: {
      required,
      minLength: minLength(6),
    },
    confirmation_password: {
      required,
      sameAs: sameAs("password"),
      minLength: minLength(6),
    },
    firstName: {
      required,
    },
    secondName: {
      required,
    },
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
    filtroTeclas: function (event) {
      if (
        (event.charCode >= 48 && event.charCode <= 57) ||
        event.keyCode == 45 ||
        event.charCode == 46
      ) {
        return true;
      } else {
        return event.preventDefault();
      }
    },
    handleRegister() {
      this.user.name = this.firstName + " " + this.secondName;
      this.user.password = this.password;
      this.user.personal = this.personal;
      this.user.address = this.address;

      this.$store.dispatch('auth/register', this.user).then(
        data => {
          this.message = data.message;
          this.successful = true;
        },
        error => {
          this.errorMessage =
            (error.response && error.response.data) ||
            error.message ||
            error.toString();
          this.dismissCountDown = 5;
        }
      );
    },
    proxCard: function (nameCard) {
      switch (nameCard) {
        case "info":
          this.progress = 0;

          break;

        case "personal":
          this.progress = 1;

          break;

        case "address":
          this.progress = 2;

          break;
      }
      this.card = nameCard;
    },
    getCEP() {
      if (validaVazio(this.address.cep)) {
        CepService.findByCep(this.address.cep).then(
          (data) => {
            let locale = data.data;

            if (locale.city != "") {
              this.address.city = locale.city;
            }
            if (locale.state != "") {
              this.address.state = locale.state;
            }
            if (locale.street != "" && locale.neighborhood != "") {
              this.address.addressName =
                locale.street + ", " + locale.neighborhood;
            } else if (locale.street != "" && locale.neighborhood === "") {
              this.address.addressName = locale.street;
            }

            console.log();
          },
          (error) => {
            this.dismissCountDown = 3;
            this.errorMessage = tratarErroCep(error);

            this.address.city = "";
            this.address.state = "";
            this.address.addressName = "";
            this.address.addressNumber = "";
          }
        );
      }
    },
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    clearDriverNumberNotHasPerm() {
      if (!this.personal.driverPass) {
        this.personal.driverNumber = "";
      }
    },
  },
};
</script>

<style scoped>
.btn-action {
  margin-top: 10px;
}

.right {
  float: right;
}

.alert-danger {
  padding: 4px 12px;
}

#message-error-callback {
  font-size: 16px;
  padding: 10px 20px;
}
</style>
