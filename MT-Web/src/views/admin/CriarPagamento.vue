<template>
    <b-form class="w-100 h-100 d-flex flex-row" name="payment" @submit.stop.prevent>
        <div id="content-left" class="h-100 border-0 shadow">
            <b-container class="py-2">
                <b-row>
                    <b-col md=12 class="p-1 text-center">
                        <h3>Informações dos pagamentos</h3>
                    </b-col>
                    <b-col md="12" class="p-3">
                        <b-form-group label-for="namePayment" class="mb-2 py-1">
                            <template v-slot:label>
                                Título do pagamento<span class="text-danger">*</span>
                            </template>
                            <b-form-input type="text" aria-describedby="input-live-help input-live-feedback"
                                v-model="payment.title" name="title" placeholder="Pagamento viagem 1"
                                @change="$v.payment.title.$touch()" :class="[
                                  { 'is-invalid': $v.payment.title.$error },
                                  'form-control',
                                ]" />
                            <b-form-invalid-feedback id="input-live-feedback">
                                {{validationMsg($v.payment.title)}}
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                    <b-col md="12" class="p-3">
                        <b-form-group label-for="dueDate" class="mb-2 py-1">
                            <template v-slot:label>
                                Data de vencimento<span class="text-danger">*</span>
                            </template>
                            <b-form-input type="text" v-mask="mask.date"
                                aria-describedby="input-live-help idinput-live-feedback" id="dueDate"
                                placeholder="99/99/9999" v-model="payment.dueDate" name="dueDate"
                                @change="$v.payment.dueDate.$touch()" :class="[
                                  { 'is-invalid': $v.payment.dueDate.$error },
                                  'form-control',
                                ]" />
                            <b-form-invalid-feedback id="input-live-feedback">
                                {{validationMsg($v.payment.dueDate)}}
                            </b-form-invalid-feedback>
                        </b-form-group>
                    </b-col>
                    <b-col md="12" class="p-3">
                        <b-button variant="danger">Cancelar pagamento</b-button>
                        <b-button variant="success" class="submit" 
                            @click.prevent="$v.payment.$invalid ? $v.payment.$touch() : submitForm()">Salvar
                            pagamento
                        </b-button>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <div id="content-right" class="h-100">
            <b-container>
                <b-row>
                    <b-col class="p-3">
                        <b-button variant="success" @click="createModalListPayments()">
                            Adicionar uma nova linha
                        </b-button>
                    </b-col>
                </b-row>
                <b-table striped hover bordered :items="items" :fields="fields">
                    <template #cell(typePayment)="data">
                        {{getOptionTypePayment(data.item.typePayment)}}
                    </template>
                    <template #cell(price)="data">
                        {{"R$"+data.item.price}}
                    </template>
                    <template #cell(Ações)="data">
                        <b-button-group>
                            <b-button variant="info" @click="data.toggleDetails">
                                <b-icon class="color-trash" icon="eye-fill"></b-icon>
                            </b-button>
                            <b-button variant="warning" @click="editModalPaymentList(data.index)">
                                <b-icon icon="pencil-fill"></b-icon>
                            </b-button>
                            <b-button variant="danger" @click="{{removePaymentList(data.index)}}">
                                <b-icon class="color-trash" icon="trash-fill"></b-icon>
                            </b-button>
                        </b-button-group>
                    </template>
                    <template #row-details="data">
                        <b-card>
                            <b-row class="mb-2" md="12" sm="12" lg="12">
                                <b-col>{{ data.item.observation }}</b-col>
                            </b-row>
                            <b-button size="sm" @click="data.toggleDetails">Esconder observações</b-button>
                        </b-card>
                    </template>
                </b-table>
            </b-container>
        </div>

        <b-modal id="addPayments" ref="addPayments" title="Adicionar um novo pagemento" v-model="modalAddPayments"
            centered header-bg-variant="info">
            <b-row>
                <b-col md="12" class="p-3">
                    <b-form-group label-for="descPayments" class="mb-2 py-1">
                        <template v-slot:label>
                            Descrição do Pagamento <span class="text-danger">*</span>
                        </template>
                        <b-form-input type="text" aria-describedby="input-live-help input-live-feedback"
                            v-model="paymentList.describe" name="describe" id="describe"
                            @change="$v.paymentList.describe.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.describe.$error },
                              'form-control',
                            ]" />
                        <b-form-invalid-feedback id="input-live-feedback">
                            Descrição do Pagamento é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
                <b-col md="6" class="p-3">
                    <b-form-group label-for="pricePayments" class="mb-2 py-1">
                        <template v-slot:label>
                            Preço <span class="text-danger">*</span>
                        </template>
                        <b-form-input type="text" aria-describedby="input-live-help input-live-feedback"
                            v-mask="'money'" v-model="paymentList.price" name="price" id="price"
                            @change="$v.paymentList.price.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.price.$error },
                              'form-control',
                            ]" />
                        <b-form-invalid-feedback id="input-live-feedback">
                            Preço é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
                <b-col md="6" class="p-3">
                    <b-form-group label-for="typePayment" class="mb-2 py-1">
                        <template v-slot:label>
                            Tipo de Pagamento <span class="text-danger">*</span>
                        </template>
                        <b-form-select v-model="paymentList.typePayment" :options="options" name="typePayment"
                            id="typePayment" @change="$v.paymentList.typePayment.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.typePayment.$error },
                              'form-control',
                            ]" />
                        <b-form-invalid-feedback id="input-live-feedback">
                            Tipo de Pagamentoo é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
                <b-col md="12" class="p-3">
                    <b-form-group label-for="typePayment" class="mb-2 py-1">
                        <template v-slot:label>
                            Observações <span class="text-danger">*</span>
                        </template>
                        <b-form-textarea id="observation" name="observation" placeholder="Escreva uma descricão..."
                            rows="4" no-resize v-model="paymentList.observation"
                            @change="$v.paymentList.observation.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.observation.$error },
                              'form-control',
                            ]">
                        </b-form-textarea>
                        <b-form-invalid-feedback id="input-live-feedback">
                            Observações é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
            </b-row>
            <template #modal-footer>
                <b-button variant="outline-danger" @click="$bvModal.hide('addPayments')">
                    Cancelar
                </b-button>
                <b-button variant="outline-success"
                    @click.prevent="$v.paymentList.$invalid ? $v.paymentList.$touch() : submitFormPaymentList()">
                    Adicionar
                </b-button>
            </template>
        </b-modal>

        <b-modal id="editPayments" ref="editPayments" title="Editar pagemento" v-model="modalEditPayments" centered
            header-bg-variant="warning">
            <b-row>
                <b-col md="12" class="p-3">
                    <b-form-group label-for="descPayments" class="mb-2 py-1">
                        <template v-slot:label>
                            Descrição do Pagamento <span class="text-danger">*</span>
                        </template>
                        <b-form-input type="text" aria-describedby="input-live-help input-live-feedback"
                            v-model="paymentList.describe" name="describe" id="describe"
                            @change="$v.paymentList.describe.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.describe.$error },
                              'form-control',
                            ]" />
                        <b-form-invalid-feedback id="input-live-feedback">
                            Descrição do Pagamento é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
                <b-col md="6" class="p-3">
                    <b-form-group label-for="pricePayments" class="mb-2 py-1">
                        <template v-slot:label>
                            Preço <span class="text-danger">*</span>
                        </template>
                        <b-form-input type="text" aria-describedby="input-live-help input-live-feedback"
                            v-mask="'money'" v-model="paymentList.price" name="price" id="price"
                            @change="$v.paymentList.price.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.price.$error },
                              'form-control',
                            ]" />
                        <b-form-invalid-feedback id="input-live-feedback">
                            Preço é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
                <b-col md="6" class="p-3">
                    <b-form-group label-for="typePayment" class="mb-2 py-1">
                        <template v-slot:label>
                            Tipo de Pagamento <span class="text-danger">*</span>
                        </template>
                        <b-form-select v-model="paymentList.typePayment" :options="options" name="typePayment"
                            id="typePayment" @change="$v.paymentList.typePayment.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.typePayment.$error },
                              'form-control',
                            ]" />
                        <b-form-invalid-feedback id="input-live-feedback">
                            Tipo de Pagamentoo é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                </b-col>
                <b-col md="12" class="p-3">
                    <b-form-group label-for="typePayment" class="mb-2 py-1">
                        <template v-slot:label>
                            Observações <span class="text-danger">*</span>
                        </template>
                        <b-form-textarea id="observation" name="observation" placeholder="Escreva uma descricão..."
                            rows="4" no-resize v-model="paymentList.observation"
                            @change="$v.paymentList.observation.$touch()" :class="[
                              { 'is-invalid': $v.paymentList.observation.$error },
                              'form-control',
                            ]">
                        </b-form-textarea>
                        <b-form-invalid-feedback id="input-live-feedback">
                            Observações é um campo obrigatório.
                        </b-form-invalid-feedback>
                    </b-form-group>
                   
                </b-col>
            </b-row>
            <template #modal-footer>
                <b-button variant="outline-danger" @click="$bvModal.hide('editPayments')">
                    Cancelar
                </b-button>
                <b-button variant="outline-success"
                    @click.prevent="$v.paymentList.$invalid ? $v.paymentList.$touch() : submitEditFormPaymentList()">
                    Salvar edição
                </b-button>
            </template>
        </b-modal>
    </b-form>

