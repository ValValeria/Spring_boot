<template>
  <div class="user-ads w-100">
    <BasicLayout :isSection="false">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white">
          <li class="breadcrumb-item active" aria-current="page">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item" aria-current="page">
            <router-link :to="this.$router.history.current.path">Ads</router-link>
          </li>
        </ol>
      </nav>
    </BasicLayout>
    <BasicLayout :title="'Our ads'">
      <FlexLayout v-if="!isLoading">
        <CardComponent
            v-for="ad in ads"
            :title="ad.title"
            :description="ad.description"
            :id="ad.id"
            :image="ad.image"
            :key="Math.random()"/>
      </FlexLayout>
      <SpinnerComponent v-else/>
    </BasicLayout>
  </div>
</template>

<script>
import FlexLayout from "../layouts/FlexLayout";
import CardComponent from "../components/CardComponent";
import SpinnerComponent from "../components/SpinnerComponent";
import BasicLayout from "../layouts/BasicLayout";

export default {
  name: "PostsPageComponent",
  data: function(){
    return {
      isLoading: true,
      ads: [],
      page: 0,
      size: 10
    }
  },
  components: {
    FlexLayout, CardComponent, SpinnerComponent, BasicLayout
  },
  async mounted(){
    const response = await fetch(`/api/ads/?size=${this.size}&page=${this.page}`);

    if(response.ok){
      const data = await response.json();

      this.ads = data.data.pagination.content;
      this.isLoading = false;
    }
  }
}
</script>

<style scoped>

</style>