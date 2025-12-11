import { useState } from 'react'
import { useAuthStore } from '../store/auth'

export default function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const login = useAuthStore((s) => s.login)
  const logout = useAuthStore((s) => s.logout)
  return (
    <div className="p-6 max-w-sm mx-auto">
      <h1 className="text-xl mb-4">Login</h1>
      <form className="flex flex-col gap-3" onSubmit={(e) => { e.preventDefault(); login(email, password) }}>
        <input className="border p-2" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
        <input className="border p-2" type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
        <button className="bg-blue-600 text-white p-2 rounded" type="submit">Login</button>
      </form>
      <button className="mt-2 text-sm underline" onClick={() => logout()}>Logout</button>
    </div>
  )
}
