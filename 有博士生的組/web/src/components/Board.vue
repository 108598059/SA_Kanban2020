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
      id: "c86d3bfb-5f0e-4830-b75d-c56c96c15a8b"
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
      axios.get(process.env.VUE_APP_API_HOST + "/columns?boardID=" + this.id)
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
