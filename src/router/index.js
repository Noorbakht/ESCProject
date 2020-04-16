import "@fortawesome/fontawesome-free/css/all.css";
import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/login.vue";
import Guest from "../views/guest.vue";
import SignUp from "../views/signup.vue";
// import VueResource from "vue-resource";
// Vue.use(VueResource);

Vue.use(VueRouter);
const router = new VueRouter({
    routes
});

console.log("router here");

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
    },
    {
        path: "/call",
        name: "call",
        component: () => import("../views/Call.vue")
    }
];

// router.beforeEach((to, from, next) => {
//     if (to.name !== "Home") {
//         next({ name: "Home" });
//     } else {
//         next();
//     }
// });
// router.beforeEach((to, from, next) => {
//     if (to.name == "loading" || to.name == "guest") {
//         next({ name: "Home" });
//     } else {
//         next();
//     }
//     if (to.name == "chatbot" || to.name == "Call") {
//         next({ name: "Home" });
//     }
// });

export default router;
