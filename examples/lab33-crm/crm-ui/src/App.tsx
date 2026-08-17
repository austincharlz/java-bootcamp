import {useState} from 'react'
import './App.css'
import {AppLayout} from './components/AppLayout'
import {type CustomerDraftErrors, CustomerForm} from './components/CustomerForm'
import {CustomerList} from './components/CustomerList'
import {CustomerToolbar} from './components/CustomerToolbar'
import {ErrorState} from './components/ErrorState'
import {LoadingState} from './components/LoadingState'
import {seedCustomers} from './data/seedCustomers'
import type {CustomerDraft} from './types/customer'

const emptyDraft: CustomerDraft = {
    fullName: '',
    email: '',
    status: 'PROSPECT',
}

function App() {
    const [draft, setDraft] = useState<CustomerDraft>(emptyDraft)
    const [errors, setErrors] = useState<CustomerDraftErrors>({})
    const showLoading = false
    const showError = false

    const handleAdd = () => {
        console.log('add', 'lab-request-001')
        setDraft(emptyDraft)
        setErrors({})
    }

    const handleSubmit = () => {
        const nextErrors: CustomerDraftErrors = {}
        if (!draft.fullName.trim()) {
            nextErrors.fullName = 'Full name is required.'
        }
        if (!draft.email.trim()) {
            nextErrors.email = 'Email is required.'
        }
        if (Object.keys(nextErrors).length > 0) {
            setErrors(nextErrors)
            return
        }
        setErrors({})
        console.log('save', draft, 'lab-request-001')
    }

    const handleCancel = () => {
        setDraft(emptyDraft)
        setErrors({})
        console.log('cancel', 'lab-request-001')
    }

    const content = showLoading ? (
        <LoadingState/>
    ) : showError ? (
        <ErrorState
            message="Unable to load customers right now."
            onRetry={() => console.log('retry', 'lab-request-001')}
        />
    ) : (
        <CustomerList
            customers={seedCustomers}
            onEdit={(id) => console.log('edit', id, 'lab-request-001')}
        />
    )

    return (
        <AppLayout>
            <CustomerToolbar onAdd={handleAdd}/>
            {content}
            <CustomerForm
                value={draft}
                errors={errors}
                onChange={setDraft}
                onSubmit={handleSubmit}
                onCancel={handleCancel}
            />
        </AppLayout>
    )
}

export default App
