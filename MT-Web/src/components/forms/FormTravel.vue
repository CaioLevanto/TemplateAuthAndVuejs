<template>

  <b-container>
    <b-overlay no-fade :show="!this.loggedIn" rounded="sm" opacity="0.60">

      <template #overlay>
        <b-card title="Aviso" style="max-width: 25rem;" bg-variant="warning" class="shadow">
          <b-card-text>
            Para você solicitar uma viagem ao acessor, é necessário estar conectado com a sua conta!
          </b-card-text>

          <b-button href="#" block @click.prevent="login">Acessar minha conta</b-button>
        </b-card>
      </template>

      <b-form name="travel" @submit.stop.prevent>
        <b-card title="Peça à sua viagem!" class="border-0 shadow">
          <b-card-body>
            <b-row>
              <b-col class="mt-2" md="12" lg="7">
                <b-form-group label-for="name">
                  <template v-slot:label>
                    Origem e destino
                    <span class="text-danger">*</span>
                  </template>
                  <b-input-group>
                    <b-form-input aria-describedby="input-live-help input-live-feedback" placeholder="Local de partida"
                      name="origin" id="origin" v-model="travel.origin" @change="$v.travel.origin.$touch()" :class="[
                        { 'is-invalid': $v.travel.origin.$error },
                        'form-control',
                      ]" />

                    <b-button @click="handleSwitchLocation">
                      <b-icon icon="arrow-left-right"></b-icon>
                    </b-button>
                    <b-form-input aria-describedby="input-live-help input-live-feedback" placeholder="Local de destino"
                      name="destiny" v-model="travel.destiny" @change="$v.travel.destiny.$touch()" :class="[
                        { 'is-invalid': $v.travel.origin.$error },
                        'form-control',
                      ]" />
                    <b-form-invalid-feedback id="input-live-feedback">
                      {{
                          validationMsg($v.travel.destiny) ||
                          validationMsg($v.travel.origin)
                      
                      
                      }}
                    </b-form-invalid-feedback>
                  </b-input-group>
                </b-form-group>
              </b-col>
              <b-col class="mt-2" md="12" lg="5">
                <b-form-group label-for="name">
                  <template v-slot:label>
                    Datas de partida e volta
                    <span class="text-danger">*</span>
                  </template>
                  <b-input-group>
                    <b-form-input aria-describedby="input-live-help input-live-feedback" placeholder="Partida"
                      name="startDate" v-mask="mask.date" v-model="travel.startDate"
                      @change="$v.travel.startDate.$touch()" :class="[
                        { 'is-invalid': $v.travel.startDate.$error },
                        'form-control',
                      ]" />

                    <b-form-input aria-describedby="input-live-help input-live-feedback" placeholder="Volta"
                      name="backDate" v-mask="mask.date" :disabled="!this.travel.radioGoBack" v-model="travel.backDate"
                      @change="$v.travel.backDate.$touch()" :class="[
                        { 'is-invalid': $v.travel.backDate.$error },
                        'form-control',
                      ]" />
                    <b-form-invalid-feedback>
                      {{
                          validationMsg($v.travel.startDate) ||
                          validationMsg($v.travel.backDate)
                      
                      
                      }}
                    </b-form-invalid-feedback>
                  </b-input-group>
                </b-form-group>
              </b-col>
            </b-row>
            <b-row class="mt-3" align-v="center">
              <b-col md="6" lg="3" class="mt-1">
                <b-form-group label-for="radio-inline" class="p-1 text-center mb-2">
                  <b-form-radio-group name="radioGoBack" class="mt-2" v-model="travel.radioGoBack">
                    <b-form-radio name="goAndBack" :value="true">Ida e volta</b-form-radio>
                    <b-form-radio name="goAndBack" :value="false">Somente Ida</b-form-radio>
                  </b-form-radio-group>
                </b-form-group>
              </b-col>
              <b-col md="6" lg="3" class="d-flex flex-column align-items-center text-center">
                <b-form-group label-for="name">
                  <template v-slot:label> Adultos </template>
                  <b-button-group>
                    <b-button @click="validationAdultsCount">-</b-button>
                    <b-form-input aria-describedby="input-live-help input-live-feedback" name="adults"
                      placeholder="Ex: 1" :value="travel.adults" type="text" v-model="travel.adults" />
                    <b-button @click="travel.adults++">+</b-button>
                  </b-button-group>
                </b-form-group>
              </b-col>
              <b-col md="6" lg="3" class="d-flex flex-column align-items-center text-center">
                <b-form-group label-for="name">
                  <template v-slot:label> Crianças </template>
                  <b-button-group>
                    <b-button @click="validationKidsCount">-</b-button>
                    <b-form-input aria-describedby="input-live-help input-live-feedback" name="kids" placeholder="Ex: 1"
                      :value="travel.kids" type="text" v-model="travel.kids" />
                    <b-button @click="travel.kids++">+</b-button>
                  </b-button-group>
                </b-form-group>
              </b-col>
              <b-col md="6" lg="3" class="mt-4 d-flex flex-column align-items-center">
                <b-button type="submit" variant="outline-success" class="right" @click.prevent="
                  $v.travel.$invalid
                    ? $v.travel.$touch()
                    : $bvModal.show('terms')
                ">Fazer pedido
                </b-button>
              </b-col>
            </b-row>
          </b-card-body>
        </b-card>
        <b-modal id="terms" title="Termos de contrato" v-model="modalTerms" centered header-bg-variant="warning">
          <p class="my-4">É de <b>extrema importância</b> que leia os termos!</p>
          <b-form-group label-for="name">
            <b-form-checkbox name="terms" :state="$v.terms.$error ? false : null" :value="true" :unchecked-value="false"
              class="mt-3" v-model="terms" @change="$v.terms.$touch()">
              <p class="terms-text">
                Eu li e concordo com os <a href="">termos de contrato</a>.
              </p>
            </b-form-checkbox>
          </b-form-group>

          <template #modal-footer>
            <b-button variant="outline-danger" @click="close()">
              Cancel
            </b-button>
            <b-button variant="outline-success" @click.prevent="
            $v.terms.$invalid ? $v.terms.$touch() : this.checkLogin()
            ">
              Fazer pedido
            </b-button>
          </template>
        </b-modal>
      </b-form>
    </b-overlay>
  </b-container>


