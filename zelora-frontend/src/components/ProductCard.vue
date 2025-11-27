<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const props = defineProps({
  id: Number,
  name: String,
  price: Number,
  thumbnail: String,
  showHeart: { type: Boolean, default: true }
});

const CURRENT_CUSTOMER_ID = 1;

const inWishlist = ref(false);

// Load wishlist state
onMounted(async () => {
  if (!props.showHeart) return;

  const res = await axios.get(
      `http://localhost:8080/api/wishlist/customer/${CURRENT_CUSTOMER_ID}/contains/${props.id}`
  );
  inWishlist.value = res.data === true;
});

// Toggle wishlist
async function toggleWishlist() {
  const base = `http://localhost:8080/api/wishlist/customer/${CURRENT_CUSTOMER_ID}`;

  if (inWishlist.value) {
    await axios.delete(`${base}/remove/${props.id}`);
    inWishlist.value = false;
  } else {
    await axios.post(`${base}/add/${props.id}`);
    inWishlist.value = true;
  }
}
</script>

<template>
  <article class="card">
    <div class="thumb-wrap">
      <img :src="thumbnail" class="thumb" alt="product image" />
    </div>

    <!-- Heart Button -->
    <button
        v-if="showHeart"
        class="heart"
        @click.stop="toggleWishlist"
        :class="{ active: inWishlist }"
    >
      ♥
    </button>

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
  position: relative;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.65);
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.thumb-wrap {
  border-radius: 10px;
  overflow: hidden;
  background: #222;
  margin-bottom: 4px;
  aspect-ratio: 4 / 3;
}

.thumb {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Heart button */
.heart {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(255,255,255,0.15);
  border: none;
  border-radius: 50%;
  width: 34px;
  height: 34px;
  color: #fff;
  font-size: 1.2rem;
  cursor: pointer;
  transition: 0.2s ease;
}

.heart:hover {
  background: rgba(255,255,255,0.25);
}

.heart.active {
  background: #ff2d55;
  color: white;
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
}
</style>
