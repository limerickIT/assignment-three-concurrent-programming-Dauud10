<script setup>
import { defineProps } from "vue"

const props = defineProps({
  id: Number,
  name: String,
  price: Number,
  thumbnail: String
})
</script>

<template>
  <div class="card">
    <img :src="thumbnail" class="thumb" alt="product image"/>

    <h3>{{ name }}</h3>
    <p>€{{ price }}</p>

    <router-link :to="`/product/${id}`" class="btn">
      View Details
    </router-link>
  </div>
</template>

<style scoped>
.card {
  border: 1px solid #444;
  padding: 14px;
  border-radius: 8px;
  background: #222;
  color: #fff;
  text-align: center;
}

.thumb {
  width: 100%;
  max-height: 150px;
  object-fit: cover;
  border-radius: 8px;
}

.btn {
  display: inline-block;
  margin-top: 10px;
  padding: 8px 12px;
  background: #4e89ff;
  color: white;
  text-decoration: none;
  border-radius: 6px;
}
</style>
