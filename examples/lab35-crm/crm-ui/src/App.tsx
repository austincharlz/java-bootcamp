import {useCallback, useEffect, useState} from 'react'
import {ApiError} from './api/ApiError'
import {customersApi} from './api/customers'
import {CustomerForm} from './components/CustomerForm'
import type {Customer, CustomerDraft, UiMode} from './types/customer'
import type {FieldErrors} from './validation/customerValidation'
import {validateCustomerDraft} from './validation/customerValidation'

const emptyDraft = (): CustomerDraft => ({
    fullName: '',
    email: '',
    status: 'PROSPECT',
})

export default function App() {
    const [customers, setCustomers] = useState<Customer[]>([])
    const [loading, setLoading] = useState(true)
    const [loadError, setLoadError] = useState<ApiError | null>(null)
    const [query, setQuery] = useState('')
    const [mode, setMode] = useState<UiMode>({type: 'list'})
    const [draft, setDraft] = useState<CustomerDraft>(emptyDraft())
    const [saving, setSaving] = useState(false)
    const [errors, setErrors] = useState<FieldErrors>({})

    const normalizedQuery = query.trim().toLowerCase()
    const visible = customers.filter((customer) =>
        [customer.customerId, customer.fullName, customer.email].some((value) =>
            value.toLowerCase().includes(normalizedQuery),
        ),
    )
    const isEmptyState = !loading && !loadError && customers.length === 0
    const hasData = !loading && !loadError && customers.length > 0

    useEffect(() => {
        const originalTitle = document.title
        document.title = `CRM (${visible.length})`
        return () => {
            document.title = originalTitle
        }
    }, [visible.length])

    const loadCustomers = useCallback(async (signal?: AbortSignal) => {
        setLoading(true)
        setLoadError(null)

        try {
            const data = await customersApi.list(signal)
            if (signal?.aborted) return
            setCustomers(data)
        } catch (error) {
            if (error instanceof ApiError && error.kind === 'abort') return
            if (signal?.aborted) return
            setLoadError(error instanceof ApiError ? error : new ApiError('Unable to load customers', 'network'))
        } finally {
            if (signal?.aborted) return
            setLoading(false)
        }
    }, [])

    useEffect(() => {
        const controller = new AbortController()
        void loadCustomers(controller.signal)

        return () => {
            controller.abort()
        }
    }, [loadCustomers])

    function resetDraftAndErrors() {
        setDraft(emptyDraft())
        setErrors({})
    }

    function openCreate() {
        setMode({type: 'create'})
        resetDraftAndErrors()
    }

    function openEdit(customer: Customer) {
        setMode({type: 'edit', customerId: customer.customerId})
        setDraft({
            fullName: customer.fullName,
            email: customer.email,
            status: customer.status,
        })
        setErrors({})
    }

    function handleDraftChange(name: keyof CustomerDraft, value: string) {
        setDraft((previous) => ({...previous, [name]: value}))
        setErrors((previous) => {
            const next = {...previous}
            delete next[name]
            return next
        })
    }

    async function handleSubmit() {
        const nextErrors = validateCustomerDraft(draft)
        setErrors(nextErrors)
        if (Object.keys(nextErrors).length > 0) return

        setSaving(true)
        setErrors({})

        try {
            if (mode.type === 'create') {
                const created = await customersApi.create(draft)
                setCustomers((previous) => [...previous, created])
            } else if (mode.type === 'edit') {
                const updated = await customersApi.update(mode.customerId, draft)
                setCustomers((previous) =>
                    previous.map((customer) =>
                        customer.customerId === mode.customerId ? updated : customer,
                    ),
                )
            }

            setMode({type: 'list'})
            resetDraftAndErrors()
        } catch (error) {
            const apiError = error instanceof ApiError ? error : new ApiError('Unable to save customer', 'network')
            if (apiError.status === 400) {
                setErrors(apiError.fieldErrors ?? {form: apiError.message})
                return
            }

            setErrors({form: apiError.message})
        } finally {
            setSaving(false)
        }
    }

    function handleCancel() {
        setMode({type: 'list'})
        resetDraftAndErrors()
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

            {loading && <p role="status">Loading customers...</p>}
            {loadError && (
                <section>
                    <p role="alert">{loadError.message}</p>
                    <button type="button" onClick={() => void loadCustomers()}>
                        Retry
                    </button>
                </section>
            )}
            {isEmptyState && <p>No customers available.</p>}
            {hasData && visible.length === 0 && <p>No customers found.</p>}
            {hasData && visible.length > 0 && (
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