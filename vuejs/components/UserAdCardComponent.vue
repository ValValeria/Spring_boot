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
          <div class="mb w-100">
            <ButtonComponent @click="goToPost()">{{"Read more"}}</ButtonComponent>
          </div>
          <div class="mb w-100" v-if="isAllowed" >
            <button @click="deleteThePost()" class="btn btn-danger w-100">{{"Delete the post"}}</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ButtonComponent from "./ButtonComponent";
import {mapState} from "vuex";
import {DELETE_AD$} from "../store";

export default {
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
    },
  },
  computed:{
    linkToNavigate(){
      return `/post/${this.id}`;
    },
    ...mapState({
      user: state => state.user
    }),
    isAllowed(){
      return this.id === this.user.id || this.user.role === "admin";
    }
  },
  methods: {
    goToPost(){
      this.$router.push(this.linkToNavigate);
    },
    async deleteThePost(){
      const response = await fetch(`/api/delete-ad/${this.id}`);

      if(response.ok){
         const data = await response.json();

         if(data.status === "ok"){
           DELETE_AD$.next({id: this.id});
         }
      }
    }
  }
}
</script>

<style scoped>

</style>