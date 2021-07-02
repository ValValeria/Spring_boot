<template>
<div class="flex-layout w-100">
  <div class="flex-layout__wrap" :data-id="uniqueId" ref="wrap">
    <div class="flex-layout__items center">
      <slot></slot>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: "FlexLayout",
  data(){
    return {
      uniqueId: Math.random()
    }
  },
  beforeCreate() {
    this.$nextTick(() => {
      const element = this.$refs.wrap.firstElementChild;

      Array.from(element.children).forEach(v => {
        v.classList.add('flex__item');
      });
    });
  }
}
</script>

<style  lang="scss">
@import '../variables';

</style>