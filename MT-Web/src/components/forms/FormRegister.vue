<!-- eslint-disable vue/no-duplicate-attributes -->
<template>
  <div class="responsive-width-form m-2 d-flex flex-column align-items-center">
    <b-alert
      id="message-error-callback"
      fade
      variant="danger"
      class="shadow"
      :show="dismissCountDown"
      @dismiss-count-down="countDownChanged"
      >{{ this.errorMessage }}</b-alert
    >
    <b-progress :value="this.progress" :max="2" class="w-100 mt-3 mb-3" :variant="register.variant" striped />
    <div v-if="!register.successful" class="w-100">
      <b-form name="register" @submit.stop.prevent>
        <b-card
          v-if="card === 'info'"
          title="Informações Iniciais"
          class="border-0 shadow mb-3 bg-white rounded"
        >
          <b-card-body>
            <b-row>
              <b-col class="mt-2" lg="4" md="6" sm="12">
                <b-form-group label-for="name" class="mb-2 py-1">
                  <template v-slot:label>
                    Primeiro nome<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="user.firstName"
                    @change="$v.user.firstName.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.user.firstName.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-help input-live-feedback"
                    name="firstName"
                  />
                  <b-form-invalid-feedback id="input-live-feedback">
                    Primeiro Nome é um campo obrigatório.
                  </b-form-invalid-feedback>
                </b-form-group>
              </b-col>

              <b-col class="mt-2" lg="4" md="6" sm="12">
                <b-form-group label-for="name" class="mb-2 py-1">
                  <template v-slot:label>
                    Sobrenome<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="user.secondName"
                    @change="$v.user.secondName.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.user.secondName.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-help input-live-feedback"
                    name="secondName"
                  />
                  <b-form-invalid-feedback id="input-live-feedback">
                    Nome é um campo obrigatório.
                  </b-form-invalid-feedback>
                </b-form-group>
              </b-col>

              <b-col class="mt-2" lg="4" md="12" sm="12">
                <b-form-group label-for="email" class="mb-2 py-1">
                  <template v-slot:label>
                    E-mail<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="user.email"
                    @change="$v.user.email.$touch()"
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
                <b-form-group label-for="phone" class="mb-2 py-1">
                  <template v-slot:label>
                    Telefone<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="user.phone"
                    @change="$v.user.phone.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.user.phone.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-help input-live-feedback"
                    name="phone"
                    v-mask="mask.phone"
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
                <b-form-group label-for="password" class="mb-2 py-1">
                  <template v-slot:label>
                    Senha<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="user.password"
                    @change="$v.user.password.$touch()"
                    type="password"
                    :class="[
                      { 'is-invalid': $v.user.password.$error },
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
                <b-form-group label-for="password-validate" class="mb-2 py-1">
                  <template v-slot:label>
                    Confirme sua Senha<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="user.confirmation_password"
                    @change="$v.user.confirmation_password.$touch()"
                    type="password"
                    :class="[
                      { 'is-invalid': $v.user.confirmation_password.$error },
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
                  @click.prevent="
                    $v.user.$invalid ? $v.user.$touch() : proxCard('personal')
                  "
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
          class="border-0 shadow mb-5 bg-white rounded"
        >
          <b-card-body>
            <b-row>
              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="cpf" class="mb-2 py-1">
                  <template v-slot:label>
                    CPF<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="personal.cpf"
                    @change="$v.personal.cpf.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.personal.cpf.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
                    name="cpf"
                    v-mask="mask.cpf"
                    @keypress="filtroTeclas"
                    placeholder="000.000.000-00"
                  />
                  <b-form-invalid-feedback id="input-live-feedback">
                    CPF é um campo obrigatório.
                  </b-form-invalid-feedback>
                </b-form-group>
              </b-col>

              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="birthday" class="mb-2 py-1">
                  <template v-slot:label>
                    Data de Nascimento<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="personal.birthday"
                    @change="$v.personal.birthday.$"
                    placeholder="Informe sua data de nascimento."
                    :class="[
                      { 'is-invalid': $v.personal.birthday.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
                    v-mask="mask.birthday"
                    name="birthday"
                  />
                  <b-form-invalid-feedback id="input-live-feedback">
                    Data de nascimento é um campo obrigatório.
                  </b-form-invalid-feedback>
                </b-form-group>
              </b-col>

              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="rg" class="mb-2 py-1">
                  <template v-slot:label>
                    RG<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="personal.rg"
                    @change="$v.personal.rg.$"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.personal.rg.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
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
                <b-form-group label-for="passportNumber" class="mb-2 py-1">
                  <template v-slot:label>
                    Número de Passaporte<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="personal.passportNumber"
                    @change="$v.personal.passportNumber.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.personal.passportNumber.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
                    name="passportNumber"
                  />
                </b-form-group>
                <b-form-invalid-feedback id="input-live-feedback">
                  Número de Passaporte é um campo obrigatório.
                </b-form-invalid-feedback>
              </b-col>

              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="radio-inline" class="mb-2 py-1">
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
                <b-form-group label-for="driverNumber" class="mb-2 py-1">
                  <template v-slot:label>
                    Número da Carteira de Motorista<span class="text-danger"
                      >*</span
                    >
                  </template>
                  <b-form-input
                    v-model="personal.driverNumber"
                    @change="$v.personal.driverNumber.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.personal.driverNumber.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
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
                  @click.prevent="
                    $v.personal.$invalid
                      ? $v.personal.$touch()
                      : proxCard('address')
                  "
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
          class="border-0 shadow mb-5 bg-white rounded"
        >
          <b-card-body>
            <!-- outros campos -->
            <b-row>
              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="cep" class="mb-2 py-1">
                  <template v-slot:label>CEP</template>
                  <b-form-input
                    v-model="address.cep"
                    @change="$v.address.cep.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.address.cep.$error },
                      'form-control',
                    ]"
                    name="cep"
                    aria-describedby="input-live-feedback"
                    @keypress="filtroTeclas"
                    @keyup="getCEP"
                  />
                </b-form-group>
                <b-form-invalid-feedback id="input-live-feedback">
                  O cep é composto por 8 dígitos.
                </b-form-invalid-feedback>
              </b-col>

              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="city" class="mb-2 py-1">
                  <template v-slot:label>
                    Cidade<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="address.city"
                    @change="$v.address.city.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.address.city.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
                    name="city"
                  />
                  <b-form-invalid-feedback id="input-live-feedback">
                    Cidade é um campo obrigatório.
                  </b-form-invalid-feedback>
                </b-form-group>
              </b-col>

              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="state" class="mb-2 py-1">
                  <template v-slot:label>
                    Estado<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="address.state"
                    @change="$v.address.state.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.address.state.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
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
                <b-form-group label-for="zipcode" class="mb-2 py-1">
                  <template v-slot:label>
                    Endereço<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="address.zipcode"
                    @change="$v.address.zipcode.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.address.zipcode.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
                    name="zipcode"
                  />
                  <b-form-invalid-feedback id="input-live-feedback">
                    Endereço é um campo obrigatório.
                  </b-form-invalid-feedback>
                </b-form-group>
              </b-col>

              <b-col class="mt-2" md="4" sm="12">
                <b-form-group label-for="numberAddress" class="mb-2 py-1">
                  <template v-slot:label>
                    Número do Endereço<span class="text-danger">*</span>
                  </template>
                  <b-form-input
                    v-model="address.numberAddress"
                    @change="$v.address.numberAddress.$touch()"
                    type="text"
                    :class="[
                      { 'is-invalid': $v.address.numberAddress.$error },
                      'form-control',
                    ]"
                    aria-describedby="input-live-feedback"
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
                  @click.prevent="
                    $v.address.$invalid ? $v.address.$touch() : handleRegister()
                  "
                >
                  Registrar-se
                </b-button>
              </b-col>
            </b-row>
          </b-card-body>
        </b-card>
      </b-form>
    </div>
    <div v-else-if="register.successful" class="w-100">
      <b-card>
        <b-card-body >
          <div class="d-flex flex-column justify-content-center align-items-center">
            <b-card-text id="success-message" class="p-4">
              {{ this.successMessage }}
            </b-card-text>
            <b-button
              variant="outline-success"
              @click="redirectHome"
              id="btn-personal"
              class="w-75"
            >
              Página inicial
            </b-button>
          </div>
        </b-card-body>
      </b-card>
    </div>
  </div>
