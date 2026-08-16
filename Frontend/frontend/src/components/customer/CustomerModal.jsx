import { useState } from "react";
import { createCustomer, deleteCustomer, updateCustomer } from "../../services/customerService";
import Button from "../Button";
import FormInput from "../FormInput";
import FormSelect from "../FormSelect";

const initialFormData = {
    documentType: "",
    documentNumber: "",
    name: "",
    email: "",
    phoneNumber: "",
}

const typeDocumentOptions = [
    { value: "CC", label: "Cédula de ciudadanía" },
    { value: "CE", label: "Cédula de extranjería" },
    { value: "NIT", label: "Número de identificación tributaria" },
    { value: "PASSPORT", label: "Pasaporte" },
]

export default function CustomerModal({ mode, customer, onClose}) {
    console.log("CustomerModal props:", { mode, customer });
    const [currentMode, setCurrentMode] = useState(mode);
    const [formData, setFormData] = useState(
        customer ? {  ...initialFormData, ...customer  } : initialFormData
    )
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess(false);
        try {
            if (currentMode === "edit") {
                await updateCustomer(customer.id, formData)
            } else if (currentMode === "create") {
                await createCustomer(formData)
            } else {
                setError("Modo inválido");
                onClose(true);
                return;
            }
            setSuccess(true);
            onClose(true);
        } catch (error) {
            setError("No se pudo guardar el cliente. Revisa los datos.");
            console.log(error.message);
        }
    };

    const handleConfirmDelete = async () => {
        setSuccess(true);
        try {
            console.log("Deleting customer:", customer);
            await deleteCustomer(customer.id);
            onClose(true);
        } catch (error) {
            setError("No se pudo eliminar el cliente.");
            console.log(error.message);
            setSuccess(false);
        }
    };

    return (
        <div 
            className="customer-modal-overlay" 
            onClick={() => onClose(false)}>
                <div 
                    className="customer-modal"
                    onClick={(e) => e.stopPropagation()}>
                        {currentMode === "delete" ? (
                            <>
                                <h2>Eliminar cliente</h2>
                                <p>
                                    Vas a eliminar el cliente <strong>{customer.name}</strong>. ¿Estás seguro?
                                </p>
                                {error && <p className="error">{error}</p>}
                                <div className="customer-modal-actions">
                                    <Button 
                                        variant="danger"
                                        onClick={handleConfirmDelete}
                                        disabled={success}
                                        text={success ? "Eliminando..." : "Sí, eliminar"}
                                    />
                                    <Button 
                                        variant="cancel"
                                        onClick={() => onClose(false)}
                                        text="Cancelar"
                                    />
                                </div>
                            </>
                        ): currentMode === "view" ? (
                            <>
                                <h2>Ver cliente</h2>
                                <h3>{customer.name}</h3>
                                <div className="customer-modal-view-details">
                                    <p><strong>Correo electrónico:</strong> {customer.email ? `${customer.email}` : ""}</p>
                                    <p><strong>Tipo de documento:</strong> {customer.documentType ? `${customer.documentType}` : ""}</p>
                                    <p><strong>Número de documento:</strong> {customer.documentNumber ? `${customer.documentNumber}` : ""}</p>
                                    <p><strong>Número de teléfono:</strong> {customer.phoneNumber ? `${customer.phoneNumber}` : ""}</p>
                                </div>

                                <div className="customer-modal-actions">
                                    <Button 
                                        variant="danger"
                                        onClick={() => setCurrentMode("delete")}
                                        text={"Eliminar cliente"}
                                    />
                                    <Button 
                                        variant="edit"
                                        onClick={() => setCurrentMode("edit")}
                                        text={"Editar cliente"}
                                    />
                                    <Button 
                                        variant="cancel"
                                        onClick={() => onClose(false)}
                                        text="Cancelar"
                                    />
                                </div>
                            </>
                        ) : (
                            <>
                                <h2>{currentMode === "edit" ? "Editar cliente" : "Crear cliente"}</h2>
                                <form onSubmit={handleSubmit} className="customer-modal-form">
                                    <FormInput 
                                        label="Nombre"
                                        name="name"
                                        value={formData.name}
                                        onChange={handleChange}
                                        required
                                    />
                                    <FormInput 
                                        label="Correo electrónico"
                                        name="email"
                                        type="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                    />
                                    <FormSelect 
                                        label="Tipo de documento"
                                        name="documentType"
                                        value={formData.documentType}
                                        onChange={handleChange}
                                        options={typeDocumentOptions}
                                        placeholder="Seleccione un tipo de documento"
                                    />
                                    <FormInput 
                                        label="Número de documento"
                                        name="documentNumber"
                                        value={formData.documentNumber}
                                        onChange={handleChange}
                                    />
                                    <FormInput 
                                        label="Número de teléfono"
                                        name="phoneNumber"
                                        value={formData.phoneNumber}
                                        onChange={handleChange}
                                    />
                                    {error && <p className="error">{error}</p>}
                                    {success && (
                                        <p className="success-register">Cliente guardado</p>
                                    )}
                                    <div className="customer-modal-actions">
                                        <Button 
                                            type="button"
                                            variant="customer-model-cancel"
                                            onClick={() => onClose(false)}
                                            text="Cancelar"
                                        />
                                        <Button 
                                            type="submit"
                                            text={success ? "Guardando..." : currentMode === "edit" ? "Guardar cambios" : "Crear cliente"}
                                            disabled={success}
                                        />
                                    </div>
                                </form>
                            </>
                        )}
                </div>
        </div>
    )
}
