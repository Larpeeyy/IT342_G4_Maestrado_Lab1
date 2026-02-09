// FILE: web/src/components/DashboardLayout.js
import { useEffect, useState } from "react";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { api } from "../api/api";
import { clearToken } from "../api/auth";

export default function DashboardLayout() {
  const nav = useNavigate();
  const loc = useLocation();
  const [me, setMe] = useState(null);

  async function loadMe() {
    try {
      const res = await api.me();
      setMe(res);
    } catch {
      clearToken();
      nav("/login", { replace: true });
    }
  }

  useEffect(() => {
    loadMe();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function goProfile() {
    nav("/profile");
  }

  function logout() {
    clearToken();
    nav("/login", { replace: true });
  }

  const isProfile = loc.pathname.startsWith("/profile");

  return (
    <div className="shell">
      <aside className="sidebar">
        <div className="brand">
          <div className="brandText">
            <div className="brandTitle">{me?.username || "User"}</div>
            <div className="brandSub">{me?.email || "Loading..."}</div>
          </div>
        </div>

        <div className="nav">
          <button
            className={`navBtn ${isProfile ? "navBtnActive" : ""}`}
            onClick={goProfile}
            type="button"
          >
            Profile
          </button>
        </div>

        <button className="logoutBtn" onClick={logout} type="button">
          Logout
        </button>
      </aside>

      <main className="main">
        <div className="topbar">
          <div className="topTitle">
            <p className="h1">{isProfile ? "Profile" : "Dashboard"}</p>
          </div>
        </div>

        <div className="contentCard">
          <Outlet context={{ me, reloadMe: loadMe }} />
        </div>
      </main>
    </div>
  );
}
