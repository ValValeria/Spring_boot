import App from "./App";
import Vue from 'vue';
import VueRouter from 'vue-router';
import HomePageComponent from "./pages/HomePageComponent";
import PostPageComponent from "./pages/PostPageComponent";
import NotFoundPageComponent from "./pages/NotFoundPageComponent";
import UserAdsPageComponent from "./pages/UserAdsPageComponent";
import UsersPageComponent from "./pages/UsersPageComponent";
import UserPageComponent from "./pages/UserPageComponent";
import PostsPageComponent from "./pages/PostsPageComponent";

Vue.use(VueRouter);

const routes = [
    {path: "/", component: HomePageComponent},
    {path: "/post/:id", component: PostPageComponent},
    {path: "/user-ads/:id", component: UserAdsPageComponent},
    {path: "/users", component: UsersPageComponent},
    {path: "/user/:id", component: UserPageComponent},
    {path: "/ads", component: PostsPageComponent},
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

