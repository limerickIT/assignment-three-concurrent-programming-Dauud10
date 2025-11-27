<script setup>
import axios from "axios";
import {ref, onMounted} from "vue";
import {useRoute} from "vue-router";
import ProductCard from "../components/ProductCard.vue";
import {addRecentlyViewed, getRecentlyViewed} from "../services/recentlyViewed.js";

// Dummy customer ID
const CUSTOMER_ID = 1;

const route = useRoute();
const product = ref(null);
const recently = ref([]);
const recommended = ref([]);
const isInWishlist = ref(false);

// Universal image loader
function imageUrl(filename) {
  return new URL(`/src/assets/products/${filename}`, import.meta.url).href;
}

onMounted(async () => {
  const productId = route.params.id;

  // 1) Load product details
  const res = await axios.get(`http://localhost:8080/api/products/${productId}/detail`);
  product.value = res.data;

  // 2) Fix image path
  if (product.value.featureImage) {
    product.value.imagePath = imageUrl(product.value.featureImage);
  } else {
    product.value.imagePath = new URL(`/src/assets/ZeloraAwaitingProductImage.png`, import.meta.url).href;
  }

  // 3) Save as recently viewed
  addRecentlyViewed({
    id: product.value.productId,
    name: product.value.productName,
    price: product.value.displayPrice,
    image: product.value.imagePath
  });
  recently.value = getRecentlyViewed();

  // 4) Recommended products
  const recRes = await axios.get(`http://localhost:8080/api/products/${productId}/recommendations`);
  recommended.value = recRes.data.map((p) => ({
    id: p.id,
    name: p.name,
    price: p.discountedPrice && p.discountedPrice < p.price ? p.discountedPrice : p.price,
    thumbnail: p.featureImage ? imageUrl(p.featureImage) : new URL(`/src/assets/no-image.png`, import.meta.url).href,
  }));

  // 5) Wishlist status
  const wishStatus = await axios.get(
      `http://localhost:8080/api/wishlist/customer/${CUSTOMER_ID}/contains/${productId}`
  );
  isInWishlist.value = wishStatus.data === true;
});

// Wishlist toggle
async function toggleWishlist() {
  const pid = route.params.id;
  const baseUrl = `http://localhost:8080/api/wishlist/customer/${CUSTOMER_ID}`;

  if (isInWishlist.value) {
    await axios.delete(`${baseUrl}/remove/${pid}`);
    isInWishlist.value = false;
  } else {
    await axios.post(`${baseUrl}/add/${pid}`);
    isInWishlist.value = true;
  }
}
</script>

<template>
  <section v-if="product" class="page-shell detail-shell">

    <!-- TOP SECTION -->
    <div class="top">
      <div class="image-col">
        <img :src="product.imagePath" class="main-img"/>
      </div>

      <div class="info-col">
        <p class="category">{{ product.categoryName }}</p>
        <h1>{{ product.productName }}</h1>

        <!-- PRICE -->
        <div class="price-row">
          <span v-if="product.discountedPrice" class="old">€{{ product.price }}</span>
          <span class="new">€{{ product.displayPrice }}</span>
        </div>

        <!-- STOCK -->
        <p class="stock" :class="product.stockStatus.toLowerCase()">
          {{ product.stockMessage }}
        </p>

        <!-- RATING -->
        <p class="rating">
          <strong>Rating:</strong>
          <span v-if="product.averageRating > 0">
            ⭐ {{ product.averageRating.toFixed(1) }} / 5
          </span>
          <span v-else>No ratings yet</span>
        </p>

        <!-- DESCRIPTION -->
        <p class="desc">{{ product.description }}</p>

        <!-- WISHLIST BUTTON -->
        <button class="wishlist-btn" @click="toggleWishlist">
          <span v-if="isInWishlist">❤️ Remove from Wishlist</span>
          <span v-else>🤍 Add to Wishlist</span>
        </button>
      </div>
    </div>

    <!-- REVIEWS SECTION -->
    <section class="reviews" v-if="product.reviews?.length > 0">
      <h2>Customer Reviews</h2>

      <div class="review-card" v-for="r in product.reviews" :key="r.reviewDate">
        <p class="stars">⭐ {{ r.rating }}/5</p>
        <p class="comment">{{ r.comment }}</p>
        <p class="meta">— {{ r.customerFirstName }}, {{ r.customerCity }}</p>
        <p class="date">{{ new Date(r.reviewDate).toLocaleDateString() }}</p>
      </div>
    </section>

    <!-- RECOMMENDED PRODUCTS -->
    <section class="recommended" v-if="recommended.length > 0">
      <h2>Recommended for You</h2>
      <div class="card-row">
        <ProductCard
            v-for="rec in recommended"
            :key="rec.id"
            :id="rec.id"
            :name="rec.name"
            :price="rec.price"
            :thumbnail="rec.thumbnail"
        />
      </div>
    </section>

    <!-- RECENTLY VIEWED -->
    <section class="recent-strip" v-if="recently.length > 1">
      <h2>Recently Viewed</h2>
      <div class="strip-row">
        <div
            class="strip-card"
            v-for="rv in recently.slice(0, 6)"
            :key="rv.id"
            v-if="rv.id !== product.productId"
        >
          <img :src="rv.image"/>
          <p class="name">{{ rv.name }}</p>
          <p class="price">€{{ rv.price }}</p>
          <router-link :to="`/product/${rv.id}`">View</router-link>
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

/* TOP GRID */
.top {
  display: grid;
  grid-template-columns: 46% 54%;
  gap: 32px;
}

@media (max-width: 860px) {
  .top {
    grid-template-columns: 1fr;
  }
}

/* IMAGE */
.image-col {
  background: #111;
  border-radius: 16px;
  padding: 20px;
  display: flex;
  justify-content: center;
}

.main-img {
  width: 100%;
  border-radius: 12px;
  object-fit: cover;
}

/* INFO */
.category {
  letter-spacing: 0.15em;
  font-size: 0.8rem;
  color: #777;
}

.price-row {
  margin: 14px 0;
  display: flex;
  gap: 10px;
  font-size: 1.5rem;
}

.old {
  text-decoration: line-through;
  color: #888;
}

.new {
  font-weight: 700;
}

.stock.in_stock {
  color: #0f9d32;
}

.stock.low_stock {
  color: #c77d00;
}

.stock.out_of_stock {
  color: #c32020;
}

.desc {
  margin-top: 10px;
  color: #555;
}

/* WISHLIST BUTTON */
.wishlist-btn {
  margin-top: 20px;
  padding: 10px 18px;
  background: #111;
  color: white;
  border-radius: 999px;
  border: 1px solid #444;
}

.wishlist-btn:hover {
  background: #222;
}

/* REVIEWS */
.review-card {
  background: #f3f3f3;
  border-radius: 10px;
  padding: 12px;
  margin-bottom: 12px;
}

/* RECOMMENDED */
.recommended .card-row {
  margin-top: 14px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 16px;
}

/* RECENT STRIP */
.strip-row {
  display: flex;
  gap: 14px;
  overflow-x: auto;
  padding-bottom: 4px;
}

.strip-card {
  min-width: 150px;
  background: #111;
  color: white;
  padding: 10px;
  border-radius: 12px;
  text-align: center;
}

.strip-card img {
  width: 100%;
  border-radius: 8px;
}
</style>
