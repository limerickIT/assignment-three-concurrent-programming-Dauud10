<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { addRecentlyViewed, getRecentlyViewed } from "../services/recentlyViewed.js";

const route = useRoute();
const router = useRouter();
const product = ref(null);
const recently = ref([]);

onMounted(async () => {
  const id = route.params.id;

  try {
    const res = await axios.get(
        `http://localhost:8080/api/products/${id}/detail`
    );
    product.value = res.data;

    // Build image path
    if (product.value.featureImage) {
      product.value.imagePath = `/src/assets/${product.value.featureImage}`;
    } else {
      product.value.imagePath = "/src/assets/no-image.png";
    }

    // Save to recently viewed
    addRecentlyViewed({
      id: product.value.id,
      name: product.value.name,
      image: product.value.imagePath,
      price: product.value.displayPrice,
    });

    recently.value = getRecentlyViewed();
  } catch (e) {
    console.error("Failed to load product", e);
    router.push("/search");
  }
});
</script>

<template>
  <section v-if="product" class="page-shell detail-shell">
    <div class="top">
      <div class="image-col">
        <img :src="product.imagePath" class="main-img" />
      </div>

      <div class="info-col">
        <p class="category">{{ product.categoryName }}</p>
        <h1>{{ product.name }}</h1>

        <div class="price-row">
          <span v-if="product.discountedPrice" class="old">
            €{{ product.price }}
          </span>
          <span class="new">€{{ product.displayPrice }}</span>
        </div>

        <p
            class="stock"
            :class="product.stockStatus.toLowerCase()"
        >
          {{ product.stockMessage }}
        </p>

        <p class="rating">
          <strong>Rating:</strong>
          <span v-if="product.averageRating > 0">
            ⭐ {{ product.averageRating.toFixed(1) }} / 5
          </span>
          <span v-else>No ratings yet</span>
        </p>

        <p class="desc">{{ product.description }}</p>
      </div>
    </div>

    <!-- Reviews -->
    <section class="reviews" v-if="product.reviews && product.reviews.length">
      <h2>Customer Reviews</h2>

      <div
          v-for="r in product.reviews"
          :key="r.reviewDate"
          class="review-card"
      >
        <p class="stars">⭐ {{ r.rating }}/5</p>
        <p class="comment">{{ r.comment }}</p>
        <p class="meta">— {{ r.customerFirstName }}, {{ r.customerCity }}</p>
        <p class="date">
          {{ new Date(r.reviewDate).toLocaleDateString() }}
        </p>
      </div>
    </section>

    <!-- Recently viewed strip -->
    <section class="recent-strip" v-if="recently.length > 1">
      <h2>Recently Viewed</h2>
      <div class="strip-row">
        <div
            v-for="item in recently.slice(0, 6)"
            :key="item.id"
            v-if="item.id !== product.id"
            class="strip-card"
        >
          <img :src="item.image" />
          <p class="name">{{ item.name }}</p>
          <p class="price">€{{ item.price }}</p>
          <router-link :to="`/product/${item.id}`">View</router-link>
        </div>
      </div>
    </section>
  </section>

  <p v-else class="loading">Loading...</p>
</template>

<style scoped>
.detail-shell {
  color: #111;
}

.top {
  display: grid;
  grid-template-columns: minmax(0, 2.6fr) minmax(0, 3.4fr);
  gap: 30px;
}

@media (max-width: 860px) {
  .top {
    grid-template-columns: 1fr;
  }
}

.image-col {
  background: #111;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 18px;
}

.main-img {
  width: 100%;
  border-radius: 12px;
  object-fit: cover;
  max-height: 420px;
}

.info-col h1 {
  font-size: 1.9rem;
  margin-bottom: 6px;
}

.category {
  text-transform: uppercase;
  letter-spacing: 0.18em;
  font-size: 0.75rem;
  color: #777;
}

.price-row {
  margin: 14px 0 6px;
  font-size: 1.4rem;
  display: flex;
  gap: 10px;
  align-items: baseline;
}

.old {
  text-decoration: line-through;
  color: #888;
}

.new {
  color: #111;
  font-weight: 700;
}

.stock {
  font-weight: 600;
  margin-bottom: 8px;
}

.stock.in_stock {
  color: #0b8a34;
}

.stock.low_stock {
  color: #c77d00;
}

.stock.out_of_stock {
  color: #c32222;
}

.rating {
  margin-bottom: 10px;
}

.desc {
  color: #444;
}

/* Reviews */

.reviews {
  margin-top: 30px;
}

.reviews h2 {
  margin-bottom: 12px;
}

.review-card {
  background: #f3f3f3;
  border-radius: 10px;
  padding: 12px 14px;
  margin-bottom: 10px;
}

.stars {
  color: #c89200;
  font-weight: 600;
}

.meta,
.date {
  font-size: 0.8rem;
  color: #666;
}

/* Recently viewed strip */

.recent-strip {
  margin-top: 32px;
}

.strip-row {
  display: flex;
  gap: 14px;
  overflow-x: auto;
  padding-bottom: 4px;
}

.strip-card {
  min-width: 150px;
  background: #111;
  color: #f5f5f5;
  border-radius: 12px;
  padding: 10px;
  text-align: center;
}

.strip-card img {
  width: 100%;
  border-radius: 8px;
}

.strip-card .price {
  font-weight: 600;
}
.loading {
  margin: 40px auto;
  text-align: center;
}
</style>
