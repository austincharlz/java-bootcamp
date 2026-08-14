import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'

describe('App flows', () => {
    it('shows seed customers', () => {
        render(<App />)
        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
        expect(screen.getByText('ravi.singh@example.com')).toBeInTheDocument()
    })

    it('filters customers by search query', async () => {
        const user = userEvent.setup()
        render(<App />)

        await user.type(screen.getByRole('searchbox', { name: /search customers/i }), 'amina')

        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
        expect(screen.queryByText('ravi.singh@example.com')).not.toBeInTheDocument()
    })

    it('creates a valid customer once', async () => {
        const user = userEvent.setup()
        render(<App />)

        await user.click(screen.getByRole('button', { name: /new customer/i }))
        await user.type(screen.getByLabelText(/full name/i), 'Jordan Lee')
        await user.type(screen.getByLabelText(/email/i), 'jordan.lee@example.com')
        await user.selectOptions(screen.getByLabelText(/status/i), 'ACTIVE')
        await user.click(screen.getByRole('button', { name: /^save$/i }))

        expect(screen.getByText('Jordan Lee')).toBeInTheDocument()
        expect(screen.getAllByText('Jordan Lee')).toHaveLength(1)
    })

    it('shows validation errors and does not create invalid customer', async () => {
        const user = userEvent.setup()
        render(<App />)

        await user.click(screen.getByRole('button', { name: /new customer/i }))
        await user.type(screen.getByLabelText(/email/i), 'bad-email')
        await user.click(screen.getByRole('button', { name: /^save$/i }))

        expect(await screen.findByText('Full name is required.')).toBeInTheDocument()
        expect(await screen.findByText('Email must be valid.')).toBeInTheDocument()
        expect(screen.queryByText('bad-email')).not.toBeInTheDocument()
    })

    it('edits Ravi and preserves other customer data', async () => {
        const user = userEvent.setup()
        render(<App />)

        await user.click(screen.getByRole('button', { name: /edit ravi singh/i }))
        await user.clear(screen.getByLabelText(/full name/i))
        await user.type(screen.getByLabelText(/full name/i), 'Ravi S.')
        await user.click(screen.getByRole('button', { name: /^save$/i }))

        expect(screen.getByRole('button', { name: /edit ravi s\./i })).toBeInTheDocument()
        expect(screen.queryByText('Ravi Singh')).not.toBeInTheDocument()
        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
    })

    it('cancel create discards draft and keeps list unchanged', async () => {
        const user = userEvent.setup()
        render(<App />)

        await user.click(screen.getByRole('button', { name: /new customer/i }))
        await user.type(screen.getByLabelText(/full name/i), 'Should Not Save')
        await user.type(screen.getByLabelText(/email/i), 'should.not.save@example.com')
        await user.click(screen.getByRole('button', { name: /cancel/i }))

        expect(screen.queryByText('Should Not Save')).not.toBeInTheDocument()
        expect(screen.getByText('amina.khan@example.com')).toBeInTheDocument()
        expect(screen.getByText('ravi.singh@example.com')).toBeInTheDocument()
    })

    it('shows empty state when search has no matches', async () => {
        const user = userEvent.setup()
        render(<App />)

        await user.type(screen.getByRole('searchbox', { name: /search customers/i }), 'missing')

        expect(screen.getByText(/no customers found\./i)).toBeInTheDocument()
    })
})