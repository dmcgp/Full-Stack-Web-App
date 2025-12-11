import { create } from 'zustand'
import { api } from '../api'

interface AuthState {
  accessToken: string | null
  refreshToken: string | null
  login: (email: string, password: string) => Promise<void>
  register: (email: string, password: string) => Promise<void>
  logout: () => void
}

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: null,
  refreshToken: null,
  login: async (email, password) => {
    const res = await api.post('/auth/login', { email, password })
    const { accessToken, refreshToken } = res.data
    localStorage.setItem('accessToken', accessToken)
    localStorage.setItem('refreshToken', refreshToken)
    set({ accessToken, refreshToken })
  },
  register: async (email, password) => {
    await api.post('/auth/register', { email, password })
  },
  logout: () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    set({ accessToken: null, refreshToken: null })
  }
}))
