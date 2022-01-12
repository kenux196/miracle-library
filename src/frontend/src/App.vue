<template>
  <div id="app">
    <b-container>
      <b-row>
        <b-col col md="auto">
          <b-nav vertical class="w-70 p-3">
            <b-nav-item to="/">홈</b-nav-item>
            <b-nav-item-dropdown id="my-nav-dropdown" text="도서" toggle-class="nav-link-custom" right>
              <b-dropdown-item to="/book">도서</b-dropdown-item>
              <b-dropdown-item to="/book-manage">책 관리</b-dropdown-item>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item>Three</b-dropdown-item></b-nav-item-dropdown
            >
            <b-nav-item to="/myInfo">내 정보</b-nav-item>
            <b-nav-item to="/about">About</b-nav-item>
          </b-nav>
        </b-col>
        <b-col>
          <sub-nav></sub-nav>
          <router-view />
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import SubNav from './components/SubNav.vue';
export default {
  components: { SubNav },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
}
</style>
