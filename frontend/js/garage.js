// ================================================
// garage.js – My Service Cars (CRUD + due status)
// ================================================

let myCars = [];
let editingCarId = null;

document.addEventListener('DOMContentLoaded', () => {
  if (!requireAuth()) return;
  updateNavbar();
  fetchMetadata(); // Load brands, colors, engines for selects
  loadMyCars();

  document.getElementById('add-car-btn').addEventListener('click', () => openModal());
  document.getElementById('garage-form').addEventListener('submit', saveCar);
  document.getElementById('modal-close').addEventListener('click', closeModal);
  document.getElementById('garage-modal').addEventListener('click', e => {
    if (e.target === document.getElementById('garage-modal')) closeModal();
  });
});

let brands = [], colorsArr = [], engines = [];

async function fetchMetadata() {
  try {
    const results = await Promise.allSettled([
      api.get('/brand/getall'),
      api.get('/color/getall'),
      api.get('/engine/getall')
    ]);
    brands = results[0].status === 'fulfilled' ? (results[0].value || []) : [];
    colorsArr = results[1].status === 'fulfilled' ? (results[1].value || []) : [];
    engines = results[2].status === 'fulfilled' ? (results[2].value || []) : [];
    populateSelects();
  } catch (err) {
    console.error('Metadata fetch critical error:', err);
  }
}

function populateSelects() {
  const bList = document.getElementById('brands-list');
  const eList = document.getElementById('engines-list');
  const cList = document.getElementById('colors-list');
  if (!bList || !eList || !cList) return;

  bList.innerHTML = brands.map(b => `<option value="${b.name}">`).join('');
  eList.innerHTML = engines.map(e => `<option value="${e.engineCode}">`).join('');
  cList.innerHTML = colorsArr.map(c => `<option value="${c.colorCode}">`).join('');
}

async function loadMyCars() {
  const tbody = document.getElementById('cars-tbody');
  tbody.innerHTML = `<tr><td colspan="6" class="text-center">
    <div class="loading-wrap"><div class="spinner"></div><p>Yüklənir...</p></div></td></tr>`;

  try {
    myCars = await api.get('/service-cars/my') || [];
    renderTable(myCars);
  } catch (err) {
    showToast(err.message || 'Avtomobillər yüklənmədi.', 'error');
    tbody.innerHTML = `<tr><td colspan="6">Xəta baş verdi.</td></tr>`;
  }
}

