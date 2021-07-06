<template>
  <div class="users w-100">
    <BasicLayout :isSection="false">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white">
          <li class="breadcrumb-item active" aria-current="page">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item" aria-current="page">
            <router-link :to="this.$router.history.current.path">Users</router-link>
          </li>
        </ol>
      </nav>
    </BasicLayout>

    <div class="users__area">
      <BasicLayout title="Our users" :is-section="false">
        <div class="users__search center mb">
          <form class="row w-100 card-sm center">
            <div>
              <label for="inputPassword2" class="visually-hidden">Search</label>
              <input type="text" class="form-control" id="inputPassword2" placeholder="Type here ..." v-model="searchText">
            </div>
            <div>
              <ButtonComponent @click="search($event)">
                Find
              </ButtonComponent>
            </div>
          </form>
        </div>
        <div class="users__list center">
          <FlexLayout v-if="users.length && !isLoading">
            <div class="users__item" v-for="user in users">
              <CardComponent
                  :title="user.username"
                  :description="user.role"
                  :image="user.image"
                  :id="user.id"
                  :key="Math.random()"
                  :link="'/user/'+user.id"
                  :linkText="'Visit profile'"
              />
            </div>
          </FlexLayout>
          <div v-if="!isLoading && !users.length" class="w-100 center mt">
            <h5>No results</h5>
          </div>
          <SpinnerComponent v-if="isLoading"/>
        </div>
        <div class="users__pagination w-100 center mt"  v-if="allPages > 1">
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
import BasicLayout from "../layouts/BasicLayout";
import ButtonComponent from "../components/ButtonComponent";
import FlexLayout from "../layouts/FlexLayout";
import CardComponent from "../components/CardComponent";
import SpinnerComponent from "../components/SpinnerComponent";

export default {
  name: "UsersPageComponent",
  components: {FlexLayout, ButtonComponent, BasicLayout, CardComponent, SpinnerComponent},
  data: function(){
    return {
      page: 0,
      size: 3,
      users: [],
      searchText: "",
      isSearch: false,
      allPages: 0,
      isLoading: true
    }
  },
  async mounted(){
     const url = `/api/users?page=${this.page}&size=${this.size}`;

     await this.makeHttpRequest(url);
  },
  methods:{
    async search($event){
      $event.preventDefault();
      this.clearPaginationData();

      this.isSearch = true;

      const url = `/api/search/user?page=${this.page}&size=${this.size}&search=${encodeURIComponent(this.searchText)}`;

      await this.makeHttpRequest(url);
    },

    clearPaginationData(){
      this.page = 0;
    },

    async makeHttpRequest(url){
      this.isLoading = true;

      const response = await fetch(url);

      if(response.ok){
        const data = await response.json();
        const paginationData = data.data.pagination;

        this.allPages = paginationData.totalPages;
        this.users = paginationData.content;
        this.isLoading = false;
      }
    },
    async changePage(page){
      this.page = page;

      let url = `/api/users?page=${this.page}&size=${this.size}`;

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

.users__area{
  min-height: 40vh;
}

li .active{
  background-color: #2487ce;
  color: white;
}
</style>