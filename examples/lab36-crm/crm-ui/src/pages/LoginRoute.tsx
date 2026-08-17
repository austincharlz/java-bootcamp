import {useEffect, useMemo} from 'react'
import {useLocation, useNavigate} from 'react-router-dom'
import {useAuth} from '../auth/AuthContext'
import {tokenStore} from '../auth/tokenStore'
import {sanitizeReturnUrl} from '../auth/returnUrl'
import {LoginPage} from './LoginPage'

type LocationState = { from?: string }

export function LoginRoute() {
    const {status, login} = useAuth()
    const navigate = useNavigate()
    const location = useLocation()
    const state = location.state as LocationState | null
    const searchReturnUrl = new URLSearchParams(location.search).get('returnUrl')
    const returnUrl = useMemo(
        () => sanitizeReturnUrl(searchReturnUrl ?? state?.from),
        [searchReturnUrl, state?.from],
    )

    useEffect(() => {
        if (status === 'authenticated') {
            navigate(returnUrl, {replace: true})
        }
    }, [navigate, returnUrl, status])

    return (
        <LoginPage
            returnUrl={returnUrl}
            onLogin={async ({username, password}) => {
                if (!username || !password) {
                    throw new Error('Invalid credentials')
                }

                tokenStore.set(`demo-${username}`)
                login({id: username, displayName: username})
                navigate(returnUrl, {replace: true})
            }}
        />
    )
}
