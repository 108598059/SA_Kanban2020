<template>
  <div id="board" class="d-flex flex-row flex-grow-1">
    <Column title="Develop"></Column>
  </div>
</template>

<script>
import Column from "./Column";

export default {
  name: "Board",
  components: { Column }
};
</script>

<style scoped>
#board {
  border: 1px blue dashed;
}
</style>