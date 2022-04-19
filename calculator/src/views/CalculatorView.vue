<template>
  <div class="text-4xl flex justify-center w-screen">
    <p
      class="bg-blue-100 h-20 w-80 text-right rounded-md flex justify-center items-center"
    >
      {{ current || "0" }}
    </p>
  </div>
  <br />
  <div class="text-2xl flex justify-center w-screen">
    <div class="grid grid-cols-4 gap-5">
      <button @click="setOperator('*')">*</button>
      <button @click="setOperator('/')">/</button>
      <button @click="invert()">+/-</button>
      <button @click="clearHistory()">CH</button>

      <button @click="append(7)">7</button>
      <button @click="append(8)">8</button>
      <button @click="append(9)">9</button>
      <button @click="clear()">C</button>

      <button @click="append(4)">4</button>
      <button @click="append(5)">5</button>
      <button @click="append(6)">6</button>
      <button @click="setOperator('-')">-</button>

      <button @click="append(1)">1</button>
      <button @click="append(2)">2</button>
      <button @click="append(3)">3</button>
      <button @click="setOperator('+')">+</button>

      <button @click="append(0)">0</button>
      <button
        @click="
          () => {
            append(0);
            append(0);
          }
        "
      >
        00
      </button>
      <button @click="dot()">.</button>
      <button @click="equals()">=</button>
    </div>
  </div>
  <br />
  <div class="m-8">
    <h1 class="text-4xl">History:</h1>
    <ul class="grid grid-cols-4 items-center text-center">
      <li
        class="p-1 m-1 bg-gray-100 rounded"
        v-for="item in history"
        :key="item"
      >
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script>
import calculatorService from "@/services/calculator.service";
import authHeader from "@/services/auth-header";
import axios from "axios";

export default {
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  data() {
    return {
      current: "",
      previous: null,
      operator: null,
      operatorClicked: false,
      operatorSign: "",
      toClear: false,
      history: [],
    };
  },
  mounted() {
    if (!this.loggedIn) {
      this.$router.push("/login");
    } else {
      this.fetchHistory();
    }
  },
  methods: {
    async fetchHistory() {
      const result = await axios.get("https://localhost:8443/api/history", {
        headers: authHeader(),
      });
      const history = [];
      for (let s of result.data) {
        var expression =
          s.firstNumber + s.operator + s.secondNumber + "=" + s.answer;
        history.splice(s.id, 0, expression);
      }
      this.history = history;
    },
    clear() {
      this.current = "";
      this.toClear = false;
    },
    invert() {
      this.current = parseFloat(this.current) * -1;
    },
    percentage() {
      this.current = parseFloat(this.current) / 100;
    },
    append(number) {
      if (this.toClear) this.clear();
      this.current = `${this.current}${number}`;
    },
    dot() {
      if (this.current.indexOf(".") === -1 && !this.current.includes("."))
        this.append(".");
    },
    setPrevious() {
      this.previous = this.current;
      this.operatorClicked = true;
      this.clear();
    },
    setOperator(operatorSign) {
      this.operatorSign = operatorSign;
      this.setPrevious();
      this.current = operatorSign;
      this.toClear = true;
    },
    equals() {
      if (this.operatorClicked) {
        calculatorService
          .calculateExpression(this.previous, this.current, this.operatorSign)
          .then((result) => {
            this.pushToHistory(
              this.previous + this.operatorSign + this.current + "=" + result
            );
            this.operatorClicked = false;
            this.clear();
            this.current = result;
            this.toClear = true;
          });
      }
    },
    clearHistory() {
      this.history = [];
    },
    pushToHistory(expression) {
      this.history.push(expression);
      this.operatorSign = "";
    },
  },
};
</script>

<style scoped>
button {
  border-radius: 10%;
  padding: 15px;
  background: #c7edff;
}
</style>
