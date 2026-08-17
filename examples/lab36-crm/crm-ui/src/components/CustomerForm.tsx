import type {CustomerDraft} from '../types/customer'
import type {FieldErrors} from '../validation/customerValidation'

export function CustomerForm({
                                 draft,
                                 errors,
                                 saving,
                                 onFieldChange,
                                 onSubmit,
                                 onCancel,
                             }: {
    draft: CustomerDraft
    errors: FieldErrors
    saving: boolean
    onFieldChange: (name: keyof CustomerDraft, value: string) => void
    onSubmit: () => void
    onCancel: () => void
}) {
    return (
        <form
            noValidate
            onSubmit={(e) => {
                e.preventDefault()
                onSubmit()
            }}
        >
            <label htmlFor="fullName">Full name</label>
            <input
                id="fullName"
                name="fullName"
                value={draft.fullName}
                onChange={(e) => onFieldChange('fullName', e.target.value)}
            />
            {errors.fullName && <p role="alert">{errors.fullName}</p>}

            <label htmlFor="email">Email</label>
            <input
                id="email"
                name="email"
                type="email"
                value={draft.email}
                onChange={(e) => onFieldChange('email', e.target.value)}
            />
            {errors.email && <p role="alert">{errors.email}</p>}

            <label htmlFor="status">Status</label>
            <select
                id="status"
                name="status"
                value={draft.status}
                onChange={(e) => onFieldChange('status', e.target.value)}
            >
                <option value="PROSPECT">PROSPECT</option>
                <option value="ACTIVE">ACTIVE</option>
                <option value="CLOSED">CLOSED</option>
            </select>
            {errors.status && <p role="alert">{errors.status}</p>}
            {errors.form && <p role="alert">{errors.form}</p>}
            <button type="submit" disabled={saving}>
                Save
            </button>
            <button type="button" onClick={onCancel}>
                Cancel
            </button>
        </form>
    )
}