import { useEffect, useState } from 'react'
import { api } from '../api'

type Task = { id: string; title: string; status: string }

export default function Completed() {
  const [tasks, setTasks] = useState<Task[]>([])

  useEffect(() => {
    api.get<Task[]>('/tasks?status=DONE').then(res => setTasks(res.data))
  }, [])

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <h1 className="text-2xl mb-4">Completed</h1>
      <ul className="space-y-2">
        {tasks.map(t => (
          <li key={t.id} className="border p-3 rounded">{t.title}</li>
        ))}
        {tasks.length === 0 && (<li className="text-gray-500">No completed tasks</li>)}
      </ul>
    </div>
  )
}
