<template>
  <div
    class="w-full max-w-sm mx-auto overflow-hidden bg-white rounded-lg shadow-md dark:bg-gray-800"
  >
    <div class="px-6 py-4">
      <h2 class="text-3xl font-bold text-center text-gray-700 dark:text-white">
        Eriks kalkulator :))
      </h2>

      <p class="mt-1 text-center text-gray-500 dark:text-gray-400">
        Innlogging
      </p>

      <Form @submit="handleLogin" :validation-schema="schema">
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

        <div class="flex items-center justify-between mt-4">
          <a
            href="#"
            class="text-sm text-gray-600 dark:text-gray-200 hover:text-gray-500"
            >Glemt passord?</a
          >

          <button
            :disabled="loading"
            class="px-4 py-2 leading-5 text-white transition-colors duration-200 transform bg-gray-700 rounded hover:bg-gray-600 focus:outline-none"
          >
            <span v-show="loading"></span>
            <span>Logg inn</span>
          </button>
        </div>
        <div v-if="message" class="alert alert-danger" role="alert">
          {{ message }}
        </div>
      </Form>
    </div>

    <div
      class="flex items-center justify-center py-4 text-center bg-gray-50 dark:bg-gray-700"
    >
      <span class="text-sm text-gray-600 dark:text-gray-200"
        >Har du ikke bruker?
      </span>

      <router-link
        class="mx-2 text-sm font-bold text-blue-500 dark:text-blue-400 hover:underline"
        to="/register"
        >Lag ny</router-link
      >
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
export default {
  name: "LoginComponent",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
    });
    return {
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
    handleLogin(user) {
      this.loading = true;
      console.log(user);
      this.$store.dispatch("auth/login", user).then(
        () => {
          this.$router.push("/profile");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
};
</script>
<style scoped></style>
