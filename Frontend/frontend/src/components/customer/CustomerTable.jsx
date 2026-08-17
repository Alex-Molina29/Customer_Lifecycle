import Button from "../Button";
import Loader from "../Loader";

//const columnsExample = [
  //  { key: "documentNumber", label: "Numero de documento" },
    //{ key: "name", label: "Nombre" }
//];

export default function CustomerTable({
    customers,
    loading,
    onRowClick,
    onDeleteClick,
    columns
}) {

    if(loading) {
        return <Loader text="Cargando clientes..."></Loader>
    }

    if(customers.length === 0) {
        return <p>No hay clientes para mostrar</p>
    }


    return (
        <div>
            <table className="customer-table">
                <thead>
                    <tr>
                        {columns.map((col) => (
                            <th key={col.key}>{col.label}</th>
                        ))}
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {customers.map((customer) => (
                        <tr
                            key={customer.id}
                            onClick={() => onRowClick(customer)}
                        >
                            {columns.map((col) => (
                                <td
                                    key={col.key}
                                >
                                    {customer[col.key]}
                                </td>
                            ))}
                            <td
                                onClick={(e) => e.stopPropagation()}
                            >
                                <Button 
                                    onClick={() => onDeleteClick(customer)}
                                    text="Eliminar"
                                    variant="danger"
                                />

                            </td>
                        </tr> 
                    ))}
                </tbody>
            </table>
        </div>
        
    )
}