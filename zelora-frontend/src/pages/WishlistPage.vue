<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";

const CURRENT_CUSTOMER_ID = 1; // same dummy user

const wishlist = ref([]);
const loading = ref(true);

onMounted(async () => {
  try {
    const res = await axios.get(
        `http://localhost:8080/api/wishlist/customer/${CURRENT_CUSTOMER_ID}`
    );

    wishlist.value = res.data.map((p) => ({
      id: p.id,
      name: p.name,
      price: p.displayPrice ?? p.price,
      thumbnail: p.featureImage
          ? `/src/assets/products/${p.featureImage}`
          : "/src/assets/ZeloraAwaitingProductImage.png",
    }));
  } catch (e) {
    console.error("Wishlist fetch failed", e);
  }

  loading.value = false;
});
</script>

<template>
  <section class="wish-shell page-shell">
    <h1 class="title">My Wishlist</h1>

    <p v-if="loading" class="muted">Loading wishlist...</p>

    <p v-if="!loading && wishlist.length === 0" class="muted">
      Your wishlist is empty!
    </p>

    <div class="grid">
      <ProductCard
          v-for="item in wishlist"
          :key="item.id"
          :id="item.id"
          :name="item.name"
          :price="item.price"
          :thumbnail="item.thumbnail"
          show-heart="false"
      />
    </div>
  </section>
</template>

<style scoped>
.wish-shell {
  max-width: 900px;
  margin: 0 auto;
}

.title {
  font-size: 2rem;
  margin-bottom: 20px;
  color: #fff;
  text-align: center;
}

.muted {
  text-align: center;
  color: #bbb;
  margin-bottom: 20px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 18px;
}
</style>
