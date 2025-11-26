<script setup>
/* Homepage */
</script>

<template>
  <div class="home-container">
    <h1>Zelora — Sustainable Fashion</h1>
    <p class="subtitle">Eco-friendly fashion built with style.</p>

    <router-link to="/search" class="home-btn">
      Browse Products
    </router-link>
  </div>
</template>

<style scoped>
.home-container {
  text-align: center;
  margin-top: 120px;


  background: linear-gradient(to bottom, #f2f2f2, #d9d9d9);
  padding: 80px 20px;
  border-radius: 14px;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
  box-shadow: 0 0 25px rgba(0,0,0,0.15);
}

h1 {
  color: #111;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 10px;
}

.subtitle {
  font-size: 1.1rem;
  color: #444;
  margin-bottom: 30px;
}


.home-btn {
  background: #111;
  color: white;
  padding: 12px 25px;
  border-radius: 8px;
  text-decoration: none;
  font-size: 1.1rem;
  font-weight: 600;
  transition: 0.2s;
}

.home-btn:hover {
  background: #333;
}
</style>
