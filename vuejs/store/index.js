import Vue from 'vue';
import Vuex from 'vuex';
import {SET_USER} from "./mutations";
import {User} from "../models/User";

Vue.use(Vuex);

const store = new Vuex.Store({
   state:{
       user: new User(),
       isAuth: false
   },
   mutations:{
       [SET_USER](state, action){
           state.user = new User(action.user);
           state.isAuth = state.user.isAuth;
       }
   }
});

export {store};