<template>
  <div id="app">
    <b-nav pills>
      <b-nav-item to="/" active>홈</b-nav-item>
      <b-nav-item-dropdown id="my-nav-dropdown" text="도서" toggle-class="nav-link-custom" right>
        <b-dropdown-item to="/book">도서</b-dropdown-item>
        <b-dropdown-item>Two</b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item>Three</b-dropdown-item></b-nav-item-dropdown
      >
      <b-nav-item to="/myInfo">내 정보</b-nav-item>
      <b-nav-item to="/about">About</b-nav-item>
    </b-nav>
    <router-view />
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.nav {
  padding: 30px;
}
</style>
