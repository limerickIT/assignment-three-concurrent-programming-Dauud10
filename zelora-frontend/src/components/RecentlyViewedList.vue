<template>
  <div v-if="items.length" class="recent-container">
    <h2>Recently Viewed</h2>

    <div class="recent-grid">
      <div
          v-for="item in items"
          :key="item.id"
          class="recent-card"
          @click="$router.push(`/product/${item.id}`)"
      >
        <img :src="item.image" alt="" />
        <p class="name">{{ item.name }}</p>
        <p class="price">€{{ item.price }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { getRecentlyViewed } from "../services/recentlyViewed";

export default {
  name: "RecentlyViewedList",
  data() {
    return {
      items: []
    };
  },
  mounted() {
    this.items = getRecentlyViewed();
  }
};
</script>

<style>
.recent-container {
  margin-top: 40px;
}
.recent-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  gap: 15px;
}
.recent-card {
  cursor: pointer;
  text-align: center;
}
.recent-card img {
  width: 100%;
  border-radius: 10px;
}
.name {
  font-weight: 600;
}
.price {
  color: red;
}
</style>
