import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import VueResource from "vue-resource";
import VueSimpleAlert from "vue-simple-alert";
import VueSocketIO from "vue-socket.io";
import { store } from "./plugins/store";
import VueUp from "vueup";

Vue.use(VueUp);

import VueChatScroll from "vue-chat-scroll";
Vue.use(VueChatScroll);

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
    store: store,
    render: h => h(App)
}).$mount("#app");
