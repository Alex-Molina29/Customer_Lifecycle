import "../styles/Login.css";

import { useContext, useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { AuthContext } from "../context/AuthContext";

import FormInput from "../components/FormInput";
import Button from "../components/Button";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = "Simulación del login";
      console.log(response);
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
