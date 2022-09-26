<template>
  <b-container fluid>
    <b-row class="row-header">
      <b-col>
        <b-container class="h-100" fluid>
          <b-form>
            <b-row>
              <b-col>
                <b-form-input />
              </b-col>
              <b-col>
                <b-form-input />
              </b-col>
            </b-row>
          </b-form>
        </b-container>
      </b-col>
    </b-row>
    <hr />
    <b-row>
      <b-col>
        <b-container fluid>
          <b-table
            sticky-header
            head-variant="light"
            striped
            hover
            :busy="isBusy"
            :items="items"
            :fields="fields"
          ></b-table>
        </b-container>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "ListaRoteiro",
  data() {
    return {
      isBusy: false,
      fields: [
        {
          key: "last_name",
          sortable: true,
        },
        {
          key: "first_name",
          sortable: false,
        },
        {
          key: "age",
          label: "Person age",
          sortable: true,
        },
      ],
      items: [
        {
          isActive: true,
          age: 40,
          first_name: "Dickerson",
          last_name: "Macdonald",
        },
        { isActive: true, age: 21, first_name: "Larsen", last_name: "Shaw" },
        { isActive: true, age: 89, first_name: "Geneva", last_name: "Wilson" },
        { isActive: true, age: 38, first_name: "Jami", last_name: "Carney" },
      ],
    };
  },
  methods: {
    toggleBusy() {
      this.isBusy = !this.isBusy;
    },
  },
};
</script>

<style scoped>

.row-header {
  height: 100px;
}

</style>
