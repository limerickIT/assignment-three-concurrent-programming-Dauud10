<script setup>
import { ref, onMounted, watch, nextTick } from "vue";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";

import $ from "jquery";
import "datatables.net-dt";
import "datatables.net-dt/css/jquery.dataTables.css";

// Search fields
const nameSearch = ref("");
const categorySearch = ref("");
const priceMin = ref("");
const priceMax = ref("");
const keywordSearch = ref("");
const recentOnly = ref(false);

// Data
const results = ref([]);
const loading = ref(false);
const categories = ref([]);

// DataTables
const tableRef = ref(null);
let dataTable = null;

// Load categories on mount
onMounted(async () => {
  try {
    const res = await axios.get("http://localhost:8080/api/categories");
    categories.value = res.data;
  } catch (err) {
    console.error("Failed to load categories", err);
  }
});

// Search handler
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
    const res = await axios.get("http://localhost:8080/api/products/search", { params });

    results.value = res.data.map((p) => ({
      id: p.id,
      name: p.name,
      price: p.displayPrice ?? p.price,
      thumbnail: p.featureImage
          ? `/src/assets/${p.featureImage}`
          : "/src/assets/no-image.png",
      category: p.categoryName,
      release: p.releaseDate,
    }));
  } catch (err) {
    console.error("Search failed:", err);
    results.value = [];
  }

  loading.value = false;
}

// Watch results → rebuild DataTables
watch(results, async (val) => {
  await nextTick();

  if (!tableRef.value) return;

  // Destroy old instance
  if (dataTable) {
    dataTable.destroy();
    dataTable = null;
  }

  // Rebuild new table
  if (val.length > 0) {
    dataTable = $(tableRef.value).DataTable({
      paging: true,
      searching: true,
      ordering: true,
      lengthChange: false,
      info: false,
      pageLength: 5,
    });
  }
});
</script>


<template>
  <section class="search-shell">
    <!-- Header -->
    <header class="search-header">
      <h1>Zelora Product Search</h1>
      <p>Find products using detailed filters powered by our database.</p>
    </header>

    <!-- Filters -->
    <div class="filters">
      <input v-model="nameSearch" type="text" placeholder="Search by product name..." />

      <select v-model="categorySearch">
        <option value="">All Categories</option>
        <option v-for="c in categories" :key="c.categoryId" :value="c.categoryName">
          {{ c.categoryName }}
        </option>
      </select>

      <div class="price-row">
        <input v-model="priceMin" type="number" placeholder="Min price" />
        <input v-model="priceMax" type="number" placeholder="Max price" />
      </div>

      <input
          v-model="keywordSearch"
          type="text"
          placeholder="Keywords (material, colour, etc.)"
      />

      <label class="recent-check">
        <input type="checkbox" v-model="recentOnly" />
        <span>Recently added (last 7 days)</span>
      </label>

      <button @click="searchProductsHandler" :disabled="loading">
        {{ loading ? "Searching..." : "Search Products" }}
      </button>
    </div>

    <!-- Results -->
    <section class="results">
      <h2>Results</h2>

      <p v-if="!loading && results.length === 0" class="muted">
        No results yet — try searching!
      </p>

      <!-- DATATABLES TABLE -->
      <table ref="tableRef" class="display results-table" v-if="results.length > 0">
        <thead>
        <tr>
          <th>Preview</th>
          <th>Name</th>
          <th>Price</th>
          <th>Category</th>
          <th>Release</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="p in results" :key="p.id">
          <td><img :src="p.thumbnail" class="table-thumb" /></td>
          <td>{{ p.name }}</td>
          <td>€{{ p.price }}</td>
          <td>{{ p.category }}</td>
          <td>{{ p.release }}</td>
        </tr>
        </tbody>
      </table>

      <!-- PRODUCT CARDS -->
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
/* PAGE LAYOUT */
.search-shell {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.search-header {
  text-align: center;
  margin-bottom: 25px;
}

.search-header h1 {
  font-size: 2.2rem;
  font-weight: 700;
  color: #ffffff;
  letter-spacing: 1px;
}

.search-header p {
  color: #cccccc;
  font-size: 0.95rem;
}

/* FILTERS */
.filters {
  background: #111;
  padding: 18px;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 28px;
  box-shadow: 0 0 25px rgba(255, 255, 255, 0.05);
}

input,
select {
  padding: 12px;
  border-radius: 12px;
  border: none;
  background: #1d1d1d;
  color: white;
  font-size: 1rem;
}

input::placeholder {
  color: #aaa;
}

button {
  background: #ffffff;
  color: black;
  padding: 12px;
  font-size: 1rem;
  font-weight: 600;
  border-radius: 12px;
  cursor: pointer;
  transition: 0.2s ease;
}

button:hover {
  background: #e5e5e5;
}

.price-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.recent-check {
  color: #ccc;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 8px;
}

/* RESULTS */
.results h2 {
  font-size: 1.4rem;
  margin-bottom: 10px;
  color: #fff;
}

.muted {
  color: #777;
  text-align: center;
}

/* DATATABLES */
.results-table :deep(th),
.results-table :deep(td) {
  padding: 10px;
  color: #fff;
}

.results-table :deep(tr) {
  background: #141414 !important;
}

.results-table :deep(th) {
  background: #222 !important;
}

.table-thumb {
  width: 55px;
  height: 55px;
  border-radius: 8px;
  object-fit: cover;
}

/* PRODUCT GRID */
.grid {
  margin-top: 20px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 18px;
}
</style>
