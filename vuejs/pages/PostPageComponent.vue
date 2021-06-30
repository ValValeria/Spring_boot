<template>
  <BasicLayout>
     <div v-if="!Object.keys(post).length" class="w-100 center">
       <div class="spinner-border text-primary" role="status">
       </div>
     </div>
     <div v-else>

     </div>
  </BasicLayout>
</template>

<script>
import BasicLayout from "../layouts/BasicLayout";

export default {
  name: "PostPageComponent",
  data: function(){
    return {
      post: {}
    }
  },
  computed: {
    postId(){
      return this.$route.params.id;
    }
  },
  mounted() {
    this.$nextTick(async () => {
       const response = await fetch(`/api/ad/${this.postId}`);

       if(response.ok){
         const data = await response.json();

         if(data && typeof data === "object"){
           this.post = data.data.ad;
         }
       } else {
         await this.$router.push("/");
       }
    });
  },
  components: {
    BasicLayout
  }
}
</script>

<style scoped>

</style>