<script setup>
import { useRoute } from 'vue-router'

/*
  Product detail page
*/

const route = useRoute()
const productId = route.params.id
</script>

<template>
  <div>
    <h1>Product #{{ productId }}</h1>

    <p>Full product details will go here.</p>
  </div>
</template>
