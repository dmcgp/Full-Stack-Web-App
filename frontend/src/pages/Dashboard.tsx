import { useEffect, useState } from 'react'
import { api } from '../api'

type Task = { id: string; title: string; status: string }

export default function Dashboard() {
  const [tasks, setTasks] = useState<Task[]>([])
  const [title, setTitle] = useState('')

  const load = async () => {
    const res = await api.get<Task[]>('/tasks?status=OPEN')
    setTasks(res.data)
  }

  const add = async () => {
    if (!title.trim()) return
    await api.post<Task>('/tasks', { title })
    setTitle('')
    await load()
  }

  const done = async (id: string) => {
    await api.patch<Task>(`/tasks/${id}`, { status: 'DONE' })
    await load()
  }

  useEffect(() => { load() }, [])

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <h1 className="text-2xl mb-4">Today</h1>
      <div className="flex gap-2 mb-4">
        <input className="border p-2 flex-1" placeholder="Add a task" value={title} onChange={(e) => setTitle(e.target.value)} />
        <button className="bg-blue-600 text-white px-4 py-2 rounded" onClick={add}>Add</button>
      </div>
      <ul className="space-y-2">
        {tasks.map(t => (
          <li key={t.id} className="border p-3 rounded flex items-center justify-between">
            <span>{t.title}</span>
            <button className="bg-green-600 text-white px-3 py-1 rounded" onClick={() => done(t.id)}>Done</button>
          </li>
        ))}
        {tasks.length === 0 && (<li className="text-gray-500">No open tasks</li>)}
      </ul>
    </div>
  )
}
