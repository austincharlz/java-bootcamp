import type {ReactNode} from 'react'

export interface AppLayoutProps {
    children: ReactNode
}

export function AppLayout({children}: AppLayoutProps) {
    return (
        <div className="app-layout">
            <header className="app-header">
                <h1>Customer Management Platform</h1>
            </header>
            <main className="app-main">{children}</main>
        </div>
    )
}
