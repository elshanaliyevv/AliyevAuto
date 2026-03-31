// ================================================
// api.js – Base fetch wrapper with JWT support
// ================================================

const BASE_URL = window.location.hostname === "localhost"
    ? "http://localhost:9090"
    : "https://aliyevauto.onrender.com";

/**
 * Main API request function.
 * Automatically attaches the Authorization header if a token exists.
 * Handles 401 by attempting a token refresh.
 */
async function apiRequest(method, path, body = null) {
  const token = localStorage.getItem('accessToken');
  const headers = { 'Content-Type': 'application/json' };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const options = { method, headers };
  if (body) options.body = JSON.stringify(body);

  let res = await fetch(BASE_URL + path, options);

  // Try to refresh on 401
  if (res.status === 401) {
    const refreshed = await tryRefresh();
    if (refreshed) {
      headers['Authorization'] = `Bearer ${localStorage.getItem('accessToken')}`;
      res = await fetch(BASE_URL + path, { method, headers, body: options.body });
    } else {
      logout();
      return null;
    }
  }

  if (res.status === 204) return null; // No content

  const text = await res.text();
  let data;
  try { data = JSON.parse(text); } catch { data = text; }

  if (!res.ok) {
    throw new Error(typeof data === 'string' ? data : (data?.message || `Error ${res.status}`));
  }
  return data;
}

async function tryRefresh() {
  const refreshToken = localStorage.getItem('refreshToken');
  if (!refreshToken) return false;
  try {
    const res = await fetch(`${BASE_URL}/api/auth/refresh`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ refreshToken }),
    });
    if (!res.ok) return false;
    const data = await res.json();
    const tokens = data.tokensResponse;
    localStorage.setItem('accessToken', tokens.accessToken);
    if (tokens.refreshToken) localStorage.setItem('refreshToken', tokens.refreshToken);
    return true;
  } catch {
    return false;
  }
}

function logout() {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  localStorage.removeItem('userRole');
  localStorage.removeItem('userId');
  window.location.href = 'login.html';
}

// ── Convenience helpers ──
const api = {
  get:    (path)         => apiRequest('GET',    path),
  post:   (path, body)   => apiRequest('POST',   path, body),
  put:    (path, body)   => apiRequest('PUT',    path, body),
  patch:  (path, body)   => apiRequest('PATCH',  path, body),
  delete: (path)         => apiRequest('DELETE', path),
};

// ── Toast notification ──
function showToast(message, type = 'info') {
  let container = document.getElementById('toast-container');
  if (!container) {
    container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container';
    document.body.appendChild(container);
  }
  const toast = document.createElement('div');
  toast.className = `toast toast-${type}`;
  const icons = { success: '✓', error: '✕', info: 'ℹ' };
  toast.innerHTML = `<span>${icons[type] || 'ℹ'}</span><span>${message}</span>`;
  container.appendChild(toast);
  setTimeout(() => toast.remove(), 3500);
}
