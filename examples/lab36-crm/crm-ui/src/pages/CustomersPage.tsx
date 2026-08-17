import App from '../App'
import {useAuth} from '../auth/AuthContext'
import {tokenStore} from '../auth/tokenStore'

export function CustomersPage() {
    const {logout} = useAuth()

    function handleLogout() {
        tokenStore.clear()
        logout()
    }

    return (
        <section>
            <button type="button" onClick={handleLogout}>
                Logout
            </button>
            <App/>
        </section>
    )
}
