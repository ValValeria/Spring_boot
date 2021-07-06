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
        <div class="ads__search mb w-100 center" >
          <form class="row w-100 card-sm center">
            <div>
              <label for="inputPassword2" class="visually-hidden">Search</label>
              <input
                  type="text"
                  class="form-control"
                  id="inputPassword2"
                  placeholder="Type here ..."
                  :readonly="isLoading"
                  v-model="text"/>
            </div>
            <div>
              <ButtonComponent @click="search($event)">
                Find
              </ButtonComponent>
            </div>
          </form>
        </div>
        <div class="ads__list w-100">
          <FlexLayout v-if="!isLoading">
            <CardComponent
                v-for="ad in ads"
                :title="ad.title"
                :description="ad.description"
                :id="ad.id"
                :image="ad.image"
                :key="Math.random()"/>
          </FlexLayout>
          <div v-if="!isLoading && !ads.length" class="w-100 center mt">
             <h5>No results</h5>
          </div>
          <SpinnerComponent v-if="isLoading"/>
        </div>
        <div class="ads__pagination mt center w-100" v-if="allPages > 1">
          <nav>
            <ul class="pagination">
              <li class="page-item" v-if="page">
                <div class="page-link" @click="changePage(page-1)">Previous</div>
              </li>
              <li class="page-item" v-for="n in allPages" :key="Math.random()">
                <div :class="'page-link ' + (n-1===page ? 'active' : '')" @click="changePage(n-1)">{{n}}</div>
              </li>
              <li class="page-item" v-if="allPages > page+1">
                <div class="page-link" @click="changePage(page+1)">Next</div>
              </li>
            </ul>
          </nav>
        </div>
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
      size: 3,
      text: "",
      allPages: 0,
      isSearch: false
    }
  },
  components: {
    FlexLayout, CardComponent, SpinnerComponent, BasicLayout, ButtonComponent
  },
  async mounted(){
    const url = `/api/ads/?size=${this.size}&page=${this.page}`;

    await this.makeHttpRequest(url);
  },
  methods: {
    async search($event){
      $event.preventDefault();

      this.isSearch = true;

      await this.makeHttpRequest(`/api/search/ads?page=${this.page}&size=${this.size}&search=${encodeURIComponent(this.text)}`);
    },
    async makeHttpRequest(url){
      this.isLoading = true;

      const response = await fetch(url);

      if(response.ok){
        const data = await response.json();
        const paginationData = data.data.pagination;

        this.allPages = paginationData.totalPages;
        this.ads = paginationData.content;
        this.isLoading = false;
      }
    },
    async changePage(page){
      this.page = page;

      let url = `/api/ads?page=${this.page}&size=${this.size}`;

      if(this.isSearch){
        url += `&search=${encodeURIComponent(this.text)}`;
      }

      await this.makeHttpRequest(url);
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

li .active{
  background-color: #2487ce;
  color: white;
}

.ads__list{
  min-height:40vh;
}
</style>
