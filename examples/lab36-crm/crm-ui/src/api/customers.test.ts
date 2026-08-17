import {customersApi} from './customers'
import {request} from './http'
import {tokenStore} from '../auth/tokenStore'

const fetchMock = vi.fn()

function jsonResponse(body: unknown, status: number): Response {
    return new Response(JSON.stringify(body), {
        status,
        headers: {'Content-Type': 'application/json'},
    })
}

describe('customersApi', () => {
    beforeEach(() => {
        fetchMock.mockReset()
        vi.stubGlobal('fetch', fetchMock)
        vi.unstubAllEnvs()
        vi.stubEnv('VITE_API_BASE_URL', 'http://localhost:8080')
        tokenStore.clear()
    })

    it('returns list from 200 response', async () => {
        tokenStore.set('memory-token')
        fetchMock.mockResolvedValue(
            jsonResponse(
                [
                    {
                        customerId: 'CUS-1001',
                        fullName: 'Amina Khan',
                        email: 'amina.khan@example.com',
                        status: 'ACTIVE',
                    },
                    {
                        customerId: 'CUS-1002',
                        fullName: 'Ravi Singh',
                        email: 'ravi.singh@example.com',
                        status: 'PROSPECT',
                    },
                ],
                200,
            ),
        )

        const customers = await customersApi.list()

        expect(customers).toHaveLength(2)
        expect(customers[0].customerId).toBe('CUS-1001')
        expect(fetchMock).toHaveBeenCalledWith('http://localhost:8080/api/customers', expect.any(Object))
        const [, init] = fetchMock.mock.calls[0]
        const headers = new Headers(init?.headers)
        expect(headers.get('Content-Type')).toBe('application/json')
        expect(headers.get('X-Correlation-Id')).toBe('lab-request-001')
        expect(headers.get('Authorization')).toBe('Bearer memory-token')
    })

    it('does not attach Authorization off the CRM origin', async () => {
        tokenStore.set('memory-token')
        fetchMock.mockResolvedValue(jsonResponse({ok: true}, 200))

        await request('https://cdn.example.com/asset.json')

        const [, init] = fetchMock.mock.calls[0]
        const headers = new Headers(init?.headers)
        expect(headers.get('Authorization')).toBeNull()
        expect(headers.get('X-Correlation-Id')).toBeNull()
    })

    it('returns created customer from 201 response', async () => {
        fetchMock.mockResolvedValue(
            jsonResponse(
                {
                    id: 'CUS-2001',
                    fullName: 'Jordan Lee',
                    email: 'jordan.lee@example.com',
                    status: 'ACTIVE',
                },
                201,
            ),
        )

        const created = await customersApi.create({
            fullName: 'Jordan Lee',
            email: 'jordan.lee@example.com',
            status: 'ACTIVE',
        })

        expect(created.customerId).toBe('CUS-2001')
        expect(created.fullName).toBe('Jordan Lee')
    })

    it('maps 400 field errors to ApiError', async () => {
        fetchMock.mockResolvedValue(
            jsonResponse(
                {
                    message: 'Validation failed',
                    fieldErrors: {email: 'Email must be valid.'},
                },
                400,
            ),
        )

        await expect(
            customersApi.create({
                fullName: 'Jordan Lee',
                email: 'bad-email',
                status: 'ACTIVE',
            }),
        ).rejects.toMatchObject({
            kind: 'http',
            status: 400,
            fieldErrors: {email: 'Email must be valid.'},
        })
    })

    it('maps 500 server responses to ApiError', async () => {
        fetchMock.mockResolvedValue(jsonResponse({message: 'Internal server error'}, 500))

        await expect(customersApi.list()).rejects.toMatchObject({
            kind: 'http',
            status: 500,
            message: 'Internal server error',
        })
    })

    it('maps fetch rejection to network ApiError', async () => {
        fetchMock.mockRejectedValue(new Error('connect ECONNREFUSED'))

        await expect(customersApi.list()).rejects.toMatchObject({
            kind: 'network',
            message: 'Cannot reach the CRM service',
        })
    })

    it('maps aborted fetch to abort ApiError', async () => {
        fetchMock.mockRejectedValue(new DOMException('The operation was aborted', 'AbortError'))

        await expect(customersApi.list()).rejects.toMatchObject({
            kind: 'abort',
            message: 'Request cancelled',
        })
    })
})
