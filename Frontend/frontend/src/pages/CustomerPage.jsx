import { useContext, useState, useEffect, useCallback } from "react";
import { AuthContext } from "../context/AuthContext";
import { getCustomerById, getCustomers } from "../services/customerService";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";
import FormInput from "../components/FormInput";
import CustomerTable from "../components/customer/CustomerTable";
import CustomerModal from "../components/customer/CustomerModal";

import "../styles/customer-page.css";
import Loader from "../components/Loader";

const COLUMNS = [
  { key: "documentNumber", label: "Número de documento" },
  { key: "name", label: "Nombre" },
];

export default function CustomerPage() {
  const [customers, setCustomers] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [loading, setLoading] = useState(false);
  const [loadingCustomer, setLoadingCustomer] = useState(false);
  const [error, setError] = useState("");

  const [searchInput, setSearchInput] = useState("");
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [modalState, setModalState] = useState(null);

  const { logout, token } = useContext(AuthContext);
  const navigate = useNavigate();

  const fetchCustomerList = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const data = await getCustomers({ search }, page, size);
      setCustomers(data.customers ?? []);
      setTotalPages(data.totalPages ?? 0);
      setTotalElements(data.totalElements ?? 0);
    } catch (error) {
      setError("Error al obtener la lista de clientes");
      console.log(error.message);
    } finally {
      setLoading(false);
    }
  }, [search, page, size]);

  useEffect(() => {
    if (!token) {
      navigate("/login", { replace: true });
      return;
    }
    // eslint-disable-next-line react-hooks/set-state-in-effect
    fetchCustomerList();
  }, [fetchCustomerList, navigate, token]);

  const closeModal = (shouldRefresh) => {
    setModalState(null);
    if (shouldRefresh) {
      fetchCustomerList();
    }
  };

  const handleSearchClick = () => {
    setSearch(searchInput);
    setPage(0);
  };

  const handleClearSearch = () => {
    setSearchInput("");
    setSearch("");
    setPage(0);
  };

  const handleSearchKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSearchClick();
    }
  };

  const handleSearchInputChange = (e) => {
    setSearchInput(e.target.value);
  };

  const handleSizeChange = (e) => {
    setSize(Number(e.target.value));
    setPage(0);
  };

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  const handleOnRowClick = async (customer) => {
    if (loadingCustomer) return;
    setLoadingCustomer(true);
    setError("");
    try {
      const customerFounded = await getCustomerById(customer.id);
      if (!customerFounded) {
        setError("Cliente no encontrado");
        return;
      }
      setError("");
      setModalState({ mode: "view", customer: customerFounded });
    } catch (error) {
      setError("Error al obtener el cliente");
      console.log(error.message);
    } finally {
      setLoadingCustomer(false);
    }
  };

  const handleOnDeleteClick = async (customer) => {
    if (loadingCustomer) return;
    setLoadingCustomer(true);
    setError("");

    try {
      const customerFounded = await getCustomerById(customer.id);
      if (!customerFounded) {
        setError("Cliente no encontrado");
        return;
      }
      setError("");
      setModalState({ mode: "delete", customer: customerFounded });
    } catch (error) {
      setError("Error al obtener el cliente");
      console.log(error.message);
    } finally {
      setLoadingCustomer(false);
    }
  };

  const handleCreateClientClick = () => {
    setModalState({ mode: "create", customer: null });
  };

  return (
    <div className="customer-page">
      <div className="customer-page-header">
        <div>
          <h1>Gestión de clientes</h1>
          <p>
            En este módulo podrás consultar, crear, editar y eliminar la
            información de tus clientes.
          </p>
          <Button onClick={handleLogout} text="Cerrar sesión" />
        </div>
        <div className="customer-page-toolbar">
          <div className="customer-page-filters">
            <FormInput
              name={"search"}
              placeholder={"Busca mediante alguna plabra clave"}
              value={searchInput}
              onChange={handleSearchInputChange}
              onKeyDown={handleSearchKeyDown}
            />

            {searchInput.trim() && (
              <Button
                onClick={handleClearSearch}
                text="Limpiar"
                variant="secondary"
              />
            )}

            <Button onClick={handleSearchClick} text="Buscar" />

            <div className="customer-page-actions">
              <Button onClick={handleCreateClientClick} text="Crear cliente" />
            </div>
          </div>
          <div className="customer-page-filters-size">
            <label htmlFor="size">Clientes por página:</label>
            <select id="size" value={size} onChange={handleSizeChange}>
              <option value={5}>5</option>
              <option value={10}>10</option>
              <option value={20}>20</option>
            </select>
          </div>
        </div>

        {error && <p className="customer-page__error">{error}</p>}
        {loadingCustomer && <Loader text="Cargando cliente..." />}

        <CustomerTable
          columns={COLUMNS}
          customers={customers}
          loading={loading}
          onRowClick={handleOnRowClick}
          onDeleteClick={handleOnDeleteClick}
        />
      </div>
      <div className="customer-page-pagination">
        <Button
          variant="secondary"
          disabled={page === 0}
          onClick={() => setPage((prevPage) => Math.max(prevPage - 1, 0))}
          text="Anterior"
        />
        <span>
          Página {page + 1} de {totalPages} ({totalElements} clientes
          {search ? " encontrados" : " en total"})
        </span>
        <Button
          variant="secondary"
          disabled={page + 1 >= totalPages}
          onClick={() =>
            setPage((prevPage) => Math.min(prevPage + 1, totalPages - 1))
          }
          text="Siguiente"
        />
      </div>
      {modalState && (
        <CustomerModal
          key={`${modalState.mode}-${modalState.customer?.id ?? "new"}`}
          mode={modalState.mode}
          customer={modalState.customer}
          onClose={closeModal}
        />
      )}
    </div>
  );
}
