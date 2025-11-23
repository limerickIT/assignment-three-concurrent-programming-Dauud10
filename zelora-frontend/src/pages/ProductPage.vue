<script setup>
import { useRoute } from 'vue-router'
import { addRecentlyViewed } from "../services/recentlyViewed.js";

onMounted(async () => {
  const id = route.params.id;
  const res = await axios.get(`/api/products/${id}/detail`);
  product.value = res.data;

  // Add to recently viewed storage
  addRecentlyViewed({
    id: res.data.id,
    name: res.data.name,
    image: res.data.featureImage,
    price: res.data.displayPrice,
  });
});

const route = useRoute()
const productId = route.params.id
</script>

<template>
  <div>
    <h1>Product #{{ productId }}</h1>

    <p>Full product details will go here.</p>
  </div>
</template>
