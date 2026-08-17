import "../styles/loader.css";

export default function Loader({ text = "Cargando..." }) {
  return (
    <div className="loader-container" role="status" aria-live="polite">
      <span className="loader-spinner"></span>
      <span>{text}</span>
    </div>
  );
}
