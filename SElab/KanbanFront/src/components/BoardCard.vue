<template>
<div class="board-view">
    <div id="uk-child-width-expand@s">
        <vk-grid>
            <div id="navTitle" class="uk-width-1-2@m uk-text-left" >
                <span>Boards</span>
            </div>
            <div id="createBoardButton" class="uk-width-1-2@m uk-text-right">
                <vk-button type="primary" id="navImageButton" @click="show = true"><img src="https://i.imgur.com/T50ORBG.png"></vk-button>
            </div>
        </vk-grid>
    </div>
    
    <div id="grid">
        <vk-grid gutter="large" class="uk-child-width-1-5@m uk-text-center">
            <div  v-for="(board, i) in boards" :key = "i" >
                <vk-card id="boardCard" padding="small" hover>
                    <div slot="header">
                        <vk-card-title id="cardTitle">{{ subString(board.boardTitle) }}</vk-card-title>
                    </div>
                    <!-- <div>
                        <p>{{ board.boardDescription }}</p>
                    </div> -->
                    <div slot="footer">
                        <vk-button-link type="text" href="#">more</vk-button-link>
                    </div>
                
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
    created (){
        this.axios.get('http://140.124.183.93:8080/board')
        .then(data => data)
        .then(({data}) => {
            console.log(data);
            this.boards = data;
        })
    },
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
        subString: function(string){
            if (string == null) return "";
            if (string.length > 5)
                string = string.substr(0, 5) + "..."
                
            return string;
        },
        clearModalForm: function(){
            this.boardTitle = "";
            this.boardDescription = "";
            this.show = false;

        },
        createBoard: function(){
            this.axios.post('http://140.124.183.93:8080/board',{
                boardTitle: this.boardTitle,
                boardDescription: this.boardDescription
            }).then(data => data)
            .then(({data}) => {
                console.log(data);
                this.boards.push({boardTitle: data.boardTitle, boardId: data.boardId, boardDescription: data.boardDescription});
                console.log(this.boards);
                this.messages.push({ message: 'Create Board Success', status: 'success' });
            })
            .catch(data => {
                this.messages.push({ message: 'Create Board Fail', status: 'danger' });
                console.log(data);
            })
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
        background-color: rgba(145, 160, 255, 0.3);
        /* background-color: rgba(45, 88, 227, 0.7); */
    }

    #cardTitle{
        color: black;
        font-family:'Microsoft JhengHei', bold;
        font-weight:bold;
    }

    #navTitle{
        font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif, bold;
        font-size: 36px;
    }

    #createBoardButton{
    }

    #modalLegend{
        font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif, bold;
    }

    #modalCreateButton{
        color: white;
        font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    a {
        text-decoration: none !important;
    }

    #navImageButton{
        margin-top: 8px;
    }

    .uk-card-default .uk-card-header {
        border-bottom: 1px solid #ffffff;
    }

    .uk-card-default .uk-card-footer {
        border-top: 1px solid #ffffff;
    }
</style>