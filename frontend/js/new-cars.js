// ================================================
// new-cars.js – Browse & buy new cars
// ================================================

let allCars = [];

async function loadNewCars() {
  const grid = document.getElementById('cars-grid');
  const search = document.getElementById('search-input')?.value.toLowerCase() || '';

  grid.innerHTML = `<div class="loading-wrap" style="grid-column:1/-1">
    <div class="spinner"></div><p>Yüklənir...</p></div>`;

  try {
    allCars = await api.get('/new-cars/all-new-cars');
    renderCars(allCars, search);
  } catch (err) {
    grid.innerHTML = `<div class="empty-state" style="grid-column:1/-1">
      <div class="icon">🚗</div>
      <p>Avtomobillər yüklənərkən xəta baş verdi.</p>
    </div>`;
    showToast(err.message, 'error');
  }
}

function renderCars(cars, search = '') {
  const grid = document.getElementById('cars-grid');
  const filtered = cars.filter(c =>
    !search ||
    c.brand?.toLowerCase().includes(search) ||
    c.engine?.toLowerCase().includes(search) ||
    c.color?.toLowerCase().includes(search)
  );

  if (!filtered.length) {
    grid.innerHTML = `<div class="empty-state" style="grid-column:1/-1">
      <div class="icon">🔍</div>
      <p>Heç bir avtomobil tapılmadı.</p>
    </div>`;
    return;
  }

  grid.innerHTML = filtered.map(car => `
    <div class="car-card">
      <div class="car-card-header">
        <span class="car-card-brand">${car.brand || '—'}</span>
        <span class="badge badge-accent">${car.trim || ''}</span>
      </div>
      <div class="car-card-body">
        <div class="car-info-row">
          <span class="car-info-label">Mühərrik</span>
          <span class="car-info-value">${car.engine || '—'}</span>
        </div>
        <div class="car-info-row">
          <span class="car-info-label">Rəng</span>
          <span class="car-info-value">${car.color || '—'}</span>
        </div>
        <div class="car-info-row">
          <span class="car-info-label">İstehsal ili</span>
          <span class="car-info-value">${formatYear(car.productYear)}</span>
        </div>
        <div class="car-info-row">
          <span class="car-info-label">Say</span>
          <span class="car-info-value">${car.count ?? '—'}</span>
        </div>
      </div>
      <div class="car-card-footer">
        <span class="car-price">$${Number(car.price).toLocaleString()}</span>
        ${isLoggedIn()
          ? `<button class="btn btn-primary btn-sm" onclick="buyCar('${car.brand}')">Satın al</button>`
          : `<a href="login.html" class="btn btn-outline btn-sm">Giriş et</a>`
        }
      </div>
    </div>
  `).join('');
}

async function buyCar(brand) {
  if (!requireAuth()) return;
  try {
    await api.post(`/new-cars/buy/brand/${encodeURIComponent(brand)}`, null);
    showToast(`${brand} uğurla satın alındı!`, 'success');
    loadNewCars();
  } catch (err) {
    showToast(err.message || 'Satın alma uğursuz oldu.', 'error');
  }
}

function formatYear(dt) {
  if (!dt) return '—';
  return new Date(dt).getFullYear();
}

document.addEventListener('DOMContentLoaded', () => {
  updateNavbar();
  loadNewCars();

  const searchInput = document.getElementById('search-input');
  if (searchInput) {
    searchInput.addEventListener('input', () => {
      renderCars(allCars, searchInput.value.toLowerCase());
    });
  }
});
