import { createRouter, createWebHistory } from "vue-router";

// I will import the ProductSearch page later
import ProductSearch from "../pages/ProductSearch.vue";

const routes = [
    { path: "/", component: ProductSearch },
    { path: "/search", component: ProductSearch },
];

export default createRouter({
    history: createWebHistory(),
    routes,
});
