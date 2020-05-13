<template>
<div>
  <vk-navbar id="boardNav">
    <vk-navbar-nav slot="left">
      <vk-button type="primary" id="navHomeButton"><img src="https://i.imgur.com/lohmYuW.png"></vk-button>
    </vk-navbar-nav>

    <vk-navbar-logo id="nvaLogo" slot="center">Kanban</vk-navbar-logo>

    <vk-navbar-nav slot="right">
      <vk-button type="primary" id="navAvatarButton"><img src="https://i.imgur.com/lojmoSO.png"></vk-button>
    </vk-navbar-nav>

  </vk-navbar>
</div>

</template>

<script>

export default {
  data(){
    return{
      show: false,
      messages: []
    }
  },
  methods:{
    createBoard: function(){
      this.show = false;
      this.messages.push({ message: 'Success', status: 'success' });
    }
  }
}
</script>>

<style scoped>
  #navHomeButton{
    background-color: transparent;
  }

  #navAvatarButton{
    background-color: transparent;
  }

  #boardNav{
    background-color: #23a8fa !important;
    height: 80px;
  }

  #nvaLogo{
    color: white;
    font-size: 40px;
    font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  }

</style>