import "@fortawesome/fontawesome-free/css/all.css";
import Vue from "vue";
import Router from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/login.vue";
import Guest from "../views/guest.vue";
import SignUp from "../views/signup.vue";
import { store } from "../plugins/store.js";

Vue.use(Router);

// console.log("router here");

let router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
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
    },
    {
      path: "/call",
      name: "call",
      component: () => import("../views/Call.vue")
    },
    {
      path: "/feedback",
      name: "feedback",
      component: () => import("../views/Feedback.vue")
    }
  ]
});

router.beforeEach((to, from, next) => {
  //prevent people from entering chatbot page directly
  if (
    to.name == "chatbot" &&
    store.state.firstname == "" &&
    store.state.lastname == ""
  ) {
    next({ name: "Home" });
  } else {
    next();
  }

  //prevent people from entering guest form directly
  if (to.name == "guest" && from.name != "Home") {
    next({ name: "Home" });
  }
});

export default router;
