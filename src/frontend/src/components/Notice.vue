<template>
  <div class="news">
    <h1>도서관 소식</h1>
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
          label: '번호',
        },
        {
          key: 'title',
          label: '제목',
        },
        {
          key: 'date',
          label: '등록일',
        },
      ],
      items: [
        { no: 1, title: '공지사항 1', date: '2022-01-09 20:00:00' },
        { no: 2, title: '공지사항 2', date: '2022-01-09 20:01:00' },
        { no: 3, title: '개관 준비 중', date: '2022-01-09 20:01:00' },
      ],
    };
  },
};
</script>

<style></style>
