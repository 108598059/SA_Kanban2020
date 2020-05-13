<template>
  <div id="modal-create-card">
    <b-button
      id="btn-create-card"
      variant="outline-primary"
      v-b-modal.modal-create-card
    >Create Card</b-button>

    <b-modal id="modal-create-card" title="Create Card" @ok="onSubmit" v-model="modalShow">
      <b-form>
        <b-form-group id="input-group-card-name" label="Card Name:" label-for="input-1">
          <b-form-input
            id="input-card-name"
            v-model="form.cardName"
            type="text"
            required
            placeholder="Enter name"
          ></b-form-input>
        </b-form-group>
      </b-form>
    </b-modal>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      modalShow: false,
      form: {
        cardName: ""
      }
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      const body = {
        boardID: String,
        cardName: String
      }
      axios.post(process.env.VUE_APP_HOST + '/card/create', body).then(res => {
        console.log(res);
        this.closeModal();
        this.$emit('cardCreated');
      }).catch(console.error);
    },
    closeModal() {
      this.modalShow = false;
    }
  }
};
</script>

<style scoped>
#modal-create-card {
  width: 200px;
  float: right;
}
</style>
