<template>
  <div id="board" class="d-flex flex-row flex-grow-1">
    <Column name="Develop"></Column>
    <CreateCardModal @cardCreated="cardCreated"></CreateCardModal>
  </div>
</template>

<script>
import axios from "axios";

import Column from "./Column";
import CreateCardModal from "./CreateCardModal";

export default {
  name: "Board",
  components: { Column, CreateCardModal },
  methods: {
    cardCreated() {
      axios.get(process.env.VUE_APP_HOST + "card").then(res => {
        const cards = res.data;
        console.log(cards);
        // TODO refresh by new cards.
      });
    }
  }
};
</script>

<style scoped>
#board {
  border: 1px blue dashed;
}
</style>
