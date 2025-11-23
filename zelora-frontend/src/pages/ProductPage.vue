<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { addRecentlyViewed } from "../services/recentlyViewed.js";

const route = useRoute();
const product = ref(null);

onMounted(async () => {
  const id = route.params.id;

  const res = await axios.get(`/api/products/${id}/detail`);
  product.value = res.data;

  // Save to recently viewed
  addRecentlyViewed({
    id: res.data.id,
    name: res.data.name,
    image: res.data.featureImage,
    price: res.data.displayPrice,
  });
});
</script>

<template>
  <div>
    <h1>Product #{{ route.params.id }}</h1>

    <p v-if="!product">Loading...</p>

    <div v-else>
      <h2>{{ product.name }}</h2>

      <img :src="product.featureImage" width="200" />

      <p><strong>Price:</strong> €{{ product.displayPrice }}</p>

      <p>{{ product.description }}</p>

      <!-- Recently viewed section will be added later -->
    </div>
  </div>
</template>
