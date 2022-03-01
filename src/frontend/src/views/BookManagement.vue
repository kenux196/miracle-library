<template>
  <div class="book-management">
    <book-add-form></book-add-form>
    <b-dd-divider></b-dd-divider>
    <div class="book-list">
      <book-search></book-search>
      <book-list-manage></book-list-manage>
    </div>
    <div></div>
  </div>
</template>

<script>
// @ is an alias to /src
import BookAddForm from '../components/BookAddForm.vue';
import BookListManage from '../components/BookListManage.vue';
import BookSearch from '../components/BookSearch.vue';

export default {
  name: 'Home',
  components: {
    BookAddForm,
    BookListManage,
    BookSearch,
  },
};
</script>
<style></style>
