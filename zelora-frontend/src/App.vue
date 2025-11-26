<script setup>
import { RouterView, RouterLink } from "vue-router";
</script>

<template>
  <div>
    <!-- 🌿 Navbar -->
    <nav class="navbar">
      <RouterLink to="/" class="nav-item">Home</RouterLink>
      <RouterLink to="/search" class="nav-item">Products</RouterLink>
      <RouterLink to="/recent" class="nav-item">Recently Viewed</RouterLink>
    </nav>

    <!-- Page content -->
    <main>
      <RouterView />
    </main>
  </div>
</template>

<style>
.navbar {
  display: flex;
  gap: 20px;
  padding: 15px;
  background: #111;
}

.nav-item {
  color: #ddd;
  text-decoration: none;
  font-size: 1.1rem;
}

.nav-item:hover {
  color: white;
}

main {
  padding: 20px;
}
</style>
