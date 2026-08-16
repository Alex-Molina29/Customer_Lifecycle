import api from "../api/axiosConfig";

const BASE_URL = "/api/customers";

export async function createCustomer(payload) {
  const response = await api.post(`${BASE_URL}/`, [payload]);
  return response.data;
}

export async function getCustomers(filter = {}, page = 0, size = 10) {
  const filters = _cleanFilter(filter);

  const response = await api.get(`${BASE_URL}/`, {
    params: {
      page,
      size,
      ...filters,
    },
  });
  return response.data;
}

export async function getAllCustomers() {
  const response = await api.get(`${BASE_URL}/all`);
  return response.data;
}

export async function updateCustomer(id, payload) {
  const response = await api.put(`${BASE_URL}/${id}`, payload);
  return response.data;
}

export async function deleteCustomer(id) {
  const response = await api.delete(`${BASE_URL}/${id}`);
  return response.data;
}

export async function getCustomerById(id) {
    const response = await api.get(`${BASE_URL}/${id}`);
    return response.data;
}

export async function getCustomerByDocumento(documentNumber) {
    const response = await api.get(`${BASE_URL}/document/${documentNumber}`);
    return response.data;
}

function _cleanFilter(filters) {
  const cleanFilters = Object.fromEntries(
    Object.entries(filters).filter(
      ([, value]) => value !== "" && value != null,
    ),
  );
  return cleanFilters;
}
