import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import VueResource from "vue-resource";
import VueSimpleAlert from "vue-simple-alert";
import VueSocketIO from "vue-socket.io";

Vue.use(
    new VueSocketIO({
        debug: true,
        connection: "https://limitless-sierra-11102.herokuapp.com/",
        options: { autoConnect: false }
    })
);

Vue.use(VueSimpleAlert);
Vue.use(VueResource);

Vue.config.productionTip = false;

new Vue({
    router,
    vuetify,
    render: h => h(App)
}).$mount("#app");
