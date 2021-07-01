<template>
  <div class="post w-100">
    <BasicLayout :isSection="false">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white">
          <li class="breadcrumb-item active" aria-current="page">
            <router-link to="/">Home</router-link>
          </li>
          <li class="breadcrumb-item" aria-current="page">
            <router-link :to="this.$router.history.current.path">Ad</router-link>
          </li>
        </ol>
      </nav>
    </BasicLayout>

    <BasicLayout v-if="!Object.keys(post).length">
      <div class="w-100 center">
        <div class="spinner-border text-primary" role="status"></div>
      </div>
    </BasicLayout>

    <BasicLayout :title="post.title" v-else :isSection="false">
      <div class="post__author p-all center justify-content-start">
        <h6 class="mb-0 mr-2">Author: </h6>
        <router-link :to="'/user/'+userData.id">
          {{userData.username}}
        </router-link>
      </div>
      <div class="post__img mb w-100">
        <div class="card-sm p-all w-100">
          <img :src="post.image" alt="..."/>
        </div>
      </div>
      <div class="post__description mb w-100">
        <div class="card-sm p-all w-100">
          <h6 class="mb-half">Description</h6>
          <p>
            {{post.description}}
          </p>
        </div>
      </div>
    </BasicLayout>

    <BasicLayout :is-section="false">
      <div class="w-100 center flex-column">
        <h2 class="mb">Other ads</h2>

        <FlexLayout>
          <CardComponent
              v-for="ad in ads"
              :title="ad.title"
              :description="ad.description"
              :id="ad.id"
              :image="ad.image"
              :key="Math.random()"/>
        </FlexLayout>
      </div>
    </BasicLayout>
  </div>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";
import CardComponent from "../components/CardComponent";
import FlexLayout from "../layouts/FlexLayout";

export default {
  name: "PostPageComponent",
  data: function(){
    return {
      post: {},
      userData: {},
      ads: []
    }
  },
  computed: {
    postId(){
      return this.$route.params.id;
    }
  },
  async mounted() {
    try{
      const responses = await Promise.all([
        fetch(`/api/ad/${this.postId}`),
        fetch(`/api/ads/?excludedId=${this.postId}&page=0&size=3`),
      ]);

      if(responses[0].ok && responses[1].ok){
        const post = await responses[0].json();
        this.post = post.data.ad;
        this.userData = post.data.ad.user;

        const otherPosts = await responses[1].json();
        this.ads = otherPosts.data.pagination;
      } else {
        throw new Error();
      }
    }catch (e){
      await this.$router.push("/");
    }
  },
  components: {
    CardComponent,
    BasicLayout,
    FlexLayout
  }
}
</script>

<style scoped lang="scss">
img{
  object-fit: cover;
  width: 100%;
  height: 100%;
  max-height: 250px;
}
</style>