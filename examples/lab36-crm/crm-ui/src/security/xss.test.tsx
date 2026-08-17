import {render, screen} from '@testing-library/react'
import {beforeEach, describe, expect, it, vi} from 'vitest'
import App from '../App'
import {customersApi} from '../api/customers'

vi.mock('../api/customers', () => ({
    customersApi: {
        list: vi.fn(),
        create: vi.fn(),
        update: vi.fn(),
    },
}))

const listMock = vi.mocked(customersApi.list)

describe('XSS rendering', () => {
    beforeEach(() => {
        listMock.mockReset()
        listMock.mockResolvedValue([
            {
                customerId: 'CUS-1001',
                fullName: '<img onerror=alert(1)>',
                email: 'xss@example.com',
                status: 'ACTIVE',
            },
        ])
    })

    it('renders malicious customer names as text', async () => {
        render(<App/>)

        expect(await screen.findByText('<img onerror=alert(1)>')).toBeInTheDocument()
        expect(document.querySelector('img')).toBeNull()
    })
})
