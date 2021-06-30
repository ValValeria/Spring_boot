<template>
<div class="home w-100">
  <BannerComponent/>
  <AboutUsComponent/>
  <ServiceComponent/>
  <BasicLayout title="Our advertisement">
      <div class="w-100 center">
        <CardComponent
            v-for="ad in ads"
            :title="ad.title"
            :description="ad.description"
            :id="ad.id"
            :image="ad.image"
            :key="Math.random()"/>
      </div>
  </BasicLayout>
  <section id="cta" class="cta">
    <BasicLayout>
      <div class="container aos-init aos-animate" data-aos="zoom-in">
        <div class="text-center">
          <h3>Call To Action</h3>
          <p> Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
          <a class="cta-btn" href="#">Call To Action</a>
        </div>
      </div>
    </BasicLayout>
  </section>
</div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import CardComponent from "../components/CardComponent";
import BannerComponent from "../components/BannerComponent";
import AboutUsComponent from "../components/AboutUsComponent";
import ServiceComponent from "../components/ServiceComponent";
export default {
  name: "HomePageComponent",
  components: {ServiceComponent, AboutUsComponent, BannerComponent, CardComponent, BasicLayout},
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
        this.ads = data.data.pagination;
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