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
          <FlexLayout v-if="users.length">
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
          <div v-if="isSearch && !users.length">
            <h6 class="mt">No results. Sorry :(</h6>
          </div>
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

export default {
  name: "UsersPageComponent",
  components: {FlexLayout, ButtonComponent, BasicLayout, CardComponent},
  data: function(){
    return {
      page: 0,
      size: 3,
      users: [],
      searchText: "",
      isSearch: false
    }
  },
  async mounted(){
     const response = await fetch(`/api/users?page=${this.page}&size=${this.size}`);

     if(response.ok){
       const data = await response.json();
       this.users = data.data.pagination.content;
     }
  },
  methods:{
    async search($event){
      $event.preventDefault();
      this.clearPaginationData();

      this.isSearch = true;

      const url = `/api/search/user?page=${this.page}&size=${this.size}&search=${encodeURIComponent(this.searchText)}`;
      const response = await fetch(url);

      if(response.ok){
        const data = await response.json();
        this.users = data.data.results.content;
      }
    },

    clearPaginationData(){
      this.page = 0;
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
</style>