import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from 'react'
import { onAuthExpired } from './authEvents'

export type User = {
    id: string
    displayName: string
}

export type AuthState =
    | { status: 'checking' }
    | { status: 'anonymous' }
    | { status: 'authenticated'; user: User }

type AuthContextValue = {
    status: AuthState['status']
    user: User | null
    login: (user: User) => void
    logout: () => void
}

const AuthContext = createContext<AuthContextValue | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {
    const [state, setState] = useState<AuthState>({ status: 'checking' })

    useEffect(() => {
        setState({ status: 'anonymous' })
    }, [])

    useEffect(() => onAuthExpired(() => setState({ status: 'anonymous' })), [])

    const value = useMemo<AuthContextValue>(
        () => ({
            status: state.status,
            user: state.status === 'authenticated' ? state.user : null,
            login: (user: User) => {
                setState({ status: 'authenticated', user })
            },
            logout: () => {
                setState({ status: 'anonymous' })
            },
        }),
        [state],
    )

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
    const ctx = useContext(AuthContext)
    if (!ctx) throw new Error('useAuth requires AuthProvider')
    return ctx
}
