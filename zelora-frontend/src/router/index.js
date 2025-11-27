import { createRouter, createWebHistory } from 'vue-router'

// Import pages
import HomePage from '../pages/HomePage.vue'
import SearchPage from '../pages/SearchPage.vue'
import ProductPage from '../pages/ProductPage.vue'
import RecentlyViewedPage from "../pages/RecentlyViewedPage.vue";
import WishlistPage from "../pages/WishlistPage.vue";


/* Basic router setup.*/
const routes = [
    { path: '/', name: 'home', component: HomePage },
    { path: '/search', name: 'search', component: SearchPage },
    { path: '/product/:id', name: 'product', component: ProductPage },
    { path: "/recent", component: RecentlyViewedPage },
    { path: "/wishlist", name: "wishlist", component: WishlistPage }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
