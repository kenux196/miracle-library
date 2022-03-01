<template>
  <div class="w-70 p-3">
    <b-button v-b-modal.modal-footer-sm>신간 등록</b-button>
    <b-modal id="modal-footer-sm" title="신규 책 등록" button-size="sm">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="input-group-1" label="제목 :" label-for="input-1">
          <b-form-input id="input-1" v-model="form.title" placeholder="제목을 입력하세요" required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="작가 :" label-for="input-2">
          <b-form-input id="input-2" v-model="form.author" placeholder="작가명을 입력하세요" required></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="ISBN : " label-for="input-3">
          <b-form-input id="input-3" v-model="form.isbn" placeholder="ISBN을 입력하세요" required></b-form-input>
        </b-form-group>

        <b-button type="submit" variant="primary">Submit</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: 'BookAddForm',
  data() {
    return {
      form: {
        title: '',
        author: '',
        isbn: '',
      },
    };
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      alert(JSON.stringify(this.form));
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
      this.form.title = '';
      this.form.author = '';
      this.form.isbn = '';
    },
  },
};
</script>
<style scoped></style>
