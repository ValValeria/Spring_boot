import App from "./App";
import Vue from 'vue';
import {store} from "./store";
import {router} from "./routes";

new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount("#app");

