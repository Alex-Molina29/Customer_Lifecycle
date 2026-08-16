import "../styles/Register.css";

import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import FormInput from "../components/FormInput";
import Button from "../components/Button";
import { AuthContext } from "../context/AuthContext";
import { register } from "../services/authService";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [verifyPassword, setVerifyPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const { token } = useContext(AuthContext);
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
      if (verifyPassword === password) {
        await register(username, password, "USER");
        setSuccess(true);
        setTimeout(() => navigate("/login"), 1500);
      } else {
        setSuccess(false);
        setError("Las contraseñas son diferentes");
      }
    } catch (error) {
      setError("No se pudo crear el usuario (puede encontrarse ya registrado)");
      console.log(error.message);
    }
  };

  const changeUsername = (e) => {
    setUsername(e.target.value);
  };

  const changePassword = (e) => {
    setPassword(e.target.value);
  };

  const changeVerifyPassword = (e) => {
    setVerifyPassword(e.target.value);
  };

  return (
    <div className="auth-card">
      <div className="auth-banner">
        <p>Bienvenido al portal de gestión</p>
      </div>
      <div className="auth-form-container">
        <h1>Registrate</h1>
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

          <FormInput
            label="Vuelva a ingresar su contraseña"
            name="password"
            type="password"
            value={verifyPassword}
            onChange={changeVerifyPassword}
            required
          />
          {error && <p className="error">{error}</p>}
          {success && (
            <p className="success-register">Cuenta creada, redirigiendo...</p>
          )}
          <Button type="submit" text="Registrarme" />
        </form>
        <p>
          <Link to="/login">¿Ya tienes una cuenta? Ingresa</Link>
        </p>
      </div>
    </div>
  );
}
