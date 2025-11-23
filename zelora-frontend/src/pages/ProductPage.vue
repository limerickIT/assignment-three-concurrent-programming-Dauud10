<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { addRecentlyViewed, getRecentlyViewed } from "../services/recentlyViewed.js";

const route = useRoute();
const product = ref(null);
const recently = ref([]);

onMounted(async () => {
  const id = route.params.id;

  // Fetch product details
  const res = await axios.get(`http://localhost:8080/api/products/${id}/detail`);
  product.value = res.data;

  // Build correct image path
  if (product.value.featureImage) {
    product.value.imagePath = `/src/assets/${product.value.featureImage}`;
  } else {
    product.value.imagePath = "/src/assets/no-image.png";
  }

  // Save to "recently viewed"
  addRecentlyViewed({
    id: product.value.id,
    name: product.value.name,
    image: product.value.imagePath,
    price: product.value.displayPrice,
  });

  recently.value = getRecentlyViewed();
});
</script>

<template>
  <div class="page">
    <h1>{{ product ? product.name : "Loading..." }}</h1>

    <p v-if="!product">Loading...</p>

    <!-- PRODUCT DETAILS -->
    <div v-else class="product-box">
      <img :src="product.imagePath" class="main-img" />

      <div class="info">
        <!-- PRICE -->
        <p class="price">
          <span v-if="product.discountedPrice" class="old">€{{ product.price }}</span>
          <span class="new">€{{ product.displayPrice }}</span>
        </p>

        <!-- STOCK STATUS -->
        <p class="stock" :class="product.stockStatus.toLowerCase()">
          {{ product.stockMessage }}
        </p>

        <!-- CATEGORY -->
        <p><strong>Category:</strong> {{ product.categoryName }}</p>

        <!-- AVG RATING -->
        <p><strong>Rating:</strong>
          <span v-if="product.averageRating > 0">
            ⭐ {{ product.averageRating.toFixed(1) }} / 5
          </span>
          <span v-else>No ratings yet</span>
        </p>

        <p class="desc">{{ product.description }}</p>
      </div>
    </div>

    <!-- REVIEWS -->
    <div class="reviews" v-if="product.reviews && product.reviews.length">
      <h2>Customer Reviews</h2>

      <div v-for="r in product.reviews" :key="r.reviewDate" class="review-card">
        <p class="stars">⭐ {{ r.rating }}/5</p>
        <p class="comment">{{ r.comment }}</p>
        <p class="meta">— {{ r.customerFirstName }}, {{ r.customerCity }}</p>
        <p class="date">{{ new Date(r.reviewDate).toLocaleDateString() }}</p>
      </div>
    </div>

    <!-- RECENTLY VIEWED -->
    <div class="recently" v-if="recently.length > 1">
      <h2>Recently Viewed</h2>

      <div class="recent-grid">
        <div
            v-for="item in recently.slice(0, 5)"
            :key="item.id"
            v-if="item.id != product.id"
            class="recent-card"
        >
          <img :src="item.image" />
          <p>{{ item.name }}</p>
          <p class="price">€{{ item.price }}</p>
          <router-link :to="`/product/${item.id}`">View</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page {
  max-width: 900px;
  margin: auto;
  color: white;
}
.product-box {
  display: flex;
  gap: 20px;
  background: #222;
  padding: 20px;
  border-radius: 8px;
}
.main-img {
  width: 280px;
  border-radius: 8px;
}
.price {
  font-size: 1.4rem;
}
.price .old {
  text-decoration: line-through;
  color: #888;
  margin-right: 8px;
}
.price .new {
  color: #4e89ff;
  font-weight: bold;
}
.stock {
  margin-top: 10px;
  font-weight: bold;
}
.stock.in_stock {
  color: #5aff70;
}
.stock.low_stock {
  color: #ffe066;
}
.stock.out_of_stock {
  color: #ff5c5c;
}
.desc {
  margin-top: 12px;
  line-height: 1.5;
}

.reviews {
  margin-top: 40px;
}
.review-card {
  background: #333;
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 12px;
}
.stars {
  font-size: 1.1rem;
  color: #ffd447;
}
.meta {
  font-size: 0.9rem;
  color: #bbb;
}
.date {
  font-size: 0.8rem;
  color: #777;
}

.recently {
  margin-top: 40px;
}
.recent-grid {
  display: flex;
  gap: 16px;
}
.recent-card {
  background: #333;
  padding: 10px;
  width: 130px;
  border-radius: 6px;
  text-align: center;
}
.recent-card img {
  width: 100%;
  border-radius: 6px;
}
.price {
  color: #4e89ff;
  font-weight: bold;
}
</style>
