<template>
  <div class="w-70 p-3">
    <h1>도서 목록</h1>
    <b-table striped hover :items="items" :fields="fields"></b-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: [
        {
          key: 'id',
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
          key: 'registerDate',
          label: '등록일',
        },
      ],
      items: [
        { id: 1, title: '책 1', author: '작가', registerDate: '2021-10-10' },
        { id: 2, title: '책 2', author: '작가 후보', registerDate: '2021-10-11' },
      ],
    };
  },
};
</script>

<style></style>
