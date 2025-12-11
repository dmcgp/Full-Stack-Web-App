import { useState } from 'react'
import { useAuthStore } from '../store/auth'

export default function Register() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const register = useAuthStore((s) => s.register)
  return (
    <div className="p-6 max-w-sm mx-auto">
      <h1 className="text-xl mb-4">Register</h1>
      <form className="flex flex-col gap-3" onSubmit={(e) => { e.preventDefault(); register(email, password) }}>
        <input className="border p-2" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
        <input className="border p-2" type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
        <button className="bg-green-600 text-white p-2 rounded" type="submit">Register</button>
      </form>
    </div>
  )
}
