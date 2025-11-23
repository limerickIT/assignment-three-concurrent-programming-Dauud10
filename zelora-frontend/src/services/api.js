import axios from 'axios'

/*
  Axios instance.
*/
const api = axios.create({
    baseURL: "http://localhost:8080", // placeholder for backend
})

export function searchProducts(filters) {
    /*
      filters will come from SearchPage:
      {
        name,
        category,
        priceMin,
        priceMax,
        keywords,
        recent
      }
    */

    console.log("Search request sent:", filters)
}


export default api
