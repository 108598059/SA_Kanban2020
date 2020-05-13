<template>
  <div id="modal-create-card">
    <b-button
      id="btn-create-card"
      variant="outline-primary"
      v-b-modal.modal-create-card
    >Create Card</b-button>

    <b-modal id="modal-create-card" title="Create Card" @ok="onSubmit" v-model="modalShow">
      <b-form>
        <b-form-group id="input-group-card-title" label="Card Title:" label-for="input-1">
          <b-form-input
            id="input-card-title"
            v-model="form.title"
            type="text"
            required
            placeholder="Enter title"
          ></b-form-input>
        </b-form-group>
      </b-form>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      modalShow: false,
      form: {
        title: ""
      }
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      // TODO send to back-end.
      console.log(JSON.stringify(this.form));
      this.modalShow = false;
    },
  }
};
</script>

<style scoped>
#modal-create-card {
  width: 200px;
  float: right;
}
</style>