<template>
  <div class="user w-100">
    <BasicLayout :isSection="false">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white">
          <li class="breadcrumb-item" aria-current="page">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item active" aria-current="page">
            <router-link :to="this.$router.history.current.path">Profile</router-link>
          </li>
        </ol>
      </nav>
    </BasicLayout>
    <BasicLayout title="Profile" v-if="Object.keys(user).length" :is-section="false">
      <div class="user__data mb">
        <FlexLayout>
          <div class="user__image card-sm">
            <img :src="user.image" alt="..."/>
          </div>
          <div class="user__profile-info card-sm">
            <FlexLayout>
              <div>
                <h6>Username: </h6>
                <p>{{user.username}}</p>
              </div>
              <div>
                <h6>Role: </h6>
                <p>{{user.role}}</p>
              </div>
            </FlexLayout>
            <div>
              <h6>About the user</h6>
              <p>{{user.description || "Hi! I have decided not to tell about myself"}}</p>
            </div>
          </div>
        </FlexLayout>
      </div>
      <div class="user__ads mt" v-if="!isLoading && ads.length">
        <div class="w-100 center mb mt">
          <h3>The ads of user</h3>
        </div>
        <div class="user__ads-list">
          <FlexLayout v-if="!isLoading">
            <CardComponent
                v-for="ad in ads"
                :title="ad.title"
                :description="ad.description"
                :id="ad.id"
                :image="ad.image"
                :key="Math.random()"/>
          </FlexLayout>
        </div>
      </div>
    </BasicLayout>
    <BasicLayout v-else title="Loading"></BasicLayout>
  </div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import FlexLayout from "../layouts/FlexLayout";
import CardComponent from "../components/CardComponent";
import SpinnerComponent from "../components/SpinnerComponent";

export default {
  name: "UserPageComponent",
  data: function(){
    return {
      user: [],
      ads: [],
      page: 0,
      size: 10,
      isLoading: true
    };
  },
  components:{
    SpinnerComponent,
    BasicLayout, FlexLayout, CardComponent
  },
  computed:{
    id(){
      return this.$route.params.id;
    }
  },
  async mounted(){
    try{
      const responses = await Promise.all([
        fetch(`/api/user/${this.id}`),
        fetch(`/api/ads/${this.id}?page=${this.page}&size=${this.size}`)
      ]);

      if(responses.every(v => v.ok)){
        const [userData, userAds] = await Promise.all([...responses.map(v => v.json())]);

        this.user = userData.data.user;
        this.ads = userAds.data.pagination.content;
        this.isLoading = false;
      }
    } catch (e){
      await this.$router.push("/");
    }
  }
}
</script>

<style scoped lang="scss">
.user__image{
  max-width: 200px;
  flex: 1 1 30%;
  width: 30%;

  img{
    object-fit: cover;
    width: 100%;
    height: 100%;
  }
}

.user__profile-info {
  flex: 1 1 70%;
  max-width: 70%;
  width: 70%;
}

</style>

<style>
.user .user__data .flex-layout__items{
  align-items: flex-start !important;
  justify-content: flex-start !important;
}
</style>