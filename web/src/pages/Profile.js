import { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";
import { api } from "../api/api";

export default function Profile() {
  const { me, reloadMe } = useOutletContext();

  const [username, setUsername] = useState("");
  const [saving, setSaving] = useState(false);
  const [err, setErr] = useState("");
  const [ok, setOk] = useState("");

  useEffect(() => {
    if (me?.username) setUsername(me.username);
  }, [me]);

  async function save(e) {
    e.preventDefault();
    setErr("");
    setOk("");
    setSaving(true);

    try {
      await api.updateUsername(username);
      await reloadMe();
      setOk("Username updated successfully");
    } catch (ex) {
      setErr(ex.message || "Failed to update username");
    } finally {
      setSaving(false);
    }
  }

  return (
    <div>
      {err ? <div className="error">{err}</div> : null}
      {ok ? <div className="success">{ok}</div> : null}

      <form className="form" onSubmit={save}>
        <label className="label">
          Email
          <input className="input" value={me?.email || ""} disabled />
        </label>

        <label className="label">
          Username
          <input
            className="input"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Enter username"
            required
          />
        </label>

        <div className="row">
          <button className="btnBlue" type="button" onClick={reloadMe}>
            Refresh
          </button>

          <button className="btnGreen" type="submit" disabled={saving}>
            {saving ? "Saving..." : "Save Changes"}
          </button>
        </div>
      </form>
    </div>
  );
}