</template>

<script>
import Travel from "@/models/travel";
import AwesomeMask from "awesome-mask";
import { validationMessage } from "vuelidate-messages";

const formInvalidMessages = {
  required: () => "Este(s) campo(s) são requiridos!",
  minValueDateGo: () => "A data de partida tem que ser maior que hoje!",
  minValueDateBack: () => "A data de volta tem que maior que a partida!",
};

import {
  required,
  requiredIf,
  maxLength,
  minLength,
  sameAs,
} from "vuelidate/lib/validators";

export default {
  name: "FormTravel",
  data: () => ({
    travel: new Travel(),
    mask: {
      date: "99/99/9999",
    },
    modalTerms: false,
    terms: false,
  }),
  validations: {
    travel: {
      origin: {
        required,
      },
      destiny: {
        required,
      },
      startDate: {
        required,
        minLength: minLength(10),
        maxLength: maxLength(10),
        minValueDateGo(value) {
          if (value == null || value == "") {
            return false;
          }

          let date = value.split("/");
          date = date[2] + "/" + date[1] + "/" + date[0];

          return new Date(date) > new Date();
        },
      },
      backDate: {
        required: requiredIf(function () {
          return this.travel.radioGoBack;
        }),
        minLength: minLength(10),
        maxLength: maxLength(10),
        minValueDateBack(val, { startDate }) {
          if (val == null || val == "") {
            return false;
          }
          let startDateFormat = startDate.split("/");
          startDateFormat =
            startDateFormat[2] +
            "/" +
            startDateFormat[1] +
            "/" +
            startDateFormat[0];

          let backDateFormat = val.split("/");
          backDateFormat =
            backDateFormat[2] +
            "/" +
            backDateFormat[1] +
            "/" +
            backDateFormat[0];

          return new Date(backDateFormat) > new Date(startDateFormat);
        },
      },
      adults: {
        required,
      },
    },
    terms: {
      sameAs: sameAs(() => true),
    },
  },
  directives: {
    mask: AwesomeMask,
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },

  },
  methods: {
    validationMsg: validationMessage(formInvalidMessages),

   
    checkLogin() {
      if (!this.loggedIn()) {

      }
      this.handleSendTravel();
    },

    handleSendTravel() {

      this.travel.startDate = new Date(this.travel.startDate).toISOString();

      if (this.travel.radioGoBack == true) {
        this.travel.backDate = new Date(this.travel.backDate).toISOString();
      }

      console.log(this.travel.startDate);
      console.log(this.travel.backDate);

    },
    validationAdultsCount() {
      if (this.travel.adults > 0) {
        this.travel.adults--;
      }
    },
    validationKidsCount() {
      if (this.travel.kids > 0) {
        this.travel.kids--;
      }
    },
    handleSwitchLocation() {
      let destiny = this.travel.destiny;
      let origin = this.travel.origin;

      this.travel.destiny = origin;
      this.travel.origin = destiny;
    },
    validationDates() {
      const startDate = this.travel.startDate.split("/");
      const backDate = new Date(this.travel.backDate);
      console.log(startDate);
    },
    login() {
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.alert-danger {
  padding: 4px 12px;
}

.terms-text {
  margin-left: 5px;
}

#message-error-callback {
  font-size: 16px;
  padding: 10px 20px;
}

.border-red {
  border: 1px solid red;
}
</style>
