import { createApp } from 'vue'
import App from './App.vue'

// Router
import router from './router/index.js'

// Global CSS
import './style.css'

/*
  Creates the Vue app and registers the router.
  This makes <router-link> and <router-view> work.
*/
createApp(App)
    .use(router)
    .mount('#app')
