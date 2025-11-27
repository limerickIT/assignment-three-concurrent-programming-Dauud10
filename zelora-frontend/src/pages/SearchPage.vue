<script setup>
import { ref, onMounted, watch, nextTick } from "vue";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";

import $ from "jquery";
import "datatables.net-dt";
import "datatables.net-dt/css/dataTables.dataTables.css";

// filters
const nameSearch = ref("");
const categorySearch = ref("");
const priceMin = ref("");
const priceMax = ref("");
const keywordSearch = ref("");
const recentOnly = ref(false);

// data
const results = ref([]);
const loading = ref(false);
const categories = ref([]);

// datatables
const tableRef = ref(null);
let dataTable = null;

onMounted(async () => {
  try {
    const res = await axios.get("http://localhost:8080/api/categories");
    categories.value = res.data;
    console.log("Categories loaded:", res.data);
  } catch (e) {
    console.error("Failed to load categories", e);
  }
});

async function searchProductsHandler() {
  loading.value = true;

  const params = {
    name: nameSearch.value || null,
    categoryName: categorySearch.value || null,
    minPrice: priceMin.value || null,
    maxPrice: priceMax.value || null,
    keyword: keywordSearch.value || null,
    recentOnly: recentOnly.value,
  };

  try {
    const res = await axios.get("http://localhost:8080/api/products/search", {
      params,
    });

    results.value = res.data.map((p) => {
      const price =
          p.discounted_price && p.discounted_price < p.price
              ? p.discounted_price
              : p.price;

      const img = p.feature_image
          ? new URL(`../assets/products/${p.feature_image}`, import.meta.url).href
          : new URL(
              "../assets/products/ZeloraAwaitingProductImage.png",
              import.meta.url
          ).href;

      return {
        id: p.product_id,
        name: p.product_name,
        price,
        thumbnail: img,
        category: p.category_name,
        release: p.release_date,
      };
    });


  } catch (err) {
    console.error("Search failed:", err);
    results.value = [];
  }

  loading.value = false;
}

// rebuild DataTables when results change
watch(results, async (val) => {
  await nextTick();
  if (!tableRef.value) return;

  if (dataTable) {
    dataTable.destroy();
    dataTable = null;
  }

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
  <section class="search-shell page-shell">
    <header class="search-header">
      <h1>Search products</h1>
      <p>Filter by name, category, price, keywords and recency.</p>
    </header>

    <!-- filters card -->
    <div class="filters-card">
      <input
          v-model="nameSearch"
          type="text"
          placeholder="Search by product name..."
      />

      <!-- FIXED DROPDOWN (camelCase fields) -->
      <select v-model="categorySearch">
        <option value="">All categories</option>

        <option
            v-for="c in categories"
            :key="c.categoryId"
            :value="c.categoryName"
        >
          {{ c.categoryName }}
        </option>
      </select>

      <div class="price-row">
        <input v-model="priceMin" type="number" placeholder="Min price"/>
        <input v-model="priceMax" type="number" placeholder="Max price"/>
      </div>

      <input
          v-model="keywordSearch"
          type="text"
          placeholder="Keywords (material, colour, eco...)"
      />

      <label class="recent-check">
        <input type="checkbox" v-model="recentOnly"/>
        <span>Recently added (last 7 days)</span>
      </label>

      <button @click="searchProductsHandler" :disabled="loading">
        {{ loading ? "Searching..." : "Search products" }}
      </button>
    </div>

    <!-- results -->
    <section class="results">
      <h2>Results</h2>

      <p v-if="!loading && results.length === 0" class="muted">
        No results yet – try searching!
      </p>

      <table
          v-if="results.length > 0"
          ref="tableRef"
          class="display results-table"
      >
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
          <td>
            <img :src="p.thumbnail" class="table-thumb"/>
          </td>
          <td>{{ p.name }}</td>
          <td>€{{ p.price.toFixed(2) }}</td>
          <td>{{ p.category }}</td>
          <td>{{ p.release }}</td>
        </tr>
        </tbody>
      </table>

      <!-- cards grid underneath -->
      <div class="grid" v-if="results.length > 0">
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
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.search-header {
  text-align: center;
  margin-bottom: 22px;
}

.search-header h1 {
  font-size: 2rem;
  color: #050505;
}

.search-header p {
  color: #050505;
  font-size: 0.95rem;
}

/* filters */
.filters-card {
  background: #f7f7f7;
  border-radius: 18px;
  padding: 18px;
  box-shadow: 0 18px 45px rgba(0, 0, 0, 0.6);
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 26px;
}

.filters-card input,
.filters-card select {
  padding: 11px 12px;
  border-radius: 12px;
  border: 1px solid #ddd;
  background: #ffffff;
  color: #111;
  font-size: 0.95rem;
}

.filters-card input::placeholder {
  color: #999;
}

.price-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px;
}

.recent-check {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.9rem;
  color: #555;
}

.filters-card button {
  margin-top: 4px;
  background: #2c6dff;
  color: #fff;
  border: none;
  padding: 11px;
  border-radius: 999px;
  font-weight: 600;
  cursor: pointer;
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.filters-card button:hover {
  background: #2254c5;
}

/* results */
.results h2 {
  font-size: 1.4rem;
  margin-bottom: 10px;
  color: #050505;
}

.muted {
  color: #a0a0a0;
  text-align: center;
}

/* DataTables overrides */
.results-table :deep(th),
.results-table :deep(td) {
  padding: 8px 10px;
  color: #f5f5f5;
}

.results-table :deep(th) {
  background: #222 !important;
}

.results-table :deep(tr) {
  background: #141414 !important;
}

.table-thumb {
  width: 55px;
  height: 55px;
  border-radius: 8px;
  object-fit: cover;
}

/* cards grid */
.grid {
  margin-top: 18px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
  gap: 16px;
}
</style>
