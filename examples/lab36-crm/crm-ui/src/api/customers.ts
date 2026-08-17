import {ApiError} from './ApiError'
import {request} from './http'
import type {Customer, CustomerDraft, CustomerStatus} from '../types/customer'

type CustomerResponse = {
    customerId?: unknown
    id?: unknown
    fullName?: unknown
    email?: unknown
    status?: unknown
}

function parseCustomerStatus(value: unknown): CustomerStatus {
    if (value === 'PROSPECT' || value === 'ACTIVE' || value === 'CLOSED') return value
    throw new ApiError('CRM response contains an unsupported customer status', 'parse')
}

function toCustomer(response: CustomerResponse): Customer {
    const idCandidate = response.customerId ?? response.id
    if (typeof idCandidate !== 'string' || !idCandidate.trim()) {
        throw new ApiError('CRM response is missing customer id', 'parse')
    }
    if (typeof response.fullName !== 'string' || !response.fullName.trim()) {
        throw new ApiError('CRM response is missing customer fullName', 'parse')
    }
    if (typeof response.email !== 'string' || !response.email.trim()) {
        throw new ApiError('CRM response is missing customer email', 'parse')
    }

    return {
        customerId: idCandidate,
        fullName: response.fullName,
        email: response.email,
        status: parseCustomerStatus(response.status),
    }
}

function toDraftPayload(draft: CustomerDraft): CustomerDraft {
    return {
        fullName: draft.fullName.trim(),
        email: draft.email.trim(),
        status: draft.status,
    }
}

export const customersApi = {
    async list(signal?: AbortSignal): Promise<Customer[]> {
        const data = await request<CustomerResponse[]>('/api/customers', {signal})
        return data.map(toCustomer)
    },
    async create(draft: CustomerDraft): Promise<Customer> {
        const data = await request<CustomerResponse>('/api/customers', {
            method: 'POST',
            body: JSON.stringify(toDraftPayload(draft)),
        })
        return toCustomer(data)
    },
    async update(id: string, draft: CustomerDraft): Promise<Customer> {
        const data = await request<CustomerResponse>(`/api/customers/${encodeURIComponent(id)}`, {
            method: 'PUT',
            body: JSON.stringify(toDraftPayload(draft)),
        })
        return toCustomer(data)
    },
}
