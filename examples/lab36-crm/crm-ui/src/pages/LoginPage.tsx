import {type FormEvent, useMemo, useState} from 'react'
import {sanitizeReturnUrl} from '../auth/returnUrl'

type LoginCredentials = {
    username: string
    password: string
    returnUrl: string
}

type LoginPageProps = {
    returnUrl?: string
    onLogin: (credentials: LoginCredentials) => Promise<void> | void
}

export function LoginPage({returnUrl, onLogin}: LoginPageProps) {
    const safeReturnUrl = useMemo(() => sanitizeReturnUrl(returnUrl), [returnUrl])
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [submitting, setSubmitting] = useState(false)

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (submitting) return

        setSubmitting(true)
        setError('')

        try {
            await onLogin({
                username: username.trim(),
                password,
                returnUrl: safeReturnUrl,
            })
        } catch {
            setError('Invalid username or password')
        } finally {
            setSubmitting(false)
        }
    }

    return (
        <main>
            <h1>Sign in</h1>
            <p>Use your lab credentials. Production deployments must use HTTPS and HSTS.</p>
            <form noValidate onSubmit={handleSubmit}>
                <label htmlFor="username">Username</label>
                <input
                    id="username"
                    name="username"
                    autoComplete="username"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                />

                <label htmlFor="password">Password</label>
                <input
                    id="password"
                    name="password"
                    type="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                />

                {error && <p role="alert">{error}</p>}

                <button type="submit" disabled={submitting}>
                    {submitting ? 'Signing in...' : 'Sign in'}
                </button>
            </form>
        </main>
    )
}
