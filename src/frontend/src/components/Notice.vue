<template>
  <div>
    <h1>도서관 소식</h1>
    <b-table striped hover :items="items"></b-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { no: 1, title: '공지 사항 테스트 1', date: '2022-01-09 20:00:00' },
        { no: 2, title: '공지 사항 테스트 2', date: '2022-01-09 20:01:00' },
      ],
    };
  },
};
</script>

<style></style>
