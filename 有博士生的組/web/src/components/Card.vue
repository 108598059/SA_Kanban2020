<template>
  <b-card :title="card.name" class="mb-2">
  </b-card>
</template>

<script>
export default {
  name: "Card",
  props: {
    card: Object
  }
};
</script>

<style>
</style>
