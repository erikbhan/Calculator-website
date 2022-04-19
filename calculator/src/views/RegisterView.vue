<template>
  <div
    class="w-full max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800"
  >
    <div class="px-6 py-4">
      <h2 class="text-3xl font-bold text-center text-gray-700 dark:text-white">
        Eriks kalkulator :))
      </h2>

      <p class="mt-1 text-center text-gray-500 dark:text-gray-400">
        Lag ny bruker
      </p>

      <Form @submit="handleRegister" :validation-schema="schema">
        <div class="w-full mt-4">
          <Field
            name="username"
            class="block w-full px-4 py-2 mt-2 text-gray-700 dark:text-gray-200 placeholder-gray-500 bg-white border rounded-md dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300"
            type="text"
            placeholder="Brukernavn"
            aria-label="Brukernavn"
          />
          <ErrorMessage name="username" class="error-feedback" />
        </div>

        <div class="w-full mt-4">
          <Field
            name="password"
            class="block w-full px-4 py-2 mt-2 text-gray-700 dark:text-gray-200 placeholder-gray-500 bg-white border rounded-md dark:bg-gray-800 dark:border-gray-600 dark:placeholder-gray-400 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-opacity-40 focus:outline-none focus:ring focus:ring-blue-300"
            type="password"
            placeholder="Passord"
            aria-label="Passord"
          />
          <ErrorMessage name="password" class="error-feedback" />
        </div>

        <div class="flex items-right justify-between mt-4">
          <button
            :disabled="loading"
            class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-gray-700 rounded hover:bg-gray-600 focus:outline-none"
          >
            <span v-show="loading"></span>
            <span>Registrer deg</span>
          </button>
        </div>
        <div v-if="message" class="alert alert-danger" role="alert">
          {{ message }}
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "RegisterView",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup
        .string()
        .required("Username is required!")
        .min(3, "Must be at least 3 characters!")
        .max(64, "Must be maximum 64 characters!"),
      password: yup
        .string()
        .required("Password is required!")
        .min(4, "Must be at least 4 characters!")
        .max(64, "Must be maximum 64 characters!"),
    });
    return {
      successful: false,
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleRegister(user) {
      this.message = "";
      this.successful = false;
      this.loading = true;
      this.$store.dispatch("auth/register", user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
          this.$router.push("/login"); //TODO: Auto login
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
          this.loading = false;
        }
      );
    },
  },
};
</script>
<style scoped></style>
