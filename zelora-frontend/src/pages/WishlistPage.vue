<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";

const CURRENT_CUSTOMER_ID = 1;
const items = ref([]);
const loading = ref(false);

onMounted(loadWishlist);

async function loadWishlist() {
  loading.value = true;
  try {
    const res = await axios.get(
        `http://localhost:8080/api/wishlist/customer/${CURRENT_CUSTOMER_ID}`
    );

    items.value = res.data.map((p) => {
      const price =
          p.discountedPrice && p.discountedPrice < p.price
              ? p.discountedPrice
              : p.price;

      const thumb = p.featureImage
          ? new URL(`../assets/products/${p.featureImage}`, import.meta.url).href
          : new URL(
              "../assets/products/ZeloraAwaitingProductImage.png",
              import.meta.url
          ).href;

      return {
        id: p.id,
        name: p.name,
        price,
        thumbnail: thumb,
      };
    });
  } catch (e) {
    console.error("Failed to load wishlist", e);
    items.value = [];
  }
  loading.value = false;
}
</script>

<template>
  <section class="page-shell wish-shell">
    <div class="wish-card">
      <header class="wish-header">
        <h1>My wishlist</h1>
        <p>Save products to come back later.</p>
      </header>

      <p v-if="!loading && items.length === 0" class="empty">
        Your wishlist is empty.
      </p>

      <div class="grid" v-if="items.length > 0">
        <ProductCard
            v-for="p in items"
            :key="p.id"
            :id="p.id"
            :name="p.name"
            :price="p.price"
            :thumbnail="p.thumbnail"
        />
      </div>
    </div>
  </section>
</template>

<style scoped>
.wish-shell {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.wish-card {
  background: #f8f8f8;
  border-radius: 24px;
  padding: 22px 24px;
  box-shadow: 0 20px 55px rgba(0, 0, 0, 0.7);
  min-height: 380px;
}

.wish-header h1 {
  font-size: 1.6rem;
  margin-bottom: 4px;
}

.wish-header p {
  color: #555;
}

.empty {
  margin-top: 40px;
  text-align: center;
  color: #777;
}

.grid {
  margin-top: 18px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
  gap: 16px;
}
</style>
