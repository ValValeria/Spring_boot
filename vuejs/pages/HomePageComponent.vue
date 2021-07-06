<template>
<div class="home w-100">
  <BannerComponent/>
  <AboutUsComponent/>
  <ServiceComponent/>
  <BasicLayout title="Our advertisement">
      <div class="w-100 center" v-if="ads.length">
        <CardComponent
            v-for="ad in ads"
            :title="ad.title"
            :description="ad.description"
            :id="ad.id"
            :image="ad.image"
            :key="Math.random()"/>
      </div>

      <div v-else>
        <SpinnerComponent/>
      </div>
  </BasicLayout>
  <div id="cta" class="cta w-100">
    <BasicLayout>
      <div class="container aos-init aos-animate" data-aos="zoom-in">
        <div class="text-center">
          <h2 class="mb text-white">
            {{"Create your advertisement".toUpperCase()}}
          </h2>
          <a class="cta-btn" href="/create-ad">Learn more</a>
        </div>
      </div>
    </BasicLayout>
  </div>
</div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import CardComponent from "../components/CardComponent";
import BannerComponent from "../components/BannerComponent";
import AboutUsComponent from "../components/AboutUsComponent";
import ServiceComponent from "../components/ServiceComponent";
import SpinnerComponent from "../components/SpinnerComponent";


export default {
  name: "HomePageComponent",
  components: {ServiceComponent, AboutUsComponent, BannerComponent, CardComponent, BasicLayout, SpinnerComponent},
  data: function (){
    return {
      page: 0,
      per_page: 3,
      ads: []
    }
  },
  async mounted() {
    try{
      const response = await fetch(`/api/ads?page=${this.page}&size=${this.per_page}`);

      if(response.ok){
        const data = await response.json();
        this.ads = data.data.pagination.content;
      }
    } catch (e){
      console.error(e);
    }
  }
}
</script>

<style scoped lang="scss">
@import '../variables';

.card-sm{
  margin-right: $space !important;
}

.card:last-child(1){
  margin-right: 0;
}
</style>