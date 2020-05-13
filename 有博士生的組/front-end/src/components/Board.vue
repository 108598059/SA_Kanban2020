<template>
  <div id="board" class="d-flex flex-row flex-grow-1">
    <Column v-for="(column, idx) in columnList"
      :key="idx"
      :column="column"
      />
    <CreateCardModal :boardID="id" @cardCreated="cardCreated"></CreateCardModal>
  </div>
</template>

<script>
import axios from "axios";

import Column from "./Column";
import CreateCardModal from "./CreateCardModal";

export default {
  name: "Board",
  components: { Column, CreateCardModal },
  data: function() {
    return {
      columnList: [],
      id: "7793dd07-74ea-423a-9e01-3bff3f32d79f"
    };
  },
  created() {
    this.updateView();
  },
  methods: {
    updateView() {
      while(this.columnList.length) {
        this.columnList.pop();
      }
      axios.get(process.env.VUE_APP_HOST + "/columns?boardID=" + this.id)
        .then(res => {
          const columnList = res.data.columnList;
          columnList.forEach(column => {
            this.columnList.push(column);
          });
        })
    },
    cardCreated() {
      this.updateView();
    }
  }
};
</script>

<style scoped>
#board {
  border: 1px blue dashed;
}
</style>
