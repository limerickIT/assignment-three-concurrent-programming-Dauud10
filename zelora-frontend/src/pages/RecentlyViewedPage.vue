<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getRecentlyViewed } from "../services/recentlyViewed.js";

const items = ref([]);
const router = useRouter();

onMounted(() => {
  items.value = getRecentlyViewed();
});
</script>

<template>
  <section class="page-shell recent-shell">
    <h1>Recently Viewed</h1>
    <p class="sub">
      The last items you opened are kept here on this device.
    </p>

    <p v-if="items.length === 0" class="empty">
      You haven’t viewed any products yet.
    </p>

    <div v-else class="grid">
      <article
          v-for="item in items"
          :key="item.id"
          class="card"
          @click="router.push(`/product/${item.id}`)"
      >
        <img :src="item.image" alt="" />
        <p class="name">{{ item.name }}</p>
        <p class="price">€{{ item.price }}</p>
      </article>
    </div>
  </section>
</template>

<style scoped>
.recent-shell {
  max-width: 820px;
  margin: 0 auto;
}

.sub {
  color: #555;
  margin-bottom: 16px;
}

.empty {
  color: #777;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 18px;
}

.card {
  cursor: pointer;
  background: #111;
  color: #f5f5f5;
  border-radius: 12px;
  padding: 10px;
  text-align: center;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.55);
}

.card img {
  width: 100%;
  border-radius: 8px;
  margin-bottom: 6px;
}

.name {
  font-weight: 600;
  font-size: 0.95rem;
}

.price {
  color: #4ea1ff;
  font-weight: 600;
}
</style>
