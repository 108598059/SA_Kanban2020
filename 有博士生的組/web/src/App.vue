<template>
  <div id="app" class="d-flex flex-column">
    <Nav/>
    <Board/>
  </div>
</template>

<script>
import Nav from './components/Nav';
import Board from './components/Board';

export default {
  name: 'App',
  components: {
    Nav,
    Board
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
}

html, body {
  width: 100%;
  height: 100%;
}
</style>
