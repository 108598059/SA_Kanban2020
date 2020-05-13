<template>
  <b-card v-bind:title="cardTitle" class="mb-2">
  </b-card>
</template>

<script>
export default {
  name: "Card",
  props: {
    cardTitle: String
  }
};
</script>

<style>
</style>