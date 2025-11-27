<script setup>
import { ref } from "vue";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";

const nameSearch = ref("");
const categorySearch = ref("");
const priceMin = ref("");
const priceMax = ref("");
const keywordSearch = ref("");
const recentOnly = ref(false);
const results = ref([]);
const loading = ref(false);

async function searchProductsHandler() {
  loading.value = true;

  const params = {
    name: nameSearch.value || null,
    category: categorySearch.value || null,
    minPrice: priceMin.value || null,
    maxPrice: priceMax.value || null,
    keywords: keywordSearch.value || null,
    recentOnly: recentOnly.value,
  };

  try {
    const res = await axios.get("http://localhost:8080/api/products/search", {
      params,
    });

    results.value = res.data.map((p) => ({
      id: p.id,
      name: p.name,
      price: p.displayPrice ?? p.price,
      thumbnail: p.featureImage
          ? `/src/assets/${p.featureImage}`
          : "/src/assets/no-image.png",
    }));
  } catch (err) {
    console.error("Search failed:", err);
    results.value = [];
  }

  loading.value = false;
}
</script>

<template>
  <section class="page-shell search-shell">
    <header class="search-header">
      <h1>Search Products</h1>
      <p>Filter by name, category, price, sustainability keywords and recency.</p>
    </header>

    <div class="filters">
      <input
          v-model="nameSearch"
          type="text"
          placeholder="Search by product name..."
      />
      <input
          v-model="categorySearch"
          type="text"
          placeholder="Category name..."
      />

      <div class="price-row">
        <input v-model="priceMin" type="number" placeholder="Min price" />
        <input v-model="priceMax" type="number" placeholder="Max price" />
      </div>

      <input
          v-model="keywordSearch"
          type="text"
          placeholder="Keywords (material, colour, eco...)"
      />

      <label class="recent-check">
        <input type="checkbox" v-model="recentOnly" />
        <span>Recently added (last 7 days)</span>
      </label>

      <button @click="searchProductsHandler" :disabled="loading">
        {{ loading ? "Searching..." : "Search Products" }}
      </button>
    </div>

    <section class="results">
      <h2>Results</h2>

      <p v-if="!loading && results.length === 0" class="muted">
        No results yet. Try searching!
      </p>

      <div class="grid">
        <ProductCard
            v-for="p in results"
            :key="p.id"
            :id="p.id"
            :name="p.name"
            :price="p.price"
            :thumbnail="p.thumbnail"
        />
      </div>
    </section>
  </section>
</template>

<style scoped>
.search-shell {
  max-width: 760px;
  margin: 0 auto;
}

.search-header {
  text-align: center;
  margin-bottom: 20px;
}

.search-header h1 {
  font-size: 2rem;
  margin-bottom: 6px;
}

.search-header p {
  color: #555;
  font-size: 0.95rem;
}

.filters {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 24px;
}

.price-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px;
}

.recent-check {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-size: 0.9rem;
  color: #555;
}

.recent-check input {
  width: 16px;
  height: 16px;
  border-radius: 4px;
}

/* Results */

.results h2 {
  font-size: 1.3rem;
  margin-bottom: 8px;
}

.muted {
  color: #777;
  font-size: 0.9rem;
}

.grid {
  margin-top: 14px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
  gap: 16px;
}
</style>
