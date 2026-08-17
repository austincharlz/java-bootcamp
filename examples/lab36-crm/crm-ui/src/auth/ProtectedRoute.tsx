import { Navigate, Outlet, useLocation } from 'react-router-dom'
import { useAuth } from './AuthContext'
import { LoadingPage } from '../pages/LoadingPage'

export function ProtectedRoute() {
    const { status } = useAuth()
    const location = useLocation()

    if (status === 'checking') return <LoadingPage />
    if (status === 'anonymous') {
        return <Navigate to="/login" replace state={{ from: location.pathname }} />
    }

    return <Outlet />
}