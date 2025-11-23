const KEY = "recently_viewed_products";

export function addRecentlyViewed(product) {
    let items = JSON.parse(localStorage.getItem(KEY)) || [];

    // remove existing entry so it doesn’t duplicate
    items = items.filter(p => p.id !== product.id);

    // add newest to the front
    items.unshift(product);

    // limit to 10
    if (items.length > 10) {
        items = items.slice(0, 10);
    }

    localStorage.setItem(KEY, JSON.stringify(items));
}

export function getRecentlyViewed() {
    return JSON.parse(localStorage.getItem(KEY)) || [];
}
