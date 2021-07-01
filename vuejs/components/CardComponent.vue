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
