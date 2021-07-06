<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid justify-content-between">
      <div class="navbar-brand">
          <router-link to="/">
            <h4>InAds</h4>
          </router-link>
      </div>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link scrollto" to="/">Home</router-link>
          </li>
          <li class="nav-item" v-if="!isAuth"><a class="nav-link scrollto " href="/login">Login</a></li>
          <li class="nav-item" v-if="!isAuth"><a class="nav-link scrollto " href="/signup">Sign up</a></li>
          <li class="nav-item" v-if="isAuth"><router-link class="nav-link scrollto " to="/create-ad">Add an advertisement</router-link></li>
          <li class="nav-item"><router-link class="nav-link scrollto " to="/ads">Advertisements</router-link></li>
          <li class="nav-item"><router-link class="nav-link scrollto " to="/users">Users</router-link></li>
          <li class="nav-item" v-if="isAuth"><router-link class="nav-link scrollto " :to="'/user/'+user.id" >Profile</router-link></li>
        </ul>
      </div>
    </div>
  </nav>
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

<style scoped lang="scss">
@import "../variables";

.bg-white{
  background-color: white;
}

nav{
  position: fixed !important;
  top:0 !important;
  left:0 !important;
  width: 100% !important;
  z-index: 88888888 !important;

  >div{
    padding: 0 $space !important;
  }
}

.show ul{
  display: flex !important;
  min-height:90vh;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  li{
    text-align: center;
    display: flex;
    justify-content: center;
    padding: $space/2;
  }

  a{
    font-size: 25px;
    text-transform: uppercase;
  }
}
</style>