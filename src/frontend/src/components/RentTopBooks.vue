<template>
  <div>
    <h1>인기 서적</h1>
    <b-table striped hover :items="items"></b-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { no: 1, title: '인기있는 책 1', author: '인기 작가' },
        { no: 2, title: '조금 인기 있는 책 2', author: '작가 지망생' },
      ],
    };
  },
};
</script>

<style></style>
