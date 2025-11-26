<script setup>
import { ref, onMounted } from "vue";
import { getRecentlyViewed } from "../services/recentlyViewed.js";

const items = ref([]);

onMounted(() => {
  items.value = getRecentlyViewed();
});
</script>

<template>
  <div class="page">
    <h1>Recently Viewed</h1>

    <p v-if="items.length === 0">No recently viewed products yet.</p>

    <div v-else class="recent-grid">
      <div
          v-for="item in items"
          :key="item.id"
          class="recent-card"
          @click="$router.push(`/product/${item.id}`)"
      >
        <img :src="item.image" />
        <p class="name">{{ item.name }}</p>
        <p class="price">€{{ item.price }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page {
  padding: 20px;
}
.recent-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 20px;
}
.recent-card {
  cursor: pointer;
  text-align: center;
  background: #222;
  padding: 10px;
  border-radius: 8px;
}
.recent-card img {
  width: 100%;
  border-radius: 8px;
}
.name {
  font-weight: 600;
  color: white;
}
.price {
  color: #4ea1ff;
}
</style>
