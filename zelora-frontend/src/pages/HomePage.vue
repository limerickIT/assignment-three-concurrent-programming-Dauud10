<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";
import { getRecentlyViewed } from "../services/recentlyViewed.js";

const router = useRouter();

const featured = ref([]);
const recent = ref([]);

function imgUrl(featureImage) {
  if (!featureImage) {
    return new URL(`/src/assets/no-image.png`, import.meta.url).href;
  }
  return new URL(`/src/assets/products/${featureImage}`, import.meta.url).href;
}

onMounted(async () => {
  // Recently viewed from localStorage
  recent.value = getRecentlyViewed();

  // Featured = recently added products
  try {
    const res = await axios.get("http://localhost:8080/api/products/search", {
      params: { recentOnly: true },
    });

    featured.value = res.data.slice(0, 8).map((p) => ({
      id: p.id,
      name: p.name,
      price: p.displayPrice ?? p.price,
      thumbnail: imgUrl(p.featureImage),
    }));
  } catch (err) {
    console.error("Failed to load featured products", err);
  }
});

function goSearch() {
  router.push({ name: "search" });
}

function goRecent() {
  router.push({ path: "/recent" });
}
</script>

<template>
  <section class="page-shell home-shell">
    <!-- HERO -->
    <section class="hero">
      <div class="hero-text">
        <p class="eyebrow">SUSTAINABLE STREETWEAR</p>
        <h1>
          Dress sharp.<br />
          <span>Leave a lighter footprint.</span>
        </h1>
        <p class="sub">
          Explore eco-friendly fits from hoodies to footwear – all pulled live from the
          Zelora database.
        </p>

        <div class="hero-actions">
          <button class="btn primary" @click="goSearch">Browse Products</button>
          <button class="btn ghost" @click="goRecent">Recently Viewed</button>
        </div>

        <p class="hero-meta">
          Powered by Spring Boot + Vue · Live search · Drill-down product insights
        </p>
      </div>

      <div class="hero-card">
        <div class="bubble main"></div>
        <div class="bubble small"></div>
        <div class="bubble tiny"></div>
        <p class="hero-tag">Eco fabrics · Fair suppliers · Clean silhouettes</p>
      </div>
    </section>

    <!-- FEATURED / NEW -->
    <section class="section" v-if="featured.length">
      <div class="section-header">
        <h2>New this week</h2>
        <p>Fresh drops released in the last seven days.</p>
      </div>

      <div class="grid">
        <ProductCard
            v-for="p in featured"
            :key="p.id"
            :id="p.id"
            :name="p.name"
            :price="p.price"
            :thumbnail="p.thumbnail"
        />
      </div>
    </section>

    <!-- RECENTLY VIEWED PREVIEW -->
    <section class="section" v-if="recent.length">
      <div class="section-header">
        <h2>Pick up where you left off</h2>
        <p>Quick links to items you recently checked out.</p>
      </div>

      <div class="recent-strip">
        <div
            class="recent-card"
            v-for="item in recent.slice(0, 6)"
            :key="item.id"
        >
          <img :src="item.image" alt="" />
          <p class="name">{{ item.name }}</p>
          <p class="price">€{{ item.price }}</p>
          <router-link :to="`/product/${item.id}`" class="mini-link">
            View
          </router-link>
        </div>
      </div>
    </section>
  </section>
</template>

<style scoped>
.home-shell {
  width: 100%;
}

/* HERO */
.hero {
  display: grid;
  grid-template-columns: minmax(0, 3fr) minmax(0, 2.4fr);
  gap: 32px;
  align-items: center;
  margin-bottom: 36px;
}

@media (max-width: 900px) {
  .hero {
    grid-template-columns: 1fr;
  }
}

.hero-text h1 {
  font-size: 2.6rem;
  line-height: 1.1;
  margin-bottom: 10px;
  color: #ffffff;
}

.hero-text h1 span {
  color: #8ab4ff;
}

.eyebrow {
  letter-spacing: 0.18em;
  text-transform: uppercase;
  font-size: 0.75rem;
  color: #bbbbbb;
  margin-bottom: 6px;
}

.sub {
  color: #d0d0d0;
  max-width: 480px;
  margin-bottom: 16px;
  font-size: 0.98rem;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 10px;
}

.btn {
  padding: 10px 18px;
  border-radius: 999px;
  border: none;
  cursor: pointer;
  font-weight: 600;
  font-size: 0.95rem;
}

.btn.primary {
  background: #3b82f6;
  color: #fff;
  box-shadow: 0 10px 30px rgba(59, 130, 246, 0.35);
}

.btn.primary:hover {
  background: #2563eb;
}

.btn.ghost {
  background: transparent;
  color: #f5f5f5;
  border: 1px solid rgba(255, 255, 255, 0.25);
}

.btn.ghost:hover {
  background: rgba(255, 255, 255, 0.06);
}

.hero-meta {
  color: #888;
  font-size: 0.85rem;
}

/* Hero visual card */
.hero-card {
  position: relative;
  background: radial-gradient(circle at 0 0, #ffffff, #888888);
  border-radius: 24px;
  padding: 24px;
  min-height: 220px;
  overflow: hidden;
  display: flex;
  align-items: flex-end;
  justify-content: flex-start;
}

.hero-tag {
  background: rgba(0, 0, 0, 0.72);
  color: #f5f5f5;
  padding: 8px 12px;
  border-radius: 999px;
  font-size: 0.8rem;
}

.bubble {
  position: absolute;
  border-radius: 50%;
  filter: blur(0.5px);
}

.bubble.main {
  width: 170px;
  height: 170px;
  background: #000000;
  top: 18px;
  right: 24px;
}

.bubble.small {
  width: 70px;
  height: 70px;
  background: #18181b;
  top: 40px;
  left: 20px;
}

.bubble.tiny {
  width: 40px;
  height: 40px;
  background: #111827;
  bottom: 30px;
  right: 40px;
}

/* GENERIC SECTION */
.section {
  margin-top: 26px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 12px;
}

.section-header h2 {
  font-size: 1.4rem;
  color: #ffffff;
}

.section-header p {
  color: #a0a0a0;
  font-size: 0.9rem;
}

/* GRID FOR FEATURED PRODUCTS */
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
  gap: 18px;
}

/* RECENT STRIP */
.recent-strip {
  display: flex;
  gap: 14px;
  overflow-x: auto;
  padding-bottom: 4px;
}

.recent-card {
  min-width: 160px;
  background: #111;
  border-radius: 14px;
  padding: 10px;
  color: #f5f5f5;
  text-align: center;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.6);
}

.recent-card img {
  width: 100%;
  border-radius: 8px;
  margin-bottom: 6px;
  object-fit: cover;
}

.recent-card .name {
  font-size: 0.88rem;
  font-weight: 600;
}

.recent-card .price {
  font-size: 0.9rem;
  font-weight: 600;
  margin-bottom: 4px;
}

.mini-link {
  font-size: 0.78rem;
  text-decoration: underline;
}
</style>
