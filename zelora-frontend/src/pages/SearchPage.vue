<script setup>
/*
  Basic SearchPage, NOT connected to backend yet.
*/

import { ref } from 'vue'

const searchText = ref("")
</script>

<template>
  <div>
    <h1>Search Products</h1>

    <input
        v-model="searchText"
        type="text"
        placeholder="Search by name..."
    />

    <p>Search text: {{ searchText }}</p>
  </div>
</template>
