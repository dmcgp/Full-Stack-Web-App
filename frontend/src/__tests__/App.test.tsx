import { render, screen } from '@testing-library/react'
import { BrowserRouter } from 'react-router-dom'
import App from '../App'

test('renders home text', () => {
  render(<BrowserRouter><App /></BrowserRouter>)
  expect(screen.getByText(/Welcome to Task Manager/i)).toBeInTheDocument()
})
