<template>
<div class="flex-layout w-100">
  <div class="flex-layout__wrap" :data-id="uniqueId" ref="wrap">
    <div class="flex-layout__items center w-100">
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
       this.handleUpdate();
    });
  },
  updated() {
    this.handleUpdate();
  },
  methods: {
    handleUpdate(){
      const element = this.$refs.wrap.firstElementChild;

      Array.from(element.children).forEach(v => {
        v.classList.add('flex__item');
      });
    }
  }
}
</script>

<style  lang="scss">
@import '../variables';

.flex-layout__items{
  flex-wrap:wrap;
}
</style>