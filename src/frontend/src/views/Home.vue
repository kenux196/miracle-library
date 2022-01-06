<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <notice></notice>
    <new-books></new-books>
    <rent-top-books></rent-top-books>
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
