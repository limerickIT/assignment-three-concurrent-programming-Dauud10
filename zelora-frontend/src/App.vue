<script setup>
import { RouterView, RouterLink } from "vue-router";
</script>

<template>
  <div>
    <nav class="navbar">
      <router-link to="/">Home</router-link>
      <router-link to="/search">Search</router-link>
      <router-link to="/recent">Recently Viewed</router-link>
    </nav>


    <!-- Page content -->
    <main>
      <RouterView />
    </main>
  </div>
</template>

<style>
.navbar {
  background: black;
  padding: 15px 25px;
  display: flex;
  gap: 20px;
}
.navbar a {
  color: white;
  font-weight: 600;
  text-decoration: none;
}
.navbar a:hover {
  color: #4ea1ff;
}


main {
  padding: 20px;
}
</style>
