import type { CustomerDraft } from '../types/customer'

export type FieldErrors = Partial<Record<keyof CustomerDraft, string>>

export function validateCustomerDraft(draft: CustomerDraft): FieldErrors {
    const errors: FieldErrors = {}
    const trimmedFullName = draft.fullName.trim()
    const trimmedEmail = draft.email.trim()
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    const validStatuses = new Set(['PROSPECT', 'ACTIVE', 'CLOSED'])

    if (!trimmedFullName) {
        errors.fullName = 'Full name is required.'
    }

    if (!trimmedEmail) {
        errors.email = 'Email is required.'
    } else if (!emailPattern.test(trimmedEmail)) {
        errors.email = 'Email must be valid.'
    }

    if (!validStatuses.has(draft.status)) {
        errors.status = 'Status is required.'
    }

    return errors
}