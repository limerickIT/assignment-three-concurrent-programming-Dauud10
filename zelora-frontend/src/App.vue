<script setup>
import { RouterView, RouterLink, useRoute } from "vue-router";

const route = useRoute();
</script>

<template>
  <div class="app-shell">
    <!-- Header / Navbar -->
    <header class="nav-wrapper">
      <nav class="navbar">
        <div class="brand">
          <span class="logo-dot" />
          <span class="logo-text">ZELORA</span>
        </div>

        <div class="nav-links">
          <RouterLink to="/" :class="{ active: route.name === 'home' }">
            Home
          </RouterLink>
          <RouterLink to="/search" :class="{ active: route.name === 'search' }">
            Search
          </RouterLink>
          <RouterLink to="/recent" :class="{ active: route.name === 'recent' }">
            Recently Viewed
          </RouterLink>
        </div>
      </nav>
    </header>

    <!-- Page content -->
    <main class="main-area">
      <RouterView />
    </main>

    <!-- Footer -->
    <footer class="footer">
      <p class="footer-title">Zelora · Sustainable Streetwear</p>
      <p class="footer-sub">
        Built with Spring Boot &amp; Vue · Demo assignment project
      </p>
    </footer>
  </div>
</template>

<style scoped>
.app-shell {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* NAVBAR */

.nav-wrapper {
  margin-bottom: 24px;
}

.navbar {
  background: rgba(0, 0, 0, 0.92);
  border-radius: 999px;
  padding: 10px 22px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5);
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
  letter-spacing: 0.18em;
  font-weight: 700;
  font-size: 0.85rem;
  text-transform: uppercase;
  color: #f5f5f5;
}

.logo-dot {
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ffffff, #bbbbbb);
  box-shadow: 0 0 10px rgba(255, 255, 255, 0.7);
}

.logo-text {
  font-size: 0.85rem;
}

.nav-links {
  display: flex;
  gap: 22px;
}

.nav-links a {
  position: relative;
  font-size: 0.95rem;
  font-weight: 500;
  color: #e5e5e5;
  padding-bottom: 4px;
}

.nav-links a::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  height: 2px;
  width: 0;
  background: #ffffff;
  transition: width 0.18s ease;
}

.nav-links a:hover::after {
  width: 100%;
}

.nav-links a.active {
  color: #ffffff;
}

.nav-links a.active::after {
  width: 100%;
}

/* MAIN CONTENT */

.main-area {
  flex: 1;
  display: flex;
}

/* FOOTER */

.footer {
  margin-top: 30px;
  padding-top: 12px;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
  text-align: center;
  color: var(--text-muted);
  font-size: 0.85rem;
}

.footer-title {
  font-weight: 600;
  margin-bottom: 4px;
  color: #f5f5f5;
}

.footer-sub {
  margin: 0;
}
</style>
