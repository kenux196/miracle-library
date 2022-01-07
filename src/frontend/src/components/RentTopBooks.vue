<template>
  <div>
    <h1>인기 서적</h1>
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
      ],
      items: [
        { no: 1, title: '인기있는 책 1', author: '인기 작가' },
        { no: 2, title: '조금 인기 있는 책 2', author: '작가 지망생' },
      ],
    };
  },
};
</script>

<style></style>
