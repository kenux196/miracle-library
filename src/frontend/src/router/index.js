import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import Books from '@/views/Books.vue';
import BookManagement from '@/views/BookManagement.vue';
import MyInfo from '@/views/MyInfo.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/book',
    name: 'Books',
    component: Books,
  },
  {
    path: '/book-manage',
    name: 'BookManagement',
    component: BookManagement,
  },
  {
    path: '/myInfo',
    name: 'MyInfo',
    component: MyInfo,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
