import '../styles/FormInput.css';

export default function FormInput({
    label = '',
    name,
    value,
    onChange,
    type = 'text',
    required = false,
    placeholder = 'Escriba aquí...',
    className = ''
}) {
    return (
        <div className={`form-field ${className}`.trim()}>
            <label htmlFor={name}>{label}</label>
            <input
                id={name}
                name={name}
                type={type}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                required={required}
            />
        </div>
    )
}