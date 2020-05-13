<template>
<div class="board-view">
    <div id="createBoardButton">
        <vk-button type="primary" id="navImageButton" @click="show = true"><img src="https://i.imgur.com/T50ORBG.png"></vk-button>
    </div>
    <div id="grid">
        <vk-grid gutter="medium" class="uk-child-width-1-5@m uk-text-center">
            <div  v-for="(board, i) in boards" :key = "i" >
                <vk-card id="boardCard" padding="large" hover>
                <vk-card-title id="cardTitle">{{ board.boardTitle }}</vk-card-title>
                </vk-card>
            </div>
        </vk-grid>
    </div>
    

      <vk-modal center :show.sync="show">
        <div class="container">
            <form>
                <fieldset class="uk-fieldset">
                    <vk-modal-close @click="show = false"></vk-modal-close>
                    <legend id="modalLegend" class="uk-legend">Create Board</legend>

                    <div class="uk-margin">
                        <input id="modalBoardTitle" class="uk-input" type="text" placeholder="Board Title" v-model="boardTitle">
                    </div>

                    <div class="uk-margin">
                        <textarea class="uk-textarea" rows="5" placeholder="Board Description" v-model="boardDescription"></textarea>
                    </div>
                    <p v-vk-margin>
                        <vk-button type="primary" id="modalCreateButton" @click="createBoard">Create</vk-button>
                    </p>
                </fieldset>
            </form>
        </div>
    </vk-modal>
  <vk-notification status="primary" :messages.sync="messages"></vk-notification>
</div>

</template>

<script>
export default {
    data(){
        return{
            show: false,
            messages: [],
            boards: [],
            boardTitle: "",
            boardDescription: ""
        }
    },
    methods:{
        clearModalForm: function(){
            this.boardTitle = "";
            this.boardDescription = "";
            this.show = false;

        },
        createBoard: function(){
            this.axios.post('http://140.124.183.93:8080/baord',{
                boardTitle: this.boardTitle,
                boardDescription: this.boardDescription
            }).then(function (response) {
                this.boards.push({boardTitle: response.boardTitle})
                this.messages.push({ message: 'Success', status: 'success' });
            })
            .catch(function (error) {
                console.log(error);
                this.messages.push({ message: 'Fail', status: 'Danger' });
            });

            // this.messages.push({ message: 'Success', status: 'success' });
            // this.boards.push({boardTitle: this.boardTitle})
            this.clearModalForm();
        }
    }
}
</script>

<style scoped>
    .board-view{
        padding-top: 20px;
        padding-left: 300px;
        padding-right: 60px;
    }

    #grid{
        padding-top: 20px;
    }

    #boardCard{
        background-color: rgba(45, 88, 227, 0.7);
    }

    #cardTitle{
        color: white;
        font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif, bold;
    }

    #createBoardButton{
        padding-left: 95%;
    }

    #modalLegend{
        font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif, bold;
    }

    #modalCreateButton{
        color: white;
        font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
</style>