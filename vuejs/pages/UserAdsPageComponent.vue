<template>
 <div class="user-ads w-100">
   <BasicLayout :title="'The ads of '+this.user.username" v-if="Object.keys(user).length">
       <FlexLayout>
           <CardComponent
               v-for="ad in ads"
               :title="ad.title"
               :description="ad.description"
               :id="ad.id"
               :image="ad.image"
               :key="Math.random()"/>
       </FlexLayout>
   </BasicLayout>

   <BasicLayout v-else-if="isLoading">
     <SpinnerComponent/>
   </BasicLayout>

   <BasicLayout v-else title="No ads related to user">
   </BasicLayout>
 </div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import SpinnerComponent from "../components/SpinnerComponent";
import FlexLayout from "../layouts/FlexLayout";
import CardComponent from "../components/CardComponent";

export default {
  name: "UserAdsPageComponent",
  components: {FlexLayout, SpinnerComponent, BasicLayout, CardComponent},
  data(){
    return {
      ads: [],
      page: 0,
      size: 4,
      user: {},
      isLoading: true
    }
  },
  computed: {
    id(){
      return this.$route.params.id;
    }
  },
  async mounted() {
    try{
      const responses = await Promise.all([
        fetch(`/api/ads/${this.id}?size=${this.size}&page=${this.page}`),
        fetch(`/api/user/${this.id}`),
      ]);

      if(responses.every(v => v.ok)){
        const data = await responses[0].json();
        this.ads = data.data.pagination || [];

        console.log(this.ads)

        const userData = await responses[1].json();
        this.user = userData.data.user || {};

        this.isLoading = false;
      }
    }catch (e){
      console.error(e)
    }
  }
}
</script>

<style scoped>

</style>