</template>

<script>
import CepService from "@/services/cep.service";
import Address from "@/models/address";
import Personal from "@/models/personal";
import User from "@/models/user";
import { validaVazio, tratarErroCep } from "@/utils";
import AwesomeMask from "awesome-mask";

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
    confirmation_password: "",
    register: {
      successful: false,
      variant: "primary"
    },
    card: "info",
    successMessage: "Usuário foi cadastrado com sucesso!",
    errorMessage: "",
    dismissSecs: 5,
    dismissCountDown: 0,
    progress: 0,
    mask: {
      cpf: "999.999.999-99",
      birthday: "99/99/9999",
      phone: "(99) 99999-9999",
    },
  }),
  directives: {
    mask: AwesomeMask,
  },
  validations: {
    user: {
      firstName: {
        required,
      },
      secondName: {
        required,
      },
      email: {
        required,
        email,
      },
      phone: {
        required,
      },
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(20),
      },
      confirmation_password: {
        required,
        sameAs: sameAs("password"),
        minLength: minLength(6),
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
      passportNumber: {
        required,
      },
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
      cep: {
        maxLength: maxLength(8),
      },
      city: {
        required,
      },
      state: {
        required,
      },
      zipcode: {
        required,
      },
      numberAddress: {
        required,
      },
    },
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
      let dt = this.personal.birthday.split("/");
      this.personal.birthday = dt[2] + "-" + dt[1] + "-" + dt[0];
      this.user.personal = this.personal;
      this.user.address = this.address;

      this.$store.dispatch("auth/register", this.user).then(
        () => {
          this.register.successful = true;
          this.register.variant = "success";

          this.$store.dispatch("auth/login", this.user).then(
            (error) => {
              this.loading = false;
              this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
            }
          );
        },
        (error) => {
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
      if (this.address.cep.length == 8) {
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
              if (
                locale.street != "" &&
                locale.neighborhood != "" &&
                locale.street != undefined &&
                locale.neighborhood != undefined
              ) {
                this.address.zipcode =
                  locale.street + ", " + locale.neighborhood;
              } else if (
                locale.street != "" &&
                locale.neighborhood == "" &&
                locale.street != undefined &&
                locale.neighborhood == undefined
              ) {
                this.address.zipcode = locale.street;
              }
            },
            (error) => {
              this.dismissCountDown = 3;
              this.errorMessage = tratarErroCep(error);

              this.clearFieldsCEP();
            }
          );
        }
      } else {
        this.clearFieldsCEP();
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
    clearFieldsCEP() {
      this.address.city = "";
      this.address.state = "";
      this.address.zipcode = "";
      this.address.numberAddress = "";
    },
    redirectHome() {
      this.$router.push("/");
    }
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

#success-message {
  font-size: 20px;
  color: #707070;
}

.responsive-width-form {
  width: 50%;
}

@media screen and (max-width: 1650px)  {
  .responsive-width-form {
    width: 80%;
  }
}

@media screen and (max-width: 760px)  {
  .responsive-width-form {
    width: 100%;
  }
}

</style>
