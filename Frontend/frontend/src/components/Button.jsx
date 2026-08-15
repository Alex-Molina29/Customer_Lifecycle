import '../styles/Button.css';

export default function Button({
    children,
    type = 'button',
    variant = 'primary',
    className = '',
    text = '',
    iconSrc = null,
    alt = '',
    onClick,
    disabled = false
}) {
    return (
        <button
            type={type}
            className={`btn btn-${variant} ${className}`.trim()}
            onClick={onClick}
            disabled = {disabled}
        >
            <span className={`btn-text ${variant}`}> {text}</span>
            {iconSrc && (
                <img src={iconSrc} alt={alt} className="btn-icon-img" />
            )}
            {children}
        </button>
    )
}