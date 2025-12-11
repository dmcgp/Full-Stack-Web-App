import axios from 'axios'
import { useAuthStore } from './store/auth'

export const api = axios.create({ baseURL: 'http://localhost:8080/api' })

api.interceptors.request.use((config) => {
  const token = useAuthStore.getState().accessToken || localStorage.getItem('accessToken')
  if (token) {
    const headersAny = config.headers as any
    if (headersAny && typeof headersAny.set === 'function') {
      headersAny.set('Authorization', `Bearer ${token}`)
    } else {
      config.headers = { ...(config.headers as any || {}), Authorization: `Bearer ${token}` }
    }
  }
  return config
})

api.interceptors.response.use(undefined, async (error) => {
  const original = error.config as any
  if (error.response?.status === 401 && !original._retry) {
    original._retry = true
    const refreshToken = useAuthStore.getState().refreshToken || localStorage.getItem('refreshToken')
    if (refreshToken) {
      try {
        const res = await api.post(`/auth/refresh?refreshToken=${encodeURIComponent(refreshToken)}`)
        const accessToken = res.data.accessToken
        localStorage.setItem('accessToken', accessToken)
        useAuthStore.setState({ accessToken })
        const originalHeadersAny = original.headers as any
        if (originalHeadersAny && typeof originalHeadersAny.set === 'function') {
          originalHeadersAny.set('Authorization', `Bearer ${accessToken}`)
        } else {
          original.headers = { ...(original.headers as any || {}), Authorization: `Bearer ${accessToken}` }
        }
        return api(original)
      } catch (_) {
        useAuthStore.getState().logout()
      }
    }
  }
  return Promise.reject(error)
})
