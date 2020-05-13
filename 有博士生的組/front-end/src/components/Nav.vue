<template>
  <div id="nav">
    <img alt="Kanban" src="../assets/logo.png" width="50" />
    <CreateCardModal></CreateCardModal>
  </div>
</template>

<script>
import CreateCardModal from "./CreateCardModal";

export default {
  name: "Nav",
  components: {
    CreateCardModal
  }
};
</script>

<style scoped>
#nav {
  padding-right: 10px;
  background-color: #fefeff;
  height: 60px;
  border: 1px pink dashed;
}
</style>