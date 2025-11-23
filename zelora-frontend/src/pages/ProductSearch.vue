<template>
  <div class="container">
    <h1>Zelora Product Search</h1>

    <!-- Simple input for search -->
    <input
        v-model="name"
        placeholder="Search product name..."
        @keyup.enter="search"
    />

    <button @click="search">Search</button>

    <!-- Search results -->
    <div v-if="products.length > 0">
      <h2>Results</h2>

      <div class="grid">
        <div class="card" v-for="p in products" :key="p.productId">

          <!-- Thumbnail image -->
          <img
              :src="thumbnail(p)"
              alt="thumbnail"
          />

          <p>{{ p.productName }}</p>
        </div>
      </div>
    </div>

    <div v-if="noResults">
      <p>No products found.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      name: "",
      products: [],
      noResults: false,
    };
  },

  methods: {
    // Returns product thumbnail, or fallback image
    thumbnail(product) {
      try {
        return new URL(
            `../assets/thumbnails/${product.productId}.jpg`,
            import.meta.url
        ).href;
      } catch (e) {
        return new URL(`../assets/no-image.png`, import.meta.url).href;
      }
    },

    async search() {
      if (this.name.trim() === "") return;

      try {
        const res = await axios.get("http://localhost:8080/api/products/search", {
          params: { name: this.name }
        });

        this.products = res.data;
        this.noResults = this.products.length === 0;

      } catch (error) {
        console.error("Request error:", error);
      }
    }
  }
};
</script>

<style>
.container { padding: 20px; }
.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
}
.card {
  border: 1px solid #ccc;
  padding: 10px;
}
.card img {
  width: 100%;
}
</style>
