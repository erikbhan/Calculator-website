<template>
  <nav class="bg-indigo-100 shadow dark:bg-gray-800">
    <div
      class="container px-6 py-4 mx-auto md:flex md:justify-between md:items-center"
    >
      <div class="flex items-center justify-between">
        <div>
          <router-link
            class="text-2xl font-bold text-gray-800 transition-colors duration-200 transform dark:text-white lg:text-3xl hover:text-gray-700 dark:hover:text-gray-300"
            to="/"
            >Eriks kalkulator</router-link
          >
        </div>
      </div>

      <!-- Mobile Menu open: "block", Menu closed: "hidden" -->
      <div class="items-center">
        <div class="flex flex-col md:flex-row md:mx-6">
          <router-link
            v-show="!loggedIn"
            class="my-1 text-sm font-medium text-gray-700 transition-colors duration-200 transform dark:text-gray-200 hover:text-blue-500 dark:hover:text-blue-400 md:mx-4 md:my-0"
            to="/login"
            >Logg inn</router-link
          >
          <router-link
            v-show="loggedIn"
            class="my-1 text-sm font-medium text-gray-700 transition-colors duration-200 transform dark:text-gray-200 hover:text-blue-500 dark:hover:text-blue-400 md:mx-4 md:my-0"
            to="/profile"
            >Profil</router-link
          >
          <button
            v-show="loggedIn"
            @click="logOut()"
            class="my-1 text-sm font-medium text-gray-700 transition-colors duration-200 transform dark:text-gray-200 hover:text-blue-500 dark:hover:text-blue-400 md:mx-4 md:my-0"
          >
            Logg ut
          </button>
        </div>
      </div>
    </div>
  </nav>
  <br />
  <div class="m-auto">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    },
  },
};
</script>
