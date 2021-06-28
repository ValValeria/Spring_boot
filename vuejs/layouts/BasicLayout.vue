<template>
<div class="basic-layout w-100 ">
  <div class="basic-layout__container w-100">
    <div :class="classes">
      <h3 v-if="title" class="text-center">{{title.toUpperCase()}}</h3>
      <div class="basic-layout__content">
        <slot></slot>
      </div>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: "BasicLayout",
  props:{
    title: {
      type: String,
      required: false
    },
    isSection: {
      type: Boolean,
      default: true
    }
  },
  computed:{
    classes(){
      let clCss = 'basic-layout__wrap ';

      if(!this.isSection){
         clCss += 'wrap-md';
      } else {
         clCss += 'wrap';
      }

      return 'basic-layout__wrap ' + clCss;
    }
  }
}
</script>

<style scoped>

</style>