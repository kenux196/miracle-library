<template>
  <div>
    <h1>신간 서적</h1>
    <b-table striped hover :items="items" :fields="fields"></b-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: [
        {
          key: 'no',
          label: '도서번호',
        },
        {
          key: 'title',
          label: '제목',
        },
        {
          key: 'author',
          label: '작가',
        },
        {
          key: 'rentable',
          label: '대여가능여부',
        },
      ],
      items: [
        { no: 1, title: '새로운 책 1', author: '작가', rentable: '가능' },
        { no: 2, title: '새로운 책 2', author: '작가 후보', rentable: '가능' },
      ],
    };
  },
};
</script>

<style></style>
