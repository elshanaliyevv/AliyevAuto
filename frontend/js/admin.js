// ================================================
// admin.js – Admin Panel (Brand/Color/Engine/NewCar CRUD)
// ================================================

let adminNewCars = [];
let editingNewCarId = null;

document.addEventListener('DOMContentLoaded', () => {
  if (!requireAuth()) return;
  if (!requireAdmin()) return;
  updateNavbar();
  switchTab('brands');
});

function switchTab(tab) {
  document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
  document.getElementById(`tab-${tab}`)?.classList.add('active');
  document.querySelectorAll('.tab-content').forEach(c => c.style.display = 'none');
  document.getElementById(`content-${tab}`)?.style.setProperty('display', 'block');
  loaders[tab]?.();
}

const loaders = {
  brands: loadBrands,
  colors: loadColors,
  engines: loadEngines,
  newcars: loadAdminNewCars,
  users:    () => {} // No logic needed to load users since we delete by ID
};

/* ═══════════════════════
   BRANDS
═══════════════════════ */
async function loadBrands() {
  const tbody = document.getElementById('brands-tbody');
  tbody.innerHTML = loadingRow(3);
  try {
    const brands = await api.get('/brand/getall') || [];
    if (!brands.length) { tbody.innerHTML = emptyRow(3, 'Marka tapılmadı.'); return; }
    tbody.innerHTML = brands.map(b => `
      <tr>
        <td>${b.id}</td>
        <td>${b.name || b.brand || String(b)}</td>
        <td>
          <div style="display:flex;gap:0.5rem;">
            <button class="btn btn-danger btn-sm" onclick="deleteBrand(${b.id})">Sil</button>
          </div>
        </td>
      </tr>
    `).join('');
  } catch (err) {
    showToast(err.message, 'error');
    tbody.innerHTML = emptyRow(3, 'Xəta baş verdi.');
  }
}

