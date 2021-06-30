<template>
  <div class="card-sm">
    <div class="card-sm__wrap">
      <div class="card-sm__content center flex-column">
         <div class="card-sm__image mb">
           <img
               :src="image"
               class="img-fluid"
               alt="..."
           />
         </div>
        <div class="card-sm__title mb-half">
          <h4>
            {{title}}
          </h4>
        </div>
        <div class="card-sm__btn">
          <ButtonComponent @click="goToPost()">Read more</ButtonComponent>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ButtonComponent from "./ButtonComponent";
export default {
  name: "CardComponent",
  components: {ButtonComponent},
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
  },
  methods: {
    goToPost(){
      this.$router.push(`/post/${this.id}`);
    }
  }
}
</script>

<style lang="scss">
@import "../variables";

.card-sm{
  border-radius: 5px;
  padding: $space !important;
  background-color: white;
  box-shadow: 0 0 29px 0 rgb(18 66 101 / 8%);
  transition: all 0.3s ease-in-out;
}
</style>