import { createRouter, createWebHistory } from "vue-router";
import CalculatorView from "@/views/CalculatorView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: CalculatorView,
  },
  {
    path: "/login",
    name: "login",
    component: () =>
      import(/* webpackChunkName: "login" */ "@/views/LoginView.vue"),
  },
  {
    path: "/register",
    name: "register",
    component: () =>
      import(/* webpackChunkName: "register" */ "@/views/RegisterView.vue"),
  },
  {
    path: "/profile",
    name: "profile",
    component: () =>
      import(/* webpackChunkName: "profile" */ "@/views/ProfileView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
