<template>
  <div>
    <b-input-group>
      <b-form-input v-model="keyword" placeholder="책 제목 혹은 작가 이름 입력하세요."></b-form-input>
      <b-button variant="primary" @click="onSearch">검색</b-button>
    </b-input-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keyword: '',
    };
  },
  methods: {
    onSearch(event) {
      event.preventDefault();
      if (this.keyword === '') {
        alert('입력 값이 없습니다. 검색 키워드를 입력하세요.');
      } else {
        alert('입력한 키워드는 ' + this.keyword + ' 입니다.');
      }
    },
  },
};
</script>

<style></style>
