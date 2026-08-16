import "../styles/Login.css";

import { useContext, useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { AuthContext } from "../context/AuthContext";
import { login as loginRequest } from "../services/authService";

import FormInput from "../components/FormInput";
import Button from "../components/Button";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { login, token } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      navigate("/customers", { replace: true });
    }
  }, [token, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {

      const { token: newToken } = await loginRequest(username, password)
      login(newToken)
      navigate("/customers");
    } catch (error) {
      setError("Usuario o contraseña incorrectos.");
      console.log(error.message);
    }
  };

  const changeUsername = (e) => {
    setUsername(e.target.value);
  };

  const changePassword = (e) => {
    setPassword(e.target.value);
  };

  return (
    <div className="auth-card">
      <div className="auth-banner">
        <p>Bienvenido al portal de gestión</p>
      </div>
      <div className="auth-form-container">
        <h1>Iniciar sesión</h1>
        <form onSubmit={handleSubmit}>
          <FormInput
            label="Usuario"
            name="username"
            value={username}
            onChange={changeUsername}
            required
          />
          <FormInput
            label="Contraseña"
            name="password"
            type="password"
            value={password}
            onChange={changePassword}
            required
          />
          {error && <p className="error">{error}</p>}
          <Button type="submit" text="Entrar" />
        </form>
        <p>
          <Link to="/register">
            ¿No te encuentras registrado? Crea una cuenta
          </Link>
        </p>
      </div>
    </div>
  );
}
