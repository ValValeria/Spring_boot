<template>
  <header id="header" class="fixed-top">
    <div class="container d-flex align-items-center justify-content-between">

      <h1 class="logo"><a href="index.html">InAds</a></h1>

      <nav id="navbar" class="navbar">
        <ul>
          <li><router-link class="nav-link scrollto active" to="/">Home</router-link></li>
          <li><a class="nav-link scrollto " href="/login" v-if="!isAuth">Login</a></li>
          <li><a class="nav-link scrollto " href="/signup" v-if="!isAuth">Sign up</a></li>
          <li><router-link class="nav-link scrollto " to="/create-ad" v-if="isAuth">Add an advertisement</router-link></li>
          <li><router-link class="nav-link scrollto " to="/ads">Advertisements</router-link></li>
          <li><router-link class="nav-link scrollto " to="/users">Users</router-link></li>
          <li><router-link class="nav-link scrollto " :to="'/user/'+user.id" v-if="isAuth">Profile</router-link></li>
        </ul>
        <i class="bi bi-list mobile-nav-toggle"></i>
      </nav>
    </div>
  </header>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import {mapState} from "vuex";

export default {
name: "HeaderComponent",
  components: {BasicLayout},
  computed:{
    ...mapState({
      isAuth: state => state.isAuth,
      user: state => state.user
    })
  }
}
</script>

<style scoped>
.bg-white{
  background-color: white;
}
</style>