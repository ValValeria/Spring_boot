<template>
<div class="home w-100 bg-light">
  <section class="home__articles">
    <BasicLayout title="Our advertisement">
      <div class="w-100 center">
        <CardComponent
            v-for="ad in ads"
            :title="ad.title"
            :description="ad.description"
            :id="ad.id"
            :key="Math.random()"/>
      </div>
    </BasicLayout>
    <BasicLayout title="Our users"></BasicLayout>
    <BasicLayout title="Our services"></BasicLayout>
  </section>
</div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import CardComponent from "../components/CardComponent";
export default {
  name: "HomePageComponent",
  components: {CardComponent, BasicLayout},
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

</style>