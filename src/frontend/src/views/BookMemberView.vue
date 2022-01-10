<template>
  <div class="books">
    <b-dd-divider></b-dd-divider>
    <div class="book-list">
      <book-list></book-list>
    </div>
    <div></div>
  </div>
</template>

<script>
// @ is an alias to /src
import BookList from '@/components/BookList.vue';

export default {
  name: 'BookSearch',
  components: {
    BookList,
  },
};
</script>
<style scoped></style>
