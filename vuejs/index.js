import App from "./App";
import Vue from 'vue';
import VueRouter from 'vue-router';
import HomePageComponent from "./pages/HomePageComponent";
import PostPageComponent from "./pages/PostPageComponent";
import NotFoundPageComponent from "./pages/NotFoundPageComponent";

Vue.use(VueRouter);

const routes = [
    {path: "/", component: HomePageComponent},
    {path: "/post/:id", component: PostPageComponent},
    {path: "*", component: NotFoundPageComponent}
];

const router = new VueRouter({
    mode: 'history',
    routes
});

new Vue({
    router,
    render: (h) => h(App),
}).$mount("#app");

