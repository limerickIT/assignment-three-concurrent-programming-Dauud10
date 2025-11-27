<script setup>
import { RouterLink } from "vue-router";
</script>

<template>
  <section class="home-shell">

    <!-- ============================
         HERO SECTION
    ============================= -->
    <div class="hero">
      <div class="hero-left">

        <p class="sub">Sustainable Streetwear</p>

        <h1 class="title">
          Dress sharp.<br />
          <span class="accent">Leave a lighter footprint.</span>
        </h1>

        <p class="desc">
          Explore eco-friendly fits from hoodies to footwear — all pulled live
          from the Zelora database through your Spring Boot API.
        </p>

        <div class="hero-actions">
          <RouterLink to="/search" class="btn-primary">Browse Products</RouterLink>
          <RouterLink to="/recent" class="btn-secondary">Recently Viewed</RouterLink>
        </div>

        <p class="powered">
          Powered by Spring Boot · Vue · Live Search · Drill-down product insights
        </p>
      </div>

      <div class="hero-right">
        <div class="art-box">
          <div class="circle small"></div>
          <div class="circle large"></div>

          <div class="tags">
            Eco fabrics · Fair suppliers · Clean silhouettes
          </div>
        </div>
      </div>
    </div>

    <!-- ============================
         BRAND SECTION (new)
    ============================= -->
    <section class="brand-section">
      <img
          src="/src/assets/ZeloraLogo.png"
          alt="Zelora Brand"
          class="brand-image"
      />

      <div class="brand-text">
        <p>
          Founded in 2025, Zelora is a sustainable online clothing company based in Ireland
          and has quickly made a name for itself in the world of sustainable fashion.
        </p>

        <p>
          Located amidst the picturesque surroundings of the TUS Moylish Campus,
          Zelora’s founders aim to create a brand that celebrates Irish craftsmanship
          while promoting a sustainable way of life.
        </p>

        <p>
          Zelora’s target market is eco-conscious, style-driven consumers who value
          sustainable materials, ethical production, and longer-lasting wardrobe pieces.
        </p>
      </div>
    </section>

  </section>

  <footer class="footer">
    Zelora · Sustainable Streetwear
  </footer>
</template>

<style scoped>
/* PAGE WRAPPER */
.home-shell {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 16px 70px;
  color: #fff;
}

/* =========================
   HERO SECTION
=========================== */
.hero {
  background: linear-gradient(135deg, #e9e9e9, #d0d0d0);
  border-radius: 24px;
  padding: 40px 50px;
  display: grid;
  grid-template-columns: 1.6fr 1.4fr;
  gap: 40px;
  box-shadow: 0 8px 40px rgba(255, 255, 255, 0.07);
}

@media (max-width: 900px) {
  .hero {
    grid-template-columns: 1fr;
    text-align: center;
  }
}

.sub {
  text-transform: uppercase;
  font-size: 0.75rem;
  letter-spacing: 0.2em;
  color: #666;
  margin-bottom: 14px;
}

.title {
  font-size: 2.4rem;
  font-weight: 800;
  margin-bottom: 10px;
  color: #111;
}

.accent {
  color: #356bff;
}

.desc {
  font-size: 1.05rem;
  color: #444;
  margin-bottom: 18px;
  max-width: 500px;
}

.hero-actions {
  display: flex;
  gap: 15px;
  margin-bottom: 16px;
}

@media (max-width: 900px) {
  .hero-actions {
    justify-content: center;
  }
}

.btn-primary {
  background: #3273ff;
  padding: 12px 22px;
  border-radius: 30px;
  color: white;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(0, 80, 200, 0.3);
}

.btn-secondary {
  background: white;
  padding: 12px 22px;
  border-radius: 30px;
  color: #111;
  font-weight: 600;
}

.powered {
  color: #555;
  font-size: 0.85rem;
}

/* Right-hand hero artwork */
.art-box {
  background: linear-gradient(145deg, #dcdcdc, #bcbcbc);
  border-radius: 18px;
  height: 230px;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.circle {
  position: absolute;
  border-radius: 50%;
}

.circle.small {
  width: 60px;
  height: 60px;
  background: #222;
  left: 40px;
}

.circle.large {
  width: 140px;
  height: 140px;
  background: #000;
  right: 40px;
}

.tags {
  position: absolute;
  bottom: 20px;
  padding: 6px 12px;
  background: rgba(0, 0, 0, 0.6);
  border-radius: 20px;
  font-size: 0.75rem;
}

/* =========================
   BRAND SECTION
=========================== */
.brand-section {
  margin-top: 60px;
  background: linear-gradient(180deg, #ffffff, #e8e8e8);
  color: #000;
  border-radius: 24px;
  padding: 40px;
  text-align: center;
  box-shadow: 0 8px 40px rgba(255, 255, 255, 0.06);
}

.brand-image {
  max-width: 420px;
  margin-bottom: 30px;
  border-radius: 12px;
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.15);
}

.brand-section p {
  font-size: 1.05rem;
  margin-bottom: 14px;
  color: #333;
  max-width: 900px;
  margin-left: auto;
  margin-right: auto;
}

.footer {
  margin-top: 60px;
  text-align: center;
  color: #ddd;
  font-size: 0.9rem;
}
</style>
