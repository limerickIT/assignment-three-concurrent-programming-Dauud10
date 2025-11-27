<script setup>
import { ref, onMounted, watch, nextTick } from "vue";
import axios from "axios";
import ProductCard from "../components/ProductCard.vue";

// datatables
import $ from "jquery";
import "datatables.net-dt";
import "datatables.net-dt/css/jquery.dataTables.css";

const nameSearch = ref("");
const categorySearch = ref("");
const priceMin = ref("");
const priceMax = ref("");
const keywordSearch = ref("");
const recentOnly = ref(false);

const results = ref([]);
const loading = ref(false);
const categories = ref([]);

const tableRef = ref(null);
let dataTable = null;

// helper to load images
function imageUrl(filename) {
  return new URL(`/src/assets/products/${filename}`, import.meta.url).href;
}

// load categories
onMounted(async () => {
  const res = await axios.get("http://localhost:8080/api/categories");
  categories.value = res.data;
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
    const res = await axios.get("http://localhost:8080/api/products/search", {
      params,
    });

    results.value = res.data.map((p) => ({
      id: p.id,
      name: p.name,
      price: p.displayPrice ?? p.price,
      thumbnail: p.featureImage
          ? imageUrl(p.featureImage)
          : new URL(`/src/assets/no-image.png`, import.meta.url).href,
      category: p.categoryName,
      release: p.releaseDate,
    }));
  } catch (e) {
    console.error("Search failed:", e);
    results.value = [];
  }

  loading.value = false;
}

// rebuild DataTables after results load
watch(results, async () => {
  await nextTick();

  if (!tableRef.value) return;

  if (dataTable) {
    dataTable.destroy();
    dataTable = null;
  }

  if (results.value.length > 0) {
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
    <header class="search-header">
      <h1>Zelora Product Search</h1>
      <p>Find products using powerful filtering and DataTables.</p>
    </header>

    <!-- FILTERS -->
    <div class="filters">
      <input v-model="nameSearch" type="text" placeholder="Search by product name..."/>

      <select v-model="categorySearch">
        <option value="">All Categories</option>
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
          placeholder="Keywords (e.g. cotton, leather, black...)"
      />

      <label class="recent-check">
        <input type="checkbox" v-model="recentOnly"/>
        Recently added (last 7 days)
      </label>

      <button @click="searchProductsHandler" :disabled="loading">
        {{ loading ? "Searching..." : "Search Products" }}
      </button>
    </div>

    <!-- RESULTS -->
    <section class="results">
      <h2>Results</h2>

      <p v-if="!loading && results.length === 0" class="muted">No results yet — try searching!</p>

      <!-- DATATABLES -->
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
        <tr v-for="r in results" :key="r.id">
          <td><img class="table-thumb" :src="r.thumbnail"/></td>
          <td>{{ r.name }}</td>
          <td>€{{ r.price }}</td>
          <td>{{ r.category }}</td>
          <td>{{ r.release }}</td>
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
/* identical to your last cleaned version — ensures polished UI */
.search-shell {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

/* HEADER */
.search-header {
  text-align: center;
  margin-bottom: 25px;
}

.search-header h1 {
  font-size: 2.2rem;
  font-weight: 700;
  color: #fff;
}

.search-header p {
  color: #ccc;
}

/* FILTERS */
.filters {
  background: #111;
  padding: 20px;
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
  background: #1c1c1c;
  color: white;
  border: none;
}

button {
  padding: 12px;
  border-radius: 12px;
  background: white;
  font-weight: 600;
  cursor: pointer;
}

button:hover {
  background: #ddd;
}

/* DATATABLES */
.results-table img {
  width: 55px;
  height: 55px;
  border-radius: 8px;
}

/* GRID CARDS */
.grid {
  margin-top: 24px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
}
</style>
