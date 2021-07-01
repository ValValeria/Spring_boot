<template>
<div class="flex-layout">
  <div class="flex-layout__wrap">
    <div class="flex-layout__items">
      <slot></slot>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: "FlexLayout",
  mounted() {
    this.$nextTick(v => {
       const element = document.querySelector(".flex-layout__items");

       Array.from(element.children).forEach(v => {
         v.classList.add('flex__item')
       });
    })
  }
}
</script>

<style scoped lang="scss">
@import '../variables';

.flex__item{
  margin-right: $space !important;
}

.flex__item :last-child(1){
  margin-right: 0;
}
</style>