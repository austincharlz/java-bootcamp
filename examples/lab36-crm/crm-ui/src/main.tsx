import React from 'react'
import ReactDOM from 'react-dom/client'
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import {AuthProvider} from './auth/AuthContext'
import {ProtectedRoute} from './auth/ProtectedRoute'
import {CustomersPage} from './pages/CustomersPage'
import {LoginRoute} from './pages/LoginRoute'

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<LoginRoute/>}/>
                    <Route element={<ProtectedRoute/>}>
                        <Route path="*" element={<CustomersPage/>}/>
                    </Route>
                    <Route path="*" element={<Navigate to="/" replace/>}/>
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    </React.StrictMode>,
)