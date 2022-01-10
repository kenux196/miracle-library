<template>
  <div class="w-70 p-3">
    <h1>도서 목록</h1>
    <book-search></book-search>
    <b-table select-mode="multi" striped hover :items="items" :fields="fields"></b-table>
    <h2>샘플</h2>
    <b-table-simple responsive>
      <b-thead>
        <b-tr>
          <b-th>도서번호</b-th>
          <b-th>제목</b-th>
          <b-th>작가</b-th>
          <b-th>대여가능</b-th>
        </b-tr>
      </b-thead>
      <b-tbody>
        <b-tr v-for="i of items" :key="i.id">
          <b-td>{{ i.id }}</b-td>
          <b-td>{{ i.title }}</b-td>
          <b-td>{{ i.author }}</b-td>
          <b-td>{{ i.rentable }}</b-td>
          <b-button variant="outline-primary">담기</b-button>
        </b-tr>
      </b-tbody>
    </b-table-simple>
  </div>
</template>

<script>
import BookSearch from './BookSearch.vue';
export default {
  components: { BookSearch },
  data() {
    return {
      keyword: '',
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
          key: 'rentable',
          label: '대여가능여부',
        },
      ],
      items: [
        { id: 1, title: '책 1', author: '작가', rentable: '가능' },
        { id: 2, title: '책 2', author: '작가 후보', rentable: '가능' },
      ],
    };
  },
};
</script>

<style></style>
