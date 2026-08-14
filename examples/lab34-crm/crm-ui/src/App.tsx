import { useEffect, useState } from 'react'
import { CustomerForm } from './components/CustomerForm'
import { seedCustomers } from './data/seedCustomers'
import type { Customer, CustomerDraft, UiMode } from './types/customer'
import { validateCustomerDraft } from './validation/customerValidation'
import type { FieldErrors } from './validation/customerValidation'

const emptyDraft = (): CustomerDraft => ({
    fullName: '',
    email: '',
    status: 'PROSPECT',
})

export default function App() {
    const [customers, setCustomers] = useState<Customer[]>(seedCustomers)
    const [query, setQuery] = useState('')
    const [mode, setMode] = useState<UiMode>({ type: 'list' })
    const [draft, setDraft] = useState<CustomerDraft>(emptyDraft())
    const [saving, setSaving] = useState(false)
    const [errors, setErrors] = useState<FieldErrors>({})

    const normalizedQuery = query.trim().toLowerCase()
    const visible = customers.filter((customer) =>
        [customer.customerId, customer.fullName, customer.email].some((value) =>
            value.toLowerCase().includes(normalizedQuery),
        ),
    )

    useEffect(() => {
        const originalTitle = document.title
        document.title = `CRM (${visible.length})`
        return () => {
            document.title = originalTitle
        }
    }, [visible.length])

    function resetDraftAndErrors() {
        setDraft(emptyDraft())
        setErrors({})
    }

    function openCreate() {
        setMode({ type: 'create' })
        resetDraftAndErrors()
    }

    function openEdit(customer: Customer) {
        setMode({ type: 'edit', customerId: customer.customerId })
        setDraft({
            fullName: customer.fullName,
            email: customer.email,
            status: customer.status,
        })
        setErrors({})
    }

    function handleDraftChange(name: keyof CustomerDraft, value: string) {
        setDraft((previous) => ({ ...previous, [name]: value }))
        setErrors((previous) => {
            const next = { ...previous }
            delete next[name]
            return next
        })
    }

    function handleSubmit() {
        const nextErrors = validateCustomerDraft(draft)
        setErrors(nextErrors)
        if (Object.keys(nextErrors).length > 0) return

        setSaving(true)
        if (mode.type === 'create') {
            setCustomers((previous) => [
                ...previous,
                {
                    customerId: crypto.randomUUID(),
                    fullName: draft.fullName.trim(),
                    email: draft.email.trim(),
                    status: draft.status,
                },
            ])
            console.log('create', 'lab-request-001')
        } else if (mode.type === 'edit') {
            setCustomers((previous) =>
                previous.map((customer) =>
                    customer.customerId === mode.customerId
                        ? {
                              ...customer,
                              fullName: draft.fullName.trim(),
                              email: draft.email.trim(),
                              status: draft.status,
                              customerId: customer.customerId,
                          }
                        : customer,
                ),
            )
            console.log('update', 'lab-request-001')
        }

        setMode({ type: 'list' })
        resetDraftAndErrors()
        setSaving(false)
    }

    function handleCancel() {
        setMode({ type: 'list' })
        resetDraftAndErrors()
        console.log('cancel', 'lab-request-001')
    }

    return (
        <main>
            <h1>Customer Management Platform</h1>
            <label htmlFor="searchCustomers">Search for customers</label>
            <input
                id="searchCustomers"
                type="search"
                aria-label="Search customers"
                value={query}
                onChange={(event) => setQuery(event.target.value)}
                placeholder="Search by id, name, or email"
            />
            <button type="button" onClick={openCreate}>
                New customer
            </button>

            {visible.length === 0 && <p>No customers found.</p>}
            {visible.length > 0 && (
                <ul>
                    {visible.map((customer) => (
                        <li key={customer.customerId}>
                            <p>{customer.fullName}</p>
                            <p>{customer.email}</p>
                            <p>{customer.status}</p>
                            <button type="button" onClick={() => openEdit(customer)}>
                                Edit {customer.fullName}
                            </button>
                        </li>
                    ))}
                </ul>
            )}

            {mode.type !== 'list' && (
                <CustomerForm
                    draft={draft}
                    errors={errors}
                    saving={saving}
                    onFieldChange={handleDraftChange}
                    onSubmit={handleSubmit}
                    onCancel={handleCancel}
                />
            )}
        </main>
    )
}