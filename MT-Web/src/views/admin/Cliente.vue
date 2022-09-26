<template>
  <b-container>
    <b-card no-body class="border-0 shadow mt-5">
      <b-tabs card justified>
        <b-tab title="Informações básicas" active>
          <b-row>
            <b-col class="mt-2" lg="6" md="6" sm="12">
              <b-form-group label-for="name" class="mb-2 py-1">
                <template v-slot:label>
                  Primeiro nome
                </template>
                <b-form-input value="Vinicius" disabled type="text" name="firstName" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" lg="6" md="6" sm="12">
              <b-form-group label-for="name" class="mb-2 py-1">
                <template v-slot:label>
                  Sobrenome
                </template>
                <b-form-input value="Sens de Olveira" disabled type="text" name="secondName" />
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col class="mt-2" lg="6" md="12" sm="12">
              <b-form-group label-for="email" class="mb-2 py-1">
                <template v-slot:label>
                  E-mail
                </template>
                <b-form-input value="vinicius@gmail.com" disabled type="email" name="email" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" lg="6" md="6" sm="12">
              <b-form-group label-for="phone" class="mb-2 py-1">
                <template v-slot:label>
                  Telefone
                </template>
                <b-form-input value="(47) 99999-9999" disabled type="text" name="phone" />
              </b-form-group>
            </b-col>
          </b-row>
        </b-tab>
        <b-tab title="Dados pessoais">
          <b-row>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="cpf" class="mb-2 py-1">
                <template v-slot:label>
                  CPF
                </template>
                <b-form-input value="999.999.999-99" disabled type="text" name="cpf" />
              </b-form-group>
            </b-col>

            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="birthday" class="mb-2 py-1">
                <template v-slot:label>
                  Data de Nascimento
                </template>
                <b-form-input value="10/10/2010" disabled name="birthday" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="rg" class="mb-2 py-1">
                <template v-slot:label>
                  RG
                </template>
                <b-form-input value="99.999.999.-99" disabled type="text" name="rg" />
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="passportNumber" class="mb-2 py-1">
                <template v-slot:label>
                  Número de Passaporte
                </template>
                <b-form-input value="B0123456789" disabled type="text" name="passportNumber" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="radio-inline" class="mb-2 py-1">
                <template v-slot:label>
                  Carteira de motorista
                </template>
                <b-form-input value="B0123456789" disabled type="text"/>
              </b-form-group>
            </b-col>>
          </b-row>

        </b-tab>
        <b-tab title="Endereço">

          <b-row>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="cep" class="mb-2 py-1">
                <template v-slot:label>CEP</template>
                <b-form-input value="99999-999" disabled type="text" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="city" class="mb-2 py-1">
                <template v-slot:label>
                  Cidade
                </template>
                <b-form-input value="Joinville" disabled type="text" name="city" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="state" class="mb-2 py-1">
                <template v-slot:label>
                  Estado
                </template>
                <b-form-input value="SC" disabled type="text" />
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col class="mt-2" md="8" sm="12">
              <b-form-group label-for="zipcode" class="mb-2 py-1">
                <template v-slot:label>
                  Endereço
                </template>
                <b-form-input value="Rua São Paulo" disabled type="text" name="zipcode" />
              </b-form-group>
            </b-col>
            <b-col class="mt-2" md="4" sm="12">
              <b-form-group label-for="numberAddress" class="mb-2 py-1">
                <template v-slot:label>
                  Número do Endereço
                </template>
                <b-form-input value="777" disabled name="numberAddress" />
              </b-form-group>
            </b-col>
          </b-row>

        </b-tab>
        <b-tab title="Complementos">


        </b-tab>
      </b-tabs>
    </b-card>
  </b-container>
</template>
<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Cliente',
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
  }
};
</script>

<style scoped>

</style>