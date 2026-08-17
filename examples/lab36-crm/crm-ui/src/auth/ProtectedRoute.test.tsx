import {render, screen} from '@testing-library/react'
import {MemoryRouter, Route, Routes} from 'react-router-dom'
import {describe, expect, it} from 'vitest'
import {AuthProvider} from './AuthContext'
import {ProtectedRoute} from './ProtectedRoute'

describe('ProtectedRoute', () => {
    it('redirects anonymous users to login', async () => {
        render(
            <AuthProvider>
                <MemoryRouter initialEntries={['/customers']}>
                    <Routes>
                        <Route element={<ProtectedRoute/>}>
                            <Route path="/customers" element={<p>Protected page</p>}/>
                        </Route>
                        <Route path="/login" element={<p>Login page</p>}/>
                    </Routes>
                </MemoryRouter>
            </AuthProvider>,
        )

        expect(await screen.findByText('Login page')).toBeInTheDocument()
    })
})
