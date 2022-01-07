<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <b-container class="bv-example-row">
      <b-row>
        <b-col>1 of 3</b-col>
        <b-col>2 of 3</b-col>
        <b-col>3 of 3</b-col>
      </b-row>
    </b-container>
    <div class="notice center-block">
      <notice></notice>
    </div>
    <div class="new-book">
      <new-books></new-books>
      <rent-top-books></rent-top-books>
    </div>
    <div></div>
  </div>
</template>

<script>
// @ is an alias to /src
import Notice from '@/components/Notice.vue';
import NewBooks from '@/components/NewBooks.vue';
import RentTopBooks from '@/components/RentTopBooks.vue';

export default {
  name: 'Home',
  components: {
    Notice,
    NewBooks,
    RentTopBooks,
  },
};
</script>
<style scoped></style>
