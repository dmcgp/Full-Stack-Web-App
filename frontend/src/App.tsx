import { Link, Route, Routes, Navigate } from 'react-router-dom'
import { useAuthStore } from './store/auth'
import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'
import Completed from './pages/Completed'

function Protected({ children }: { children: JSX.Element }) {
  const isAuth = useAuthStore((s) => !!s.accessToken)
  return isAuth ? children : <Navigate to="/login" replace />
}

export default function App() {
  return (
    <div className="min-h-screen">
      <nav className="p-4 border-b flex gap-4">
        <Link to="/">Home</Link>
        <Link to="/projects">Today</Link>
        <Link to="/completed">Completed</Link>
      </nav>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/projects" element={<Protected><Dashboard /></Protected>} />
        <Route path="/completed" element={<Protected><Completed /></Protected>} />
        <Route path="/" element={<div className="p-6">Welcome to Task Manager</div>} />
      </Routes>
    </div>
  )
}
