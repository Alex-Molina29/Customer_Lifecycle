import "../styles/FormInput.css";

export default function FormSelect({
    label = "",
    name,
    value,
    onChange,
    options = [],
    required = false,
    placeholder = "Seleccione una opción...",
    className = "",
    onKeyDown = null
}) {
    return (
        <div className={`form-field ${className}`.trim()}>
            <label htmlFor={name}>{label}</label>
            <div className="input-wrapper">
                <select
                    id={name}
                    name={name}
                    value={value}
                    onChange={onChange}
                    onKeyDown={onKeyDown}
                    required={required}
                >
                    <option value="" disabled hidden>{placeholder}</option>
                    {options.map((option, index) => (
                        <option key={index} value={option.value}>
                            {option.label}
                        </option>
                    ))}
                </select>
            </div>
        </div>
    )
}