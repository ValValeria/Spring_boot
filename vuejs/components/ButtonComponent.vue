<template>
<button class="button button-blue btn-get-started" @click="$emit('click', $event)">
  <div class="h6">
    <slot></slot>
  </div>
</button>
</template>

<script>
export default {
  name: "ButtonComponent"
}
</script>

<style scoped lang="scss">
@import "../style";

.button{
  min-width: 110px;
  padding: $space/2;
  text-align: center;
  background: #2487ce;
  border-radius: 5px;
  font-weight: 400;
  outline:none;
  border: none;

  .h6{
    font-weight: 500;
    color: #fff !important;
  }
}
</style>