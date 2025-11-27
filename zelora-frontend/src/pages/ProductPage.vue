<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import {
  addRecentlyViewed,
  getRecentlyViewed,
} from "../services/recentlyViewed.js";
import ProductCard from "../components/ProductCard.vue";

const route = useRoute();
const product = ref(null);
const recently = ref([]);
const recommended = ref([]);
const isInWishlist = ref(false);

const CURRENT_CUSTOMER_ID = 1; // dummy user

onMounted(async () => {
  const id = route.params.id;

  // 1) product detail
  const res = await axios.get(
      `http://localhost:8080/api/products/${id}/detail`
  );

  const dto = res.data;

  const imagePath = dto.featureImage
      ? new URL(`../assets/products/${dto.featureImage}`, import.meta.url).href
      : new URL(
          "../assets/products/ZeloraAwaitingProductImage.png",
          import.meta.url
      ).href;

  product.value = {
    id: dto.productId,
    name: dto.productName,
    description: dto.description,
    price: dto.price,
    discountedPrice: dto.discountedPrice,
    displayPrice: dto.displayPrice,
    categoryName: dto.categoryName,
    averageRating: dto.averageRating,
    featureImage: dto.featureImage,
    imagePath,
    stockStatus: dto.stockStatus,
    stockMessage: dto.stockMessage,
    availableQuantity: dto.availableQuantity,
    reviews: dto.reviews || [],
  };

  // 2) recently viewed (localStorage)
  addRecentlyViewed({
    id: product.value.id,
    name: product.value.name,
    image: product.value.imagePath,
    price: product.value.displayPrice ?? product.value.price,
  });
  recently.value = getRecentlyViewed();

  // 3) recommended products (safe if endpoint missing)
  try {
    const recRes = await axios.get(
        `http://localhost:8080/api/products/${id}/recommendations`
    );
    recommended.value = recRes.data.map((p) => {
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
    console.warn("Recommendations endpoint not available (yet).");
  }

  // 4) wishlist state
  try {
    const wishRes = await axios.get(
        `http://localhost:8080/api/wishlist/customer/${CURRENT_CUSTOMER_ID}/contains/${id}`
    );
    isInWishlist.value = wishRes.data === true;
  } catch (e) {
    console.warn("Wishlist check failed:", e);
  }
});

async function toggleWishlist() {
  if (!product.value) return;
  const id = route.params.id;
  const base = `http://localhost:8080/api/wishlist/customer/${CURRENT_CUSTOMER_ID}`;

  try {
    if (isInWishlist.value) {
      await axios.delete(`${base}/remove/${id}`);
      isInWishlist.value = false;
    } else {
      await axios.post(`${base}/add/${id}`);
      isInWishlist.value = true;
    }
  } catch (e) {
    console.error("Wishlist toggle failed", e);
  }
}
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

        <p class="stock" :class="product.stockStatus.toLowerCase()">
          {{ product.stockMessage }}
        </p>

        <p class="rating">
          <strong>Rating:</strong>
          <span v-if="product.averageRating > 0">
            ⭐ {{ product.averageRating.toFixed(1) }} / 5
          </span>
          <span v-else>No ratings yet</span>
        </p>

        <button class="wishlist-btn" @click="toggleWishlist">
          <span v-if="isInWishlist">♥ In wishlist</span>
          <span v-else>♡ Add to wishlist</span>
        </button>

        <p class="desc">
          {{ product.description }}
        </p>
      </div>
    </div>

    <!-- Reviews -->
    <section class="reviews" v-if="product.reviews && product.reviews.length">
      <h2>Customer reviews</h2>

      <div v-for="r in product.reviews" :key="r.reviewDate" class="review-card">
        <p class="stars">⭐ {{ r.rating }}/5</p>
        <p class="comment">{{ r.comment }}</p>
        <p class="meta">— {{ r.customerFirstName }}, {{ r.customerCity }}</p>
        <p class="date">{{ new Date(r.reviewDate).toLocaleDateString() }}</p>
      </div>
    </section>

    <!-- Recommended -->
    <section class="recommended" v-if="recommended.length">
      <h2>Recommended for you</h2>
      <div class="card-row">
        <ProductCard
            v-for="p in recommended"
            :key="p.id"
            :id="p.id"
            :name="p.name"
            :price="p.price"
            :thumbnail="p.thumbnail"
        />
      </div>
    </section>

    <!-- Recently viewed -->
    <section class="recent-strip" v-if="recently.length > 1">
      <h2>Recently viewed</h2>
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

  <p v-else class="loading">Loading…</p>
</template>

<style scoped>
.detail-shell {
  color: #111;
}

.top {
  display: grid;
  grid-template-columns: minmax(0, 2.4fr) minmax(0, 3.2fr);
  gap: 26px;
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
  padding: 16px;
  box-shadow: 0 18px 40px rgba(0, 0, 0, 0.7);
}

.main-img {
  width: 100%;
  border-radius: 12px;
  object-fit: cover;
  max-height: 420px;
}

.info-col {
  background: #f8f8f8;
  border-radius: 18px;
  padding: 18px 20px;
  box-shadow: 0 18px 40px rgba(0, 0, 0, 0.6);
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
  margin-bottom: 6px;
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
  margin-bottom: 8px;
}

.desc {
  margin-top: 10px;
  color: #444;
}

/* wishlist button */
.wishlist-btn {
  margin-top: 4px;
  padding: 8px 16px;
  border-radius: 999px;
  border: 1px solid #444;
  background: #111;
  color: #fff;
  font-weight: 600;
  cursor: pointer;
  font-size: 0.85rem;
}

.wishlist-btn span {
  letter-spacing: 0.06em;
}

/* Reviews */
.reviews {
  margin-top: 28px;
}

.reviews h2 {
  margin-bottom: 10px;
  color: #fff;
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

/* Recommended */
.recommended {
  margin-top: 34px;
}

.recommended h2 {
  margin-bottom: 12px;
  color: #fff;
}

.card-row {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(190px, 1fr));
  gap: 16px;
}

/* Recently viewed */
.recent-strip {
  margin-top: 30px;
}

.recent-strip h2 {
  margin-bottom: 8px;
  color: #fff;
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
  color: #fff;
}
</style>
