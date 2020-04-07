import "@fortawesome/fontawesome-free/css/all.css";
import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/login.vue";
import Guest from "../views/guest.vue";
import SignUp from "../views/signup.vue";

Vue.use(VueRouter);

// console.log("router here");

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/login",
    name: "login",
    component: Login
  },
  {
    path: "/signup",
    name: "signup",
    component: SignUp
  },
  {
    path: "/guest",
    name: "guest",
    component: Guest
  },
  {
    path: "/loading",
    name: "loading",
    component: () => import("../views/Loading.vue")
  },
  {
    path: "/chatbot",
    name: "chatbot",
    component: () => import("../views/ChatBot.vue")
  }
];

const router = new VueRouter({
  routes
});

export default router;

