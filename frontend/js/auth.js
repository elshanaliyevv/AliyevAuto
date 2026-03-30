// ================================================
// auth.js – Login, Register, token management
// ================================================

function isLoggedIn() {
  return !!localStorage.getItem('accessToken');
}

function isAdmin() {
  return localStorage.getItem('userRole') === 'ADMIN';
}

function requireAuth() {
  if (!isLoggedIn()) {
    window.location.href = 'login.html';
    return false;
  }
  return true;
}

function requireAdmin() {
  if (!isAdmin()) {
    showToast('Bu səhifəyə giriş üçün Admin hüququ lazımdır.', 'error');
    setTimeout(() => window.location.href = 'index.html', 1500);
    return false;
  }
  return true;
}

/* ── Decode JWT payload ── */
function parseJwt(token) {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch {
    return null;
  }
}

/* ── Save tokens + extract claims ── */
/* ── Save tokens + extract claims ── */
function saveAuth(data) {
  const tokens = data.tokensResponse;
  const user = data.userResponse;

  if (tokens) {
    localStorage.setItem('accessToken', tokens.accessToken);
    if (tokens.refreshToken) localStorage.setItem('refreshToken', tokens.refreshToken);
  }

  if (user) {
    // Backend gives role directly (e.g., "ADMIN" or "USER")
    localStorage.setItem('userRole', user.role);
    localStorage.setItem('userEmail', user.email || '');
    localStorage.setItem('userName', user.username || '');
    localStorage.setItem('userNumber', user.number || '');
    localStorage.setItem('userId', user.id);
  }
}

/* ── Login ── */
async function handleLogin(e) {
  e.preventDefault();
  const email    = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;
  const btn      = document.getElementById('login-btn');
  const alert    = document.getElementById('login-alert');

  alert.classList.remove('show');
  btn.disabled = true;
  btn.textContent = 'Giriş edilir...';

  try {
    const data = await api.post('/api/auth/login', { identifier: email, password });
    saveAuth(data);
    showToast('Uğurla daxil oldunuz!', 'success');
    setTimeout(() => window.location.href = 'index.html', 600);
  } catch (err) {
    alert.textContent = err.message || 'Giriş uğursuz oldu.';
    alert.classList.add('show');
    btn.disabled = false;
    btn.textContent = 'Daxil ol';
  }
}

/* ── Register ── */
async function handleRegister(e) {
  e.preventDefault();
  const username = document.getElementById('username').value.trim();
  const email    = document.getElementById('email').value.trim();
  const password = document.getElementById('password').value;
  const number   = document.getElementById('number').value.trim();
  const btn      = document.getElementById('register-btn');
  const alert    = document.getElementById('register-alert');

  alert.classList.remove('show');
  btn.disabled = true;
  btn.textContent = 'Qeydiyyat...';

  try {
    const data = await api.post('/api/auth/register', { username, email, password, number });
    saveAuth(data);
    showToast('Qeydiyyat uğurla tamamlandı!', 'success');
    setTimeout(() => window.location.href = 'index.html', 600);
  } catch (err) {
    alert.textContent = err.message || 'Qeydiyyat uğursuz oldu.';
    alert.classList.add('show');
    btn.disabled = false;
    btn.textContent = 'Qeydiyyatdan keç';
  }
}

/* ── Update navbar based on auth state ── */
function updateNavbar() {
  const loggedIn = isLoggedIn();
  const admin    = isAdmin();

  const loginLink    = document.getElementById('nav-login');
  const registerLink = document.getElementById('nav-register');
  const garageLink   = document.getElementById('nav-garage');
  const profileLink  = document.getElementById('nav-profile');
  const adminLink    = document.getElementById('nav-admin');
  const logoutLink   = document.getElementById('nav-logout');
  const emailSpan    = document.getElementById('nav-email');

  if (loginLink)    loginLink.style.display    = loggedIn ? 'none' : '';
  if (registerLink) registerLink.style.display = loggedIn ? 'none' : '';
  if (garageLink)   garageLink.style.display   = loggedIn ? '' : 'none';
  if (profileLink)  profileLink.style.display  = loggedIn ? '' : 'none';
  if (adminLink)    adminLink.style.display     = (loggedIn && admin) ? '' : 'none';
  if (logoutLink)   logoutLink.style.display    = loggedIn ? '' : 'none';
  if (emailSpan)    emailSpan.textContent       = localStorage.getItem('userEmail') || '';
}

/* ── Profile Management ── */
async function openProfileModal() {
  document.getElementById('p-username').value = localStorage.getItem('userName') || '';
  document.getElementById('p-email').value = localStorage.getItem('userEmail') || '';
  document.getElementById('p-number').value = localStorage.getItem('userNumber') || '';
  document.getElementById('profile-modal').classList.add('open');
}

function closeProfileModal() {
  document.getElementById('profile-modal').classList.remove('open');
}

async function saveUsername() {
  const val = document.getElementById('p-username').value.trim();
  if (!val) return;
  try {
    await api.patch(`/user/updateusername?username=${encodeURIComponent(val)}`);
    localStorage.setItem('userName', val);
    showToast('İstifadəçi adı dəyişdirildi.', 'success');
  } catch (err) {
    showToast(err.message || 'Xəta baş verdi.', 'error');
  }
}

async function saveEmail() {
  const val = document.getElementById('p-email').value.trim();
  if (!val) return;
  try {
    await api.patch(`/user/updatemail?mail=${encodeURIComponent(val)}`);
    localStorage.setItem('userEmail', val);
    updateNavbar();
    showToast('Email dəyişdirildi.', 'success');
  } catch (err) {
    showToast(err.message || 'Xəta baş verdi.', 'error');
  }
}

async function saveNumber() {
  const val = document.getElementById('p-number').value.trim();
  if (!val) return;
  try {
    await api.patch(`/user/updatenumber?number=${encodeURIComponent(val)}`);
    localStorage.setItem('userNumber', val);
    showToast('Telefon nömrəsi dəyişdirildi.', 'success');
  } catch (err) {
    showToast(err.message || 'Xəta baş verdi.', 'error');
  }
}
