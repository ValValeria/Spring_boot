<template>
  <div class="app w-100">
    <HeaderComponent/>
    <div class="app__content">
      <router-view></router-view>
    </div>
    <FooterComponent/>
  </div>
</template>

<script>
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";

export default {
  name: "App",
  components:{
    FooterComponent,
    HeaderComponent
  }
}
</script>

<style lang="scss">
@import "./style";

.app{
  min-height: 100vh;
}

.app__content{
  min-height: 80vh;
}
</style>