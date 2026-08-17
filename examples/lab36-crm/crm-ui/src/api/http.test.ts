import {beforeEach, describe, expect, it, vi} from 'vitest'
import {onAuthExpired} from '../auth/authEvents'
import {tokenStore} from '../auth/tokenStore'
import {request} from './http'

const fetchMock = vi.fn()

function jsonResponse(body: unknown, status: number): Response {
    return new Response(JSON.stringify(body), {
        status,
        headers: {'Content-Type': 'application/json'},
    })
}

describe('request auth behavior', () => {
    beforeEach(() => {
        fetchMock.mockReset()
        vi.stubGlobal('fetch', fetchMock)
        vi.unstubAllEnvs()
        vi.stubEnv('VITE_API_BASE_URL', 'http://localhost:8080')
        tokenStore.clear()
    })

    it('clears the token and emits expiry on 401', async () => {
        tokenStore.set('memory-token')
        const expired = vi.fn()
        const unsubscribe = onAuthExpired(expired)
        fetchMock.mockResolvedValue(jsonResponse({message: 'Unauthorized'}, 401))

        await expect(request('/api/customers')).rejects.toMatchObject({
            kind: 'http',
            status: 401,
        })

        expect(tokenStore.get()).toBeNull()
        expect(expired).toHaveBeenCalledTimes(1)
        unsubscribe()
    })

    it('does not clear the token on 403', async () => {
        tokenStore.set('memory-token')
        const expired = vi.fn()
        const unsubscribe = onAuthExpired(expired)
        fetchMock.mockResolvedValue(jsonResponse({message: 'Forbidden'}, 403))

        await expect(request('/api/customers')).rejects.toMatchObject({
            kind: 'http',
            status: 403,
        })

        expect(tokenStore.get()).toBe('memory-token')
        expect(expired).not.toHaveBeenCalled()
        unsubscribe()
    })
})
