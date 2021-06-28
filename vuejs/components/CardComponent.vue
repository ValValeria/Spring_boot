<template>
  <div class="card">
    <img :src="image" class="card-img-top" alt="...">
    <div class="card-body">
      <h5 class="card-title">{{title}}</h5>
      <p class="card-text">
        {{description.slice(0, 30) + "..."}}
      </p>
      <router-link :to="'/ads/'+id" class="btn btn-primary">Read more</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "CardComponent",
  props:{
    title:{
      type: String
    },
    description: {
      type: String
    },
    id: {
      type: Number
    },
    image: {
      type: String
    }
  }
}
</script>

<style scoped>

</style>