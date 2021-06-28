import App from "./App";
import Vue from 'vue';
import VueRouter from 'vue-router';
import HomePageComponent from "./pages/HomePageComponent";

Vue.use(VueRouter);

const routes = [
    {path: "/", component: HomePageComponent}
];

const router = new VueRouter({
    mode: 'history',
    routes
});

new Vue({
    router,
    render: (h) => h(App),
}).$mount("#app");