function renderTable(cars) {
  const tbody = document.getElementById('cars-tbody');
  if (!cars.length) {
    tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;padding:3rem;color:var(--text-muted);">
      Qarajınız boşdur. İlk avtomobilinizi əlavə edin! 🚗</td></tr>`;
    return;
  }
  tbody.innerHTML = cars.map(c => `
    <tr data-id="${c.id}">
      <td><strong>${c.brand || '—'}</strong></td>
      <td>${c.engine || '—'}</td>
      <td>${c.color || '—'}</td>
      <td>${c.kilometers?.toLocaleString() ?? '—'} km</td>
      <td>${formatYear(c.productYear)}</td>
      <td>
        <div style="display:flex;gap:0.5rem;">
          <button class="btn btn-outline btn-sm" onclick="openOdometerModal(${c.id})">Yürüş</button>
          <button class="btn btn-outline btn-sm" onclick="openModal(${c.id})">✏️</button>
          <button class="btn btn-danger btn-sm" onclick="deleteCar(${c.id})">🗑</button>
          <button class="btn btn-sm" style="background:rgba(46,213,115,0.1);color:var(--success);border:1px solid rgba(46,213,115,0.3)" onclick="showServiceModal(${c.id})">Servis</button>
        </div>
      </td>
    </tr>
  `).join('');
}

function openModal(carId = null) {
  editingCarId = carId;
  const modal = document.getElementById('garage-modal');
  const title = document.getElementById('modal-title');
  const form = document.getElementById('garage-form');
  form.reset();

  if (carId) {
    const car = myCars.find(c => c.id === carId);
    title.textContent = 'Avtomobili yenilə';
    if (car) {
      document.getElementById('f-brand').value = car.brand || '';
      document.getElementById('f-engine').value = car.engine || '';
      document.getElementById('f-color').value = car.color || '';
      document.getElementById('f-kilometers').value = car.kilometers || '';
      document.getElementById('f-year').value = car.productYear ? car.productYear.split('T')[0] : '';
    }
  } else {
    title.textContent = 'Yeni avtomobil əlavə et';
  }
  modal.classList.add('open');
}

function closeModal() {
  document.getElementById('garage-modal').classList.remove('open');
  editingCarId = null;
}
window.openModal = openModal;
window.closeModal = closeModal;
window.deleteCar = deleteCar;
window.openOdometerModal = openOdometerModal;
window.showServiceModal = showServiceModal;

async function saveCar(e) {
  e.preventDefault();
  const btn = document.getElementById('save-car-btn');
  btn.disabled = true;

  const brandVal = document.getElementById('f-brand').value;
  const engineVal = document.getElementById('f-engine').value;
  const colorVal = document.getElementById('f-color').value;
  const kmVal = document.getElementById('f-kilometers').value;

  const payload = {
    brand: brandVal,
    engine: engineVal,
    color: colorVal,
    kilometers: kmVal === '' ? 0 : parseInt(kmVal),
    productYear: document.getElementById('f-year').value
      ? document.getElementById('f-year').value + "T00:00:00"
      : new Date().toISOString().split('.')[0],
  };

  try {
    if (editingCarId) {
      await api.put(`/service-cars/${editingCarId}`, payload);
      showToast('Avtomobil yeniləndi!', 'success');
    } else {
      await api.post('/service-cars', payload);
      showToast('Avtomobil əlavə edildi!', 'success');
    }
    closeModal();
    loadMyCars();
  } catch (err) {
    showToast(err.message || 'Xəta baş verdi.', 'error');
  } finally {
    btn.disabled = false;
  }
}

async function deleteCar(carId) {
  if (!confirm('Bu avtomobili silmək istədiyinizə əminsiniz?')) return;
  try {
    await api.delete(`/service-cars/${carId}`);
    showToast('Avtomobil silindi.', 'success');
    loadMyCars();
  } catch (err) {
    showToast(err.message || 'Silmə uğursuz oldu.', 'error');
  }
}

/* ── Odometer Modal ── */
function openOdometerModal(carId) {
  const km = prompt('Yeni yürüş (km) daxil edin:');
  if (km === null) return;
  const parsed = parseInt(km);
  if (isNaN(parsed) || parsed < 0) { showToast('Düzgün rəqəm daxil edin.', 'error'); return; }
  updateOdometer(carId, parsed);
}

async function updateOdometer(carId, kilometers) {
  try {
    await api.patch(`/service-cars/${carId}/odometer`, { kilometers });
    showToast('Yürüş yeniləndi!', 'success');
    loadMyCars();
  } catch (err) {
    showToast(err.message || 'Yürüş yenilənmədi.', 'error');
  }
}

/* ── Service Modal & Confirmation ── */
let currentServiceCarId = null;

async function showServiceModal(carId) {
  currentServiceCarId = carId;
  try {
    const status = await api.get(`/service-cars/${carId}/due-status`);
    console.log('Status received:', status);

    let el = document.getElementById('service-modal');
    if (!el) {
      el = document.createElement('div');
      el.id = 'service-modal';
      el.className = 'modal-backdrop';
      el.innerHTML = `
        <div class="modal" style="max-width:480px">
          <div class="modal-header">
            <h3>🔧 Servis & Baxım</h3>
            <button class="modal-close" onclick="closeServiceModal()">×</button>
          </div>
          <div style="padding:1.5rem;">
            <div id="s-summary-box" style="background:rgba(var(--primary-rgb),0.1); padding:1rem; border-radius:12px; margin-bottom:1.2rem; border-left:4px solid var(--primary);">
                <p id="s-summary" style="font-weight:600; color:var(--primary); font-size:0.9rem;"></p>
                <p id="s-current-km" style="font-size:0.8rem; color:var(--text-muted); margin-top:0.3rem;"></p>
                <p id="s-total-amount" style="font-size:0.8rem; color:var(--text-secondary); margin-top:0.3rem; font-weight:bold;"></p>
            </div>
            
            <div style="background:var(--bg-secondary); padding:1.2rem; border-radius:12px; margin-bottom:1.5rem;">
                <p style="font-size:0.85rem; font-weight:600; color:var(--text-secondary); margin-bottom:1rem;">Texniki Hesabat:</p>
                <div style="display:flex; flex-direction:column; gap:1rem;">
                  <label class="service-item">
                    <input type="checkbox" id="s-oil"> 
                    <div class="service-info">
                        <span class="service-title">Motor Yağı (60 AZN)</span>
                        <span class="service-meta" id="m-oil">Yüklənir...</span>
                    </div>
                  </label>
                  <label class="service-item">
                    <input type="checkbox" id="s-air"> 
                    <div class="service-info">
                        <span class="service-title">Hava Filtri (15 AZN)</span>
                        <span class="service-meta" id="m-air">Yüklənir...</span>
                    </div>
                  </label>
                  <label class="service-item">
                    <input type="checkbox" id="s-oil-filter"> 
                    <div class="service-info">
                        <span class="service-title">Yağ Filtri (10 AZN)</span>
                        <span class="service-meta" id="m-oil-filter">Yüklənir...</span>
                    </div>
                  </label>
                </div>
            </div>
            
            <input type="hidden" id="s-odo">
            <button id="confirm-service-btn" class="btn btn-block" onclick="confirmServiceSubmit()">Görülən İşləri Təsdiqlə</button>
          </div>
        </div>`;
      document.body.appendChild(el);

      // Basic styles for the new service items
      const style = document.createElement('style');
      style.textContent = `
        .service-item { display:flex; align-items:flex-start; gap:0.8rem; cursor:pointer; }
        .service-item input { width:20px; height:20px; margin-top:2px; }
        .service-info { display:flex; flex-direction:column; }
        .service-title { font-size:0.9rem; font-weight:500; }
        .service-meta { font-size:0.75rem; color:var(--text-muted); margin-top:2px; }
        .status-due { color:var(--danger); font-weight:600; }
        .status-ok { color:var(--success); font-weight:600; }
      `;
      document.head.appendChild(style);
    }

    document.getElementById('s-summary').textContent = status.summary;
    document.getElementById('s-current-km').textContent = `Hazırkı yürüş: ${status.currentKilometers} km`;
    if (document.getElementById('s-total-amount')) {
      document.getElementById('s-total-amount').textContent = `Təxmini xərc: ${status.totalAmount || 0} AZN`;
    }
    document.getElementById('s-odo').value = status.currentKilometers;

    document.getElementById('s-oil').checked = status.oilChangeDue;
    document.getElementById('m-oil').innerHTML = `Son dəyişmə: ${status.lastOilChangeKilometr} km. Status: <span class="${status.oilChangeDue ? 'status-due' : 'status-ok'}">${status.oilChangeDue ? 'Dəyişilməlidir' : 'Hələ lazim deyil'}</span>`;

    document.getElementById('s-air').checked = status.airFilterDue;
    document.getElementById('m-air').innerHTML = `Son dəyişmə: ${status.lastAirFilterChangeKilometr} km. Status: <span class="${status.airFilterDue ? 'status-due' : 'status-ok'}">${status.airFilterDue ? 'Dəyişilməlidir' : 'Hələ lazim deyil'}</span>`;

    document.getElementById('s-oil-filter').checked = status.oilFilterDue;
    document.getElementById('m-oil-filter').innerHTML = `Son dəyişmə: ${status.lastOilFilterChangeKilometr} km. Status: <span class="${status.oilFilterDue ? 'status-due' : 'status-ok'}">${status.oilFilterDue ? 'Dəyişilməlidir' : 'Hələ lazim deyil'}</span>`;

    el.classList.add('open');
  } catch (err) {
    showToast(err.message || 'Status yoxlanılmadı.', 'error');
  }
}

function closeServiceModal() {
  const el = document.getElementById('service-modal');
  if (el) el.classList.remove('open');
  currentServiceCarId = null;
}

async function confirmServiceSubmit() {
  if (!currentServiceCarId) return;
  const btn = document.getElementById('confirm-service-btn');
  btn.disabled = true;
  btn.textContent = 'Gözləyin...';

  const payload = {
    oilChange: document.getElementById('s-oil').checked,
    airFilterChange: document.getElementById('s-air').checked,
    oilFilterChange: document.getElementById('s-oil-filter').checked,
    odometerAtService: parseInt(document.getElementById('s-odo').value) || 0
  };

  try {
    await api.post(`/service-cars/${currentServiceCarId}/last-service/confirm`, payload);
    showToast('Servis qeydə alındı!', 'success');
    closeServiceModal();
    loadMyCars();
  } catch (err) {
    showToast(err.message || 'Servis təsdiqlənmədi.', 'error');
  } finally {
    btn.disabled = false;
    btn.textContent = 'Təsdiqlə və Yadda Saxla';
  }
}

function formatYear(dt) {
  if (!dt) return '—';
  return new Date(dt).getFullYear();
}
