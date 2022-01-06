<template>
  <div>
    <h1>신간 서적</h1>
    <b-table striped hover :items="items"></b-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { no: 1, title: '새로운 책 1', author: '작가' },
        { no: 2, title: '새로운 책 2', author: '작가 후보' },
      ],
    };
  },
};
</script>

<style></style>
