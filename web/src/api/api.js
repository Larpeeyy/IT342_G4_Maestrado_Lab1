
import { getToken, clearToken } from "./auth";

const BASE_URL = "http://localhost:8080";

async function request(path, method = "GET", body) {
  const headers = { "Content-Type": "application/json" };
  const token = getToken();
  if (token) headers.Authorization = `Bearer ${token}`;

  const res = await fetch(BASE_URL + path, {
    method,
    headers,
    body: body ? JSON.stringify(body) : null
  });

  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (res.status === 401) clearToken();
  if (!res.ok) throw new Error(data?.message || `Request failed (${res.status})`);

  return data;
}

export const api = {
  register(payload) {
    return request("/api/auth/register", "POST", payload);
  },
  login(payload) {
    return request("/api/auth/login", "POST", payload);
  },
  me() {
    return request("/api/users/me", "GET");
  },
  updateUsername(username) {
    return request("/api/users/me/username", "PATCH", { username });
  }
};
