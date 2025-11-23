<script setup>
import { ref } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import axios from 'axios'

// Form fields
const nameSearch = ref("")
const categorySearch = ref("")
const priceMin = ref("")
const priceMax = ref("")
const keywordSearch = ref("")
const recentOnly = ref(false)

// Results
const results = ref([])
const loading = ref(false)

// Run search
async function searchProductsHandler() {
  loading.value = true;

  const params = {
    name: nameSearch.value || null,
    category: categorySearch.value || null,
    minPrice: priceMin.value || null,
    maxPrice: priceMax.value || null,
    keywords: keywordSearch.value || null,
    recentOnly: recentOnly.value
  }

  try {
    const res = await axios.get("http://localhost:8080/api/products/search", { params });

    results.value = res.data.map(p => ({
      id: p.id,
      name: p.name,
      price: p.displayPrice ?? p.price,
      thumbnail: p.featureImage
          ? `/src/assets/${p.featureImage}`
          : "/src/assets/no-image.png"
    }));

  } catch (err) {
    console.error("Search failed:", err)
  }

  loading.value = false;
}
</script>

<template>
  <div class="search-page">
    <h1>Search Products</h1>

    <div class="filters">
      <input v-model="nameSearch" type="text" placeholder="Search by product name..." />
      <input v-model="categorySearch" type="text" placeholder="Category name..." />

      <div class="price-row">
        <input v-model="priceMin" type="number" placeholder="Min price" />
        <input v-model="priceMax" type="number" placeholder="Max price" />
      </div>

      <input v-model="keywordSearch" type="text" placeholder="Keywords (material, colour...)" />

      <label>
        <input type="checkbox" v-model="recentOnly" />
        Recently added (last 7 days)
      </label>

      <button @click="searchProductsHandler" :disabled="loading">
        {{ loading ? "Searching..." : "Search Products" }}
      </button>
    </div>

    <!-- Results -->
    <div class="results">
      <h2>Results</h2>

      <p v-if="results.length === 0 && !loading">No results yet. Try searching!</p>

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
    </div>
  </div>
</template>

<style scoped>
.search-page {
  max-width: 700px;
  margin: auto;
  text-align: center;
}

.filters {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.price-row {
  display: flex;
  gap: 10px;
}

button {
  padding: 10px;
  cursor: pointer;
  background: #4e89ff;
  border: none;
  border-radius: 6px;
  color: white;
  font-weight: bold;
}

.results .grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 16px;
  margin-top: 20px;
}
</style>
