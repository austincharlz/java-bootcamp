import {render, screen} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'
import {ApiError} from './api/ApiError'
import {customersApi} from './api/customers'

vi.mock('./api/customers', () => ({
    customersApi: {
        list: vi.fn(),
        create: vi.fn(),
        update: vi.fn(),
    },
}))

const listMock = vi.mocked(customersApi.list)
const createMock = vi.mocked(customersApi.create)
const updateMock = vi.mocked(customersApi.update)

const serverCustomers = [
    {customerId: 'CUS-1001', fullName: 'Amina Khan', email: 'amina.khan@example.com', status: 'ACTIVE' as const},
    {customerId: 'CUS-1002', fullName: 'Ravi Singh', email: 'ravi.singh@example.com', status: 'PROSPECT' as const},
]

describe('App flows', () => {
    beforeEach(() => {
        listMock.mockReset()
        createMock.mockReset()
        updateMock.mockReset()
        listMock.mockResolvedValue(serverCustomers)
    })

    it('shows customers from API', async () => {
        render(<App/>)
        expect(await screen.findByText('amina.khan@example.com')).toBeInTheDocument()
        expect(screen.getByText('ravi.singh@example.com')).toBeInTheDocument()
    })

    it('filters customers by search query', async () => {
        const user = userEvent.setup()
        render(<App/>)

        expect(await screen.findByText('amina.khan@example.com')).toBeInTheDocument()
        await user.type(screen.getByRole('searchbox', {name: /search customers/i}), 'amina')

        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
        expect(screen.queryByText('ravi.singh@example.com')).not.toBeInTheDocument()
    })

    it('creates a valid customer once', async () => {
        const user = userEvent.setup()
        createMock.mockResolvedValue({
            customerId: 'CUS-2001',
            fullName: 'Jordan Lee',
            email: 'jordan.lee@example.com',
            status: 'ACTIVE',
        })
        render(<App/>)

        expect(await screen.findByText('amina.khan@example.com')).toBeInTheDocument()
        await user.click(screen.getByRole('button', {name: /new customer/i}))
        await user.type(screen.getByLabelText(/full name/i), 'Jordan Lee')
        await user.type(screen.getByLabelText(/email/i), 'jordan.lee@example.com')
        await user.selectOptions(screen.getByLabelText(/status/i), 'ACTIVE')
        await user.click(screen.getByRole('button', {name: /^save$/i}))

        expect(screen.getByText('Jordan Lee')).toBeInTheDocument()
        expect(screen.getAllByText('Jordan Lee')).toHaveLength(1)
    })

    it('shows validation errors and does not create invalid customer', async () => {
        const user = userEvent.setup()
        render(<App/>)

        await user.click(screen.getByRole('button', {name: /new customer/i}))
        await user.type(screen.getByLabelText(/email/i), 'bad-email')
        await user.click(screen.getByRole('button', {name: /^save$/i}))

        expect(await screen.findByText('Full name is required.')).toBeInTheDocument()
        expect(await screen.findByText('Email must be valid.')).toBeInTheDocument()
        expect(screen.queryByText('bad-email')).not.toBeInTheDocument()
    })

    it('edits Ravi and preserves other customer data', async () => {
        const user = userEvent.setup()
        updateMock.mockResolvedValue({
            customerId: 'CUS-1002',
            fullName: 'Ravi S.',
            email: 'ravi.singh@example.com',
            status: 'PROSPECT',
        })
        render(<App/>)

        expect(await screen.findByText('ravi.singh@example.com')).toBeInTheDocument()
        await user.click(screen.getByRole('button', {name: /edit ravi singh/i}))
        await user.clear(screen.getByLabelText(/full name/i))
        await user.type(screen.getByLabelText(/full name/i), 'Ravi S.')
        await user.click(screen.getByRole('button', {name: /^save$/i}))

        expect(screen.getByRole('button', {name: /edit ravi s\./i})).toBeInTheDocument()
        expect(screen.queryByText('Ravi Singh')).not.toBeInTheDocument()
        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
    })

    it('cancel create discards draft and keeps list unchanged', async () => {
        const user = userEvent.setup()
        render(<App/>)

        expect(await screen.findByText('amina.khan@example.com')).toBeInTheDocument()
        await user.click(screen.getByRole('button', {name: /new customer/i}))
        await user.type(screen.getByLabelText(/full name/i), 'Should Not Save')
        await user.type(screen.getByLabelText(/email/i), 'should.not.save@example.com')
        await user.click(screen.getByRole('button', {name: /cancel/i}))

        expect(screen.queryByText('Should Not Save')).not.toBeInTheDocument()
        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
        expect(screen.getByText('ravi.singh@example.com')).toBeInTheDocument()
    })

    it('shows empty state when search has no matches', async () => {
        const user = userEvent.setup()
        render(<App/>)

        expect(await screen.findByText('ravi.singh@example.com')).toBeInTheDocument()
        await user.type(screen.getByRole('searchbox', {name: /search customers/i}), 'missing')

        expect(screen.getByText(/no customers found\./i)).toBeInTheDocument()
    })

    it('shows error and retries list loading', async () => {
        const user = userEvent.setup()
        listMock
            .mockRejectedValueOnce(new ApiError('Cannot reach the CRM service', 'network'))
            .mockResolvedValueOnce(serverCustomers)
        render(<App/>)

        expect(await screen.findByRole('alert')).toHaveTextContent('Cannot reach the CRM service')
        await user.click(screen.getByRole('button', {name: /retry/i}))

        expect(await screen.findByText('amina.khan@example.com')).toBeInTheDocument()
    })

    it('maps backend 400 field errors to the form', async () => {
        const user = userEvent.setup()
        createMock.mockRejectedValue(
            new ApiError('Validation failed', 'http', 400, {email: 'Email must be a valid address.'}),
        )
        render(<App/>)

        expect(await screen.findByText('amina.khan@example.com')).toBeInTheDocument()
        await user.click(screen.getByRole('button', {name: /new customer/i}))
        await user.type(screen.getByLabelText(/full name/i), 'Jordan Lee')
        await user.type(screen.getByLabelText(/email/i), 'jordan.lee@example.com')
        await user.click(screen.getByRole('button', {name: /^save$/i}))

        expect(await screen.findByText('Email must be a valid address.')).toBeInTheDocument()
        expect(screen.queryByText('Jordan Lee')).not.toBeInTheDocument()
    })

    it('disables save while create is in flight', async () => {
        const user = userEvent.setup()

        let resolveCreate: ((value: (typeof serverCustomers)[number]) => void) | undefined
        createMock.mockImplementation(
            () =>
                new Promise((resolve) => {
                    resolveCreate = resolve
                }),
        )

        render(<App/>)

        expect(await screen.findByText('ravi.singh@example.com')).toBeInTheDocument()
        await user.click(screen.getByRole('button', {name: /new customer/i}))
        await user.type(screen.getByLabelText(/full name/i), 'Jordan Lee')
        await user.type(screen.getByLabelText(/email/i), 'jordan.lee@example.com')
        await user.click(screen.getByRole('button', {name: /^save$/i}))

        expect(screen.getByRole('button', {name: /^save$/i})).toBeDisabled()
        expect(createMock).toHaveBeenCalledTimes(1)

        resolveCreate?.({
            customerId: 'CUS-2001',
            fullName: 'Jordan Lee',
            email: 'jordan.lee@example.com',
            status: 'ACTIVE',
        })

        expect(await screen.findByText('Jordan Lee')).toBeInTheDocument()
        expect(createMock).toHaveBeenCalledTimes(1)
    })
})