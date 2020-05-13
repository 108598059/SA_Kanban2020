<template>
  <b-card v-bind:name="cardName" class="mb-2">
  </b-card>
</template>

<script>
export default {
  name: "Card",
  props: {
    cardName: String
  }
};
</script>

<style>
</style>
