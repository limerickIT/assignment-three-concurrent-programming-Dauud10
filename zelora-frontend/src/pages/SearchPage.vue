<script setup>
import { ref } from 'vue'
import ProductCard from '../components/ProductCard.vue'
import { searchProducts } from '../services/api.js'

// Form fields
const nameSearch = ref("")
const categorySearch = ref("")
const priceMin = ref("")
const priceMax = ref("")
const keywordSearch = ref("")
const recentOnly = ref(false)

// Results
const results = ref([])

// Called when user presses the button
function searchProductsHandler() {
  const filters = {
    name: nameSearch.value,
    category: categorySearch.value,
    priceMin: priceMin.value,
    priceMax: priceMax.value,
    keywords: keywordSearch.value,
    recent: recentOnly.value
  }

  console.log("Search filters:", filters)

  // temporary fake result
  results.value = [
    {
      id: 1,
      name: "Sample Product",
      price: 29.99,
      thumbnail: "/src/assets/no-image.png"
    }
  ]
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

      <button @click="searchProductsHandler">Search Products</button>
    </div>

    <!-- Results -->
    <div class="results">
      <h2>Results</h2>

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
</template>

<style scoped>
.search-page {
  max-width: 600px;
  margin: auto;
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
}

.results {
  margin-top: 25px;
}
</style>
