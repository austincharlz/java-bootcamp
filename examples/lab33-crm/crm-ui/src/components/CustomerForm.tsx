import type {ChangeEvent, FormEvent} from 'react'
import type {CustomerDraft, CustomerStatus} from '../types/customer'

export type CustomerDraftErrors = Partial<Record<keyof CustomerDraft, string>>

export interface CustomerFormProps {
    value: CustomerDraft
    errors: CustomerDraftErrors
    onChange: (value: CustomerDraft) => void
    onSubmit: () => void
    onCancel: () => void
}

const statusOptions: CustomerStatus[] = ['PROSPECT', 'ACTIVE', 'CLOSED']

export function CustomerForm({
                                 value,
                                 errors,
                                 onChange,
                                 onSubmit,
                                 onCancel,
                             }: CustomerFormProps) {
    const fullNameErrorId = 'full-name-error'
    const emailErrorId = 'email-error'
    const statusErrorId = 'status-error'

    const handleInputChange =
        (field: keyof CustomerDraft) =>
            (event: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
                onChange({...value, [field]: event.target.value})
            }

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        onSubmit()
    }

    return (
        <section className="customer-form-shell" aria-labelledby="customer-form-title">
            <h2 id="customer-form-title">Customer form</h2>
            <form className="customer-form" onSubmit={handleSubmit}>
                <div className="form-field">
                    <label htmlFor="fullName">Full name</label>
                    <input
                        id="fullName"
                        name="fullName"
                        type="text"
                        value={value.fullName}
                        onChange={handleInputChange('fullName')}
                        aria-invalid={Boolean(errors.fullName)}
                        aria-describedby={errors.fullName ? fullNameErrorId : undefined}
                    />
                    {errors.fullName && (
                        <p id={fullNameErrorId} role="alert" className="field-error">
                            {errors.fullName}
                        </p>
                    )}
                </div>

                <div className="form-field">
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        value={value.email}
                        onChange={handleInputChange('email')}
                        aria-invalid={Boolean(errors.email)}
                        aria-describedby={errors.email ? emailErrorId : undefined}
                    />
                    {errors.email && (
                        <p id={emailErrorId} role="alert" className="field-error">
                            {errors.email}
                        </p>
                    )}
                </div>

                <div className="form-field">
                    <label htmlFor="status">Status</label>
                    <select
                        id="status"
                        name="status"
                        value={value.status}
                        onChange={handleInputChange('status')}
                        aria-invalid={Boolean(errors.status)}
                        aria-describedby={errors.status ? statusErrorId : undefined}
                    >
                        {statusOptions.map((status) => (
                            <option key={status} value={status}>
                                {status}
                            </option>
                        ))}
                    </select>
                    {errors.status && (
                        <p id={statusErrorId} role="alert" className="field-error">
                            {errors.status}
                        </p>
                    )}
                </div>

                <div className="form-actions">
                    <button type="submit">Save</button>
                    <button type="button" onClick={onCancel}>
                        Cancel
                    </button>
                </div>
            </form>
        </section>
    )
}
