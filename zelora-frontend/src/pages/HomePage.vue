<script setup>
/* Homepage hero */
</script>

<template>
  <section class="page-shell home-shell">
    <div class="home-left">
      <h1>Zelora — Sustainable Fashion</h1>
      <p class="tagline">Eco-friendly streetwear with performance styling.</p>

      <p class="sub">
        Search for products by name, category, price range, and material
        filters. View detailed sustainability status, customer reviews, and
        availability across stock.
      </p>

      <router-link to="/search" class="cta">
        Browse Products
      </router-link>
    </div>

    <div class="home-right">
      <div class="badge-row">
        <span class="badge">Recycled fabrics</span>
        <span class="badge">Low-impact dyes</span>
      </div>
      <div class="card-visual">
        <p class="cv-title">Next-Gen Essentials</p>
        <p class="cv-sub">Built for everyday, designed for the planet.</p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.home-shell {
  display: grid;
  grid-template-columns: minmax(0, 3fr) minmax(0, 2.3fr);
  gap: 32px;
  align-items: center;
}

@media (max-width: 860px) {
  .home-shell {
    grid-template-columns: 1fr;
    text-align: center;
  }

  .home-right {
    order: -1;
  }
}

.home-left h1 {
  font-size: 2.5rem;
  margin-bottom: 8px;
}

.tagline {
  font-size: 1.1rem;
  color: #333;
  margin-bottom: 18px;
}

.sub {
  color: #555;
  max-width: 460px;
  margin-bottom: 26px;
}

.cta {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 12px 28px;
  border-radius: 999px;
  background: #111;
  color: #f5f5f5;
  font-weight: 600;
  letter-spacing: 0.03em;
  text-transform: uppercase;
  font-size: 0.85rem;
}

.cta:hover {
  background: #333;
}

/* Right visual */

.home-right {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.badge-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: flex-end;
}

.badge {
  font-size: 0.75rem;
  padding: 6px 12px;
  border-radius: 999px;
  border: 1px solid #b5b5b5;
  text-transform: uppercase;
  letter-spacing: 0.14em;
  color: #444;
  background: rgba(255, 255, 255, 0.6);
}

.card-visual {
  background: radial-gradient(circle at top left, #ffffff, #c7c7c7);
  border-radius: 20px;
  padding: 40px 24px;
  text-align: right;
  box-shadow: 0 14px 40px rgba(0, 0, 0, 0.35);
}

.cv-title {
  font-weight: 700;
  font-size: 1.3rem;
  margin-bottom: 4px;
}

.cv-sub {
  color: #555;
  font-size: 0.95rem;
}
</style>
