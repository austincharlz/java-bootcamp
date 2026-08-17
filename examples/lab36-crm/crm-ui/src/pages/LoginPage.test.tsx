import {render, screen} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import {describe, expect, it, vi} from 'vitest'
import {LoginPage} from './LoginPage'
import {sanitizeReturnUrl} from '../auth/returnUrl'

describe('sanitizeReturnUrl', () => {
    it('keeps internal paths', () => {
        expect(sanitizeReturnUrl('/customers?tab=active')).toBe('/customers?tab=active')
    })

    it('rejects external return urls', () => {
        expect(sanitizeReturnUrl('https://evil.example.com')).toBe('/')
        expect(sanitizeReturnUrl('//evil.example.com')).toBe('/')
        expect(sanitizeReturnUrl('customers')).toBe('/')
    })
})

describe('LoginPage', () => {
    it('shows a generic error on failed sign-in', async () => {
        const user = userEvent.setup()
        const onLogin = vi.fn().mockRejectedValue(new Error('bad credentials'))

        render(<LoginPage onLogin={onLogin} returnUrl="/customers"/>)

        await user.type(screen.getByLabelText(/username/i), 'demo')
        await user.type(screen.getByLabelText(/password/i), 'secret')
        await user.click(screen.getByRole('button', {name: /sign in/i}))

        expect(await screen.findByRole('alert')).toHaveTextContent('Invalid username or password')
        expect(onLogin).toHaveBeenCalledWith({
            username: 'demo',
            password: 'secret',
            returnUrl: '/customers',
        })
    })

    it('disables repeat submit while the sign-in request is in flight', async () => {
        const user = userEvent.setup()
        let resolveLogin: (() => void) | undefined
        const onLogin = vi.fn(
            () =>
                new Promise<void>((resolve) => {
                    resolveLogin = resolve
                }),
        )

        render(<LoginPage onLogin={onLogin}/>)

        await user.type(screen.getByLabelText(/username/i), 'demo')
        await user.type(screen.getByLabelText(/password/i), 'secret')
        await user.click(screen.getByRole('button', {name: /sign in/i}))

        expect(screen.getByRole('button', {name: /signing in\.\.\./i})).toBeDisabled()

        resolveLogin?.()

        expect(await screen.findByRole('button', {name: /sign in/i})).toBeEnabled()
    })
})