async function addBrand() {
  const name = document.getElementById('new-brand').value.trim();
  if (!name) { showToast('Brand adı daxil edin.', 'error'); return; }
  try {
    const res = await fetch(`${BASE_URL}/brand/add`, {
      method: 'POST',
      headers: {
        'brand': name,
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      }
    });
    if (!res.ok) throw new Error('Brend əlavə edilmədi.');
    showToast(`"${name}" əlavə edildi!`, 'success');
    document.getElementById('new-brand').value = '';
    loadBrands();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

async function deleteBrand(id) {
  if (!confirm('Bu markanı silmək istəyirsiniz?')) return;
  try {
    await api.delete(`/brand/delete/${id}`);
    showToast('Marka silindi.', 'success');
    loadBrands();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

/* ═══════════════════════
   COLORS
═══════════════════════ */
async function loadColors() {
  const tbody = document.getElementById('colors-tbody');
  tbody.innerHTML = loadingRow(3);
  try {
    const colors = await api.get('/color/getall') || [];
    if (!colors.length) { tbody.innerHTML = emptyRow(3, 'Rəng tapılmadı.'); return; }
    tbody.innerHTML = colors.map(c => `
      <tr>
        <td>${c.id}</td>
        <td>${c.colorCode || String(c)}</td>
        <td>
          <button class="btn btn-danger btn-sm" onclick="deleteColor(${c.id})">Sil</button>
        </td>
      </tr>
    `).join('');
  } catch (err) {
    showToast(err.message, 'error');
    tbody.innerHTML = emptyRow(3, 'Xəta baş verdi.');
  }
}

async function addColor() {
  const name = document.getElementById('new-color').value.trim();
  if (!name) { showToast('Rəng adı daxil edin.', 'error'); return; }
  try {
    const res = await fetch(`${BASE_URL}/color/add`, {
      method: 'POST',
      headers: {
        'colorCode': name,
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      }
    });
    if (!res.ok) throw new Error('Rəng əlavə edilmədi.');
    showToast(`"${name}" əlavə edildi!`, 'success');
    document.getElementById('new-color').value = '';
    loadColors();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

async function deleteColor(id) {
  if (!confirm('Bu rəngi silmək istəyirsiniz?')) return;
  try {
    await api.delete(`/color/delete/id/${id}`);
    showToast('Rəng silindi.', 'success');
    loadColors();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

/* ═══════════════════════
   ENGINES
═══════════════════════ */
async function loadEngines() {
  const tbody = document.getElementById('engines-tbody');
  tbody.innerHTML = loadingRow(3);
  try {
    const engines = await api.get('/engine/getall') || [];
    if (!engines.length) { tbody.innerHTML = emptyRow(3, 'Mühərrik tapılmadı.'); return; }
    tbody.innerHTML = engines.map(e => `
      <tr>
        <td>${e.id}</td>
        <td>${e.engineCode || String(e)}</td>
        <td>
          <button class="btn btn-danger btn-sm" onclick="deleteEngine(${e.id})">Sil</button>
        </td>
      </tr>
    `).join('');
  } catch (err) {
    showToast(err.message, 'error');
    tbody.innerHTML = emptyRow(3, 'Xəta baş verdi.');
  }
}

async function addEngine() {
  const name = document.getElementById('new-engine').value.trim();
  if (!name) { showToast('Mühərrik adı daxil edin.', 'error'); return; }
  try {
    const res = await fetch(`${BASE_URL}/engine/add`, {
      method: 'POST',
      headers: {
        'engineCode': name,
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      }
    });
    if (!res.ok) throw new Error('Mühərrik əlavə edilmədi.');
    showToast(`"${name}" əlavə edildi!`, 'success');
    document.getElementById('new-engine').value = '';
    loadEngines();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

async function deleteEngine(id) {
  if (!confirm('Bu mühərriki silmək istəyirsiniz?')) return;
  try {
    await api.delete(`/engine/delete/id/${id}`);
    showToast('Mühərrik silindi.', 'success');
    loadEngines();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

/* ═══════════════════════
   NEW CARS (Admin)
═══════════════════════ */
async function loadAdminNewCars() {
  const tbody = document.getElementById('newcars-tbody');
  tbody.innerHTML = loadingRow(6);
  try {
    const cars = await api.get('/new-cars/all-new-cars') || [];
    if (!cars.length) { tbody.innerHTML = emptyRow(6, 'Avtomobil tapılmadı.'); return; }
    adminNewCars = cars;
    tbody.innerHTML = cars.map((c, i) => `
      <tr>
        <td>${c.brand || '—'}</td>
        <td>${c.engine || '—'}</td>
        <td>${c.color || '—'}</td>
        <td>${c.trim || '—'}</td>
        <td>$${Number(c.price).toLocaleString()}</td>
        <td>${c.count ?? '—'}</td>
        <td>
          <div style="display:flex;gap:0.5rem;">
            <button class="btn btn-outline btn-sm" onclick="openEditNewCarModal(${c.id})">Redaktə</button>
            <button class="btn btn-danger btn-sm" onclick="deleteNewCar(${c.id})">Sil</button>
          </div>
        </td>
      </tr>
    `).join('');
  } catch (err) {
    showToast(err.message, 'error');
    tbody.innerHTML = emptyRow(7, 'Xəta baş verdi.');
  }
}

async function addNewCar() {
  const brand = document.getElementById('nc-brand').value.trim();
  const engine = document.getElementById('nc-engine').value.trim();
  const color = document.getElementById('nc-color').value.trim();
  const trim = document.getElementById('nc-trim').value;
  const price = document.getElementById('nc-price').value;
  const count = document.getElementById('nc-count').value;
  const yearVal = document.getElementById('nc-year').value;

  if (!brand || !engine || !color || !trim || !price || !count || !yearVal) {
    showToast('Bütün sahələri doldurun.', 'error'); return;
  }

  const payload = {
    brand, engine, color, trim,
    price: parseFloat(price),
    count: parseInt(count),
    productYear: new Date(yearVal).toISOString(),
  };

  try {
    await api.post('/new-cars/add', payload);
    showToast('Yeni avtomobil əlavə edildi!', 'success');
    document.getElementById('new-car-form').reset();
    loadAdminNewCars();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

async function deleteNewCar(id) {
  if (!confirm('Bu avtomobili silinsin?')) return;
  try {
    await api.delete(`/new-cars/${id}`);
    showToast('Avtomobil silindi.', 'success');
    loadAdminNewCars();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

/* ── Edit New Car Actions ── */
function openEditNewCarModal(id) {
  const car = adminNewCars.find(c => c.id === id);
  if (!car) { showToast('Avtomobil tapılmadı.', 'error'); return; }

  editingNewCarId = id;
  document.getElementById('e-nc-brand').value = car.brand || '';
  document.getElementById('e-nc-engine').value = car.engine || '';
  document.getElementById('e-nc-color').value = car.color || '';
  document.getElementById('e-nc-trim').value = car.trim || '';
  document.getElementById('e-nc-price').value = car.price || '';
  document.getElementById('e-nc-count').value = car.count || '';

  if (car.productYear) {
    document.getElementById('e-nc-year').value = car.productYear.split('T')[0];
  }

  document.getElementById('edit-newcar-modal').classList.add('open');
}

function closeEditNewCarModal() {
  document.getElementById('edit-newcar-modal').classList.remove('open');
  editingNewCarId = null;
}

async function saveNewCarEdit() {
  if (!editingNewCarId) return;

  const btn = document.getElementById('edit-nc-btn');
  btn.disabled = true;
  btn.textContent = 'Gözləyin...';

  const payload = {
    brand: document.getElementById('e-nc-brand').value.trim(),
    engine: document.getElementById('e-nc-engine').value.trim(),
    color: document.getElementById('e-nc-color').value.trim(),
    trim: document.getElementById('e-nc-trim').value,
    price: parseFloat(document.getElementById('e-nc-price').value),
    count: parseInt(document.getElementById('e-nc-count').value),
    productYear: new Date(document.getElementById('e-nc-year').value).toISOString(),
  };

  try {
    await api.put(`/new-cars/${editingNewCarId}`, payload);
    showToast('Avtomobil yeniləndi!', 'success');
    closeEditNewCarModal();
    loadAdminNewCars();
  } catch (err) {
    showToast(err.message || 'Yeniləmə uğursuz oldu.', 'error');
  } finally {
    btn.disabled = false;
    btn.textContent = 'Yadda Saxla';
  }
}

/* ═══════════════════════
   USERS
═══════════════════════ */
async function deleteUserById() {
  const userId = document.getElementById('delete-user-id').value.trim();
  if (!userId) {
    showToast('Tələb olunan: User ID.', 'error');
    return;
  }
  if (!confirm(`Həqiqətən də ID-si ${userId} olan istifadəçini silmək istəyirsiniz?`)) return;

  try {
    await api.delete(`/user/id/${userId}`);
    showToast('İstifadəçi silindi.', 'success');
    document.getElementById('delete-user-id').value = '';
  } catch (err) {
    showToast(err.message || 'İstifadəçi silinmədi.', 'error');
  }
}

/* ── Helpers ── */
function loadingRow(cols) {
  return `<tr><td colspan="${cols}"><div class="loading-wrap"><div class="spinner"></div></div></td></tr>`;
}
function emptyRow(cols, msg) {
  return `<tr><td colspan="${cols}" style="text-align:center;padding:2rem;color:var(--text-muted);">${msg}</td></tr>`;
}