</template>
<script>
import Payment from '@/models/payment';
import PaymentList from '@/models/paymentList'
import { validationMessage } from "vuelidate-messages";
import { required, maxLength, minLength, } from "vuelidate/lib/validators";
import AwesomeMask from "awesome-mask";


const formInvalidMessages = {
    required: () => "Este(s) campo(s) são requiridos!",
    minValueDate: () => "A data de vencimento tem que ser maior que hoje!",
};

export default {
    name: 'CreatePayment',
    data() {
        return {
            payment: new Payment(),
            paymentList: new PaymentList(),
            index: null,
            fields: [{ key: 'describe', label: "Descrição do pagamento" }, { key: 'price', label: "Preço" }, { key: 'typePayment', label: "Forma de pagamento" }, "Ações"],
            items: [],
            options: [
                { value: null, text: 'Selecione o tipo' },
                { value: '1', text: 'PIX' },
                { value: '2', text: 'Cartão de crédito' },
                { value: '3', text: 'Cartão de débito' },
                { value: '4', text: 'Com a vida' }
            ],
            selected: null,
            modalAddPayments: false,
            modalEditPayments: false,
            mask: {
                date: "99/99/9999"
            },
        };
    },
    validations: {
        payment: {
            title: { required, },
            dueDate: {
                required,
                minLength: minLength(10),
                maxLength: maxLength(10),
                minValueDate(value) {
                    if (value == null || value == "") {
                        return false;
                    }

                    let dueDate = value.split("/");
                    dueDate = dueDate[2] + "/" + dueDate[1] + "/" + dueDate[0];

                    return new Date(dueDate) > new Date();
                },
            },
        },
        paymentList: {
            describe: { required },
            price: {
                required,
            },
            typePayment: { required },
            observation: {
                required,
                maxLength: maxLength(240)
            }
        }
    },
    mounted() {
        // UserService.getAdminBoard().then(
        //     response => {
        //         this.content = response.data;
        //     },
        //     error => {
        //         this.content =
        //             (error.response && error.response.data) ||
        //             error.message ||
        //             error.toString();
        //         if (error.response && error.response.status === 403) {
        //             EventBus.dispatch("logout");
        //         }
        //     }
        // );
    },
    directives: {
        mask: AwesomeMask,
    },
    methods: {
        validationMsg: validationMessage(formInvalidMessages),
        submitForm() {
            alert('entrei')
            this.payment.list = this.items;

            console.log(JSON.stringify(this.payment));
        },
        createModalListPayments() {
            this.paymentList = new PaymentList();
            this.$refs['addPayments'].show();
        },
        submitFormPaymentList() {
            this.items.push(this.paymentList);
            this.resetModalAddPaymentList();
        },
        resetModalAddPaymentList() {
            this.$refs['addPayments'].hide();
        },
        resetModalEditPaymentList() {
            this.$refs['editPayments'].hide();
        },
        getOptionTypePayment(type) {
            return this.options.find(i => i.value == type).text;
        },
        editModalPaymentList(index) {
            this.paymentList = new PaymentList();
            this.paymentList.describe = this.items[index].describe;
            this.paymentList.price = this.items[index].price;
            this.paymentList.typePayment = this.items[index].typePayment;
            this.paymentList.observation = this.items[index].observation;
            this.index = index;
            this.$refs['editPayments'].show();
        },
        submitEditFormPaymentList(){
            this.items[this.index].describe = this.paymentList.describe;
            this.items[this.index].price = this.paymentList.price;
            this.items[this.index].typePayment = this.paymentList.typePayment;
            this.items[this.index].observation = this.paymentList.observation;
            this.index = null;
            this.paymentList = null;
            this.resetModalEditPaymentList();
        },
        removePaymentList(index) {
            this.items.pop(index);
        }
    }
};
</script>
<style scoped>
#content-left {
   
    width: 30%;
}

#content-right {
    width: 70%;
    overflow: auto;
}

.submit {
    float: right;
}

.color-trash {
    color: black;
}
</style>