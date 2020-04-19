import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import VueResource from "vue-resource";
import VueSimpleAlert from "vue-simple-alert";
import VueSocketIO from "vue-socket.io";
import { store } from "./plugins/store";
import Vuesax from "vuesax";
import { vsButton, vsSelect, vsPopup } from "vuesax";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import "vuesax/dist/vuesax.css"; //Vuesax styles
import VueFormulate from "@braid/vue-formulate";

Vue.use(VueFormulate);

Vue.use(VueMaterial);
Vue.use(Vuesax, {
    vsButton,
    vsSelect,
    vsPopup
    // options here
});
Vue.use(VueSimpleAlert);
Vue.use(VueResource);

Vue.use(
    new VueSocketIO({
        debug: true,
        connection: "https://limitless-sierra-11102.herokuapp.com/availability",
        options: { autoConnect: false }
    })
);

Vue.config.productionTip = false;

new Vue({
    router,
    vuetify,
    store: store,
    render: h => h(App)
}).$mount("#app");
