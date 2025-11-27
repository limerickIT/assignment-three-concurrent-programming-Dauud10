<script setup>
const props = defineProps({
  id: Number,
  name: String,
  price: Number,
  thumbnail: String,
});
</script>

<template>
  <article class="card">
    <div class="thumb-wrap">
      <img :src="thumbnail" class="thumb" alt="product image" />
    </div>

    <h3 class="name">{{ name }}</h3>
    <p class="price">€{{ price.toFixed(2) }}</p>

    <router-link :to="`/product/${id}`" class="btn">
      View Details
    </router-link>
  </article>
</template>

<style scoped>
.card {
  background: #111;
  color: #f5f5f5;
  border-radius: 14px;
  padding: 12px 12px 16px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.65);
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 8px;
}

.thumb-wrap {
  border-radius: 10px;
  overflow: hidden;
  background: radial-gradient(circle at top, #ffffff, #999999);
  margin-bottom: 4px;
  aspect-ratio: 4 / 3;
  display: flex;
  align-items: center;
  justify-content: center;
}

.thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.name {
  font-size: 0.95rem;
  font-weight: 600;
}

.price {
  font-weight: 700;
  font-size: 0.95rem;
  margin-bottom: 4px;
}

.btn {
  margin-top: 4px;
  text-align: center;
  padding: 8px 10px;
  border-radius: 999px;
  background: #ffffff;
  color: #111;
  font-size: 0.85rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.btn:hover {
  background: #e3e3e3;
}
</style>
