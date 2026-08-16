import api from '../api/axiosConfig'

const BASE_URL = '/auth'

export async function login(username, password) {
    const response = await api.post(`${BASE_URL}/login`, {username, password});
    return response.data
}

export async function register(username, password, rol) {
    const response = await api.post(`${BASE_URL}/register`, {username, password, rol});
    return response.data
}