<template>
  <div id="column">
    <div class="column_name">{{name}}</div>
    <div class="column_content">
      <Card card-name="Develop"></Card>
    </div>
  </div>
</template>

<script>
import Card from "./Card";

export default {
  name: "Column",
  components: {
    Card
  },
  props: {
    name: String
  }
};
</script>

<style scoped>
#column {
  margin: 5px;
  width: 300px;
  border-radius: 10px;
  border: 1px #494949 solid;
}

.column_title {
  height: 35px;
  font-size: 20px;
  text-align: center;
  border-bottom: 1px #504f4f solid;
}

.column_content {
  padding: 5px;
}
</style>
