<template>
  <div class="book-management">
    <book-add-form></book-add-form>
    <b-dd-divider></b-dd-divider>
    <div class="book-list">
      <books></books>
    </div>
    <div></div>
  </div>
</template>

<script>
// @ is an alias to /src
import Books from '@/components/Books.vue';
import BookAddForm from '../components/BookAddForm.vue';

export default {
  name: 'Home',
  components: {
    Books,
    BookAddForm,
  },
};
</script>
<style scoped></style>
