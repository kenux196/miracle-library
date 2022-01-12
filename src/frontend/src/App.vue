<template>
  <div id="app">
    <b-container>
      <b-row>
        <b-col cols="2">
          <b-nav vertical class="w-70 p-3">
            <b-navbar-brand to="/">홈</b-navbar-brand>
            <b-navbar toggleable>
              <b-navbar-brand to="/book">도서</b-navbar-brand>
              <b-navbar-toggle target="navbar-toggle-collapse">
                <template #default="{ expanded }">
                  <b-icon v-if="expanded" icon="chevron-bar-up"></b-icon>
                  <b-icon v-else icon="chevron-bar-down"></b-icon>
                </template>
              </b-navbar-toggle>

              <b-collapse id="navbar-toggle-collapse" is-nav>
                <b-navbar-nav class="ml-auto">
                  <b-nav-item to="/book">도서 검색</b-nav-item>
                  <b-nav-item to="/book/manage">도서 관리</b-nav-item>
                </b-navbar-nav>
              </b-collapse>
            </b-navbar>
            <b-navbar-brand to="/myInfo">내 정보</b-navbar-brand>
            <b-navbar-brand to="/about">About</b-navbar-brand>
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
