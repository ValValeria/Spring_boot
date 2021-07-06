import Vue from "vue";
import VueRouter from "vue-router";
import HomePageComponent from "../pages/HomePageComponent";
import PostPageComponent from "../pages/PostPageComponent";
import UserAdsPageComponent from "../pages/UserAdsPageComponent";
import UsersPageComponent from "../pages/UsersPageComponent";
import UserPageComponent from "../pages/UserPageComponent";
import PostsPageComponent from "../pages/PostsPageComponent";
import NotFoundPageComponent from "../pages/NotFoundPageComponent";
import {store} from "../store";
import {SET_USER} from "../store/mutations";
import CreateAdPageComponent from "../pages/CreateAdPageComponent";

Vue.use(VueRouter);

const routes = [
    {path: "/", component: HomePageComponent},
    {path: "/post/:id", component: PostPageComponent},
    {path: "/user-ads/:id", component: UserAdsPageComponent},
    {path: "/users", component: UsersPageComponent},
    {path: "/user/:id", component: UserPageComponent},
    {path: "/create-ad", component: CreateAdPageComponent},
    {path: "/ads", component: PostsPageComponent},
    {path: "*", component: NotFoundPageComponent}
];

const router = new VueRouter({
    mode: 'history',
    routes
});

router.beforeEach(async (to, from, next) => {
    try{
        const response = await fetch('/api/auth-user');

        if(response.ok){
            const json = await response.json();
            const user = json.data.user;

            store.commit(SET_USER, {user});
        }
    }catch(e){
        console.error(e.message)
    }

    next();
});

export {router};