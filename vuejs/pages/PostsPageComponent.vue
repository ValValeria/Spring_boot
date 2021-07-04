<template>
  <div class="ads w-100">
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
    <div class="ads__area">
      <BasicLayout :title="'Our ads'" :is-section="false">
        <div class="ads__search mb w-100 center" v-if="!isLoading">
          <form class="row w-100 card-sm center">
            <div>
              <label for="inputPassword2" class="visually-hidden">Search</label>
              <input type="text" class="form-control" id="inputPassword2" placeholder="Type here ..." v-model="text">
            </div>
            <div>
              <ButtonComponent @click="search($event)">
                Find
              </ButtonComponent>
            </div>
          </form>
        </div>
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
  </div>
</template>

<script>
import FlexLayout from "../layouts/FlexLayout";
import CardComponent from "../components/CardComponent";
import SpinnerComponent from "../components/SpinnerComponent";
import BasicLayout from "../layouts/BasicLayout";
import ButtonComponent from "../components/ButtonComponent";

export default {
  name: "PostsPageComponent",
  data: function(){
    return {
      isLoading: true,
      ads: [],
      page: 0,
      size: 10,
      text: ""
    }
  },
  components: {
    FlexLayout, CardComponent, SpinnerComponent, BasicLayout, ButtonComponent
  },
  async mounted(){
    const response = await fetch(`/api/ads/?size=${this.size}&page=${this.page}`);

    if(response.ok){
      const data = await response.json();

      this.ads = data.data.pagination.content;
      this.isLoading = false;
    }
  },
  methods: {
    async search($event){
      $event.preventDefault();

      this.isLoading = true;

      const url = `/api/search/ads?page=${this.page}&size=${this.size}&search=${encodeURIComponent(this.text)}`;
      const response = await fetch(url);

      if(response.ok){
        const data = await response.json();
        this.ads = data.data.results.content;
        this.isLoading = false;
      }
    }
  }
}
</script>

<style scoped lang="scss">
@import "../variables";

form{
  max-width: 700px;
}

form div:nth-child(1){
  flex-grow: 1;
  margin-right: $space;
}

.users__item{
  flex: 1 1 33%;
}

.ads__area{
  min-height: 40vh;
}
</style>

<style>
.ads .flex-layout__items{
  justify-content: flex-start !important;
}
</style>