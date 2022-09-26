<template>
  <div class="background-light">
    <div id="header-calendar">
      <b-container fluid>
        <b-button variant="success" class="m-2" @click="getListRoteiroView"
          >Lista de Viagens</b-button
        >
      </b-container>
      <b-container fluid>
        <b-row :cols="countScreenCalendar" class="pt-2">
          <b-col
            v-for="date in calculateDate()"
            :key="date.day"
            class="p-0 text-center"
          >
            <div>
              {{ date.week }}
            </div>
            <div>
              {{ date.day }}
            </div>
          </b-col>
        </b-row>
      </b-container>
    </div>
    <div id="body-calendar">
      <b-container fluid>
        <b-row :cols="countScreenCalendar">
          <b-col v-for="date in calculateDate()" :key="date.day" class="p-0">
            <b-card
              @click="redirectRoteiro(colInfo.id)"
              v-for="colInfo in callbackValue(date)"
              :key="colInfo.id"
              class="text-center card-info shadow-sm"
            >
              <p class="card-text-up">
                <b>{{ colInfo.Nome }}</b>
              </p>
              <p class="card-text-up">
                {{ colInfo.Data }}
              </p>
              <hr />
              <p class="card-text-down">
                {{ colInfo.De }}
              </p>
              <b-icon :icon="iconViagem[colInfo.Tipo]" />
              <p class="card-text-down">
                {{ colInfo.Para }}
              </p>
              <hr />
              <div class="status-display">
                <div
                  class="cicle-status"
                  :style="{
                    'background-color': `${configStatus[colInfo.Status].color}`,
                  }"
                ></div>
                <p class="card-text-down text-status">
                  {{ configStatus[colInfo.Status].name }}
                </p>
              </div>
            </b-card>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import UserService from "@/services/user.service";
import RoteiroService from "@/services/travel/roteiro.service";
import EventBus from "@/common/EventBus";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Admin",
  data() {
    return {
      content: "",
      countScreenCalendar: 1,
      iconViagem: ["arrow-down", "arrow-down-up"],
      configStatus: [],
      dados: []
    };
  },
  mounted() {
    this.requestTravel();
  },
  created() {
    window.addEventListener("resize", this.checkScreen);
    this.checkScreen();
  },
  methods: {
    checkScreen() {
      let size = window.innerWidth;

      if (size < 576) {
        this.countScreenCalendar = 2;
      } else if (size >= 576 && size < 768) {
        this.countScreenCalendar = 3;
      } else if (size >= 768 && size < 992) {
        this.countScreenCalendar = 4;
      } else if (size >= 992 && size < 1200) {
        this.countScreenCalendar = 5;
      } else if (size >= 1200) {
        this.countScreenCalendar = 8;
      }
    },
    calculateDate() {
      let arrayDate = [];

      for (let i = 0; i < this.countScreenCalendar; i++) {
        let date = new Date(new Date().setDate(new Date().getDate() + i));

        arrayDate.push({
          week: this.calculateWeek(date),
          day: `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`,
        });
      }

      return arrayDate;
    },
    calculateWeek(date) {
      let week = [
        "Domingo",
        "Segunda-feira",
        "Terça-feira",
        "Quarta-feira",
        "Quinta-feira",
        "Sexta-feira",
        "Sábado",
      ];
      return week[date.getDay()];
    },
    callbackValue(date) {
      return this.dados.filter((i) => i.Data == date.day);
    },
    redirectRoteiro(id) {
      this.$router.push({ path: `/visualizar/${id}` });
    },
    getListRoteiroView() {
      this.$router.push(`/lista`);
    },
    requestTravel() {
      RoteiroService.getStatus().then(response => {
          this.configStatus = response.data;

          this.dados = [
            {
              id: 1,
              Data: "23/9/2022",
              Nome: "Caio Cesar",
              De: "Joinville",
              Tipo: 0,
              Para: "Pernambuco",
              Status: 6,
            },
            {
              id: 2,
              Data: "24/9/2022",
              Nome: "Caio Cesar",
              De: "Joinville",
              Tipo: 1,
              Para: "Pernambuco",
              Status: 2,
            }
          ]
        },
        (err) => {
          this.content =
            (err.response && err.response.data) ||
            err.message ||
            err.toString();
          if (err.response && err.response.status === 403) {
            EventBus.dispatch("logout");
          }
        }
      );
    },
  },
};
</script>

<style>
#container-roteiro {
  background-color: #fff;
}

#header-calendar {
  height: 110px;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 5%);
  user-select: none;
}

.card-info {
  margin: 10px;
  transition: 200ms;
  user-select: none;
  cursor: pointer;
}

.card-info:hover {
  transition: 200ms;
  transform: scale(1.02);
}

.card-text-down {
  margin: 0;
}

.card-text-up {
  margin-bottom: 4px;
}

.status-display {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}

.cicle-status {
  height: 10px;
  width: 10px;
  border-radius: 10px;
  border: 1px solid #1d1d1d85;
}

.text-status {
  font-size: 12px;
  width: 90%;
}
</style>
