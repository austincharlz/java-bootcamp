import {StatusBadge} from './StatusBadge'
import type {Customer} from '../types/customer'

export interface CustomerCardProps {
    customer: Customer
    onEdit: (customerId: string) => void
}

export function CustomerCard({customer, onEdit}: CustomerCardProps) {
    const headingId = `customer-name-${customer.customerId}`

    return (
        <article className="customer-card" aria-labelledby={headingId}>
            <h2 id={headingId}>{customer.fullName}</h2>
            <p className="customer-id">{customer.customerId}</p>
            <StatusBadge status={customer.status}/>
            <a href={`mailto:${customer.email}`}>{customer.email}</a>
            <button type="button" onClick={() => onEdit(customer.customerId)}>
                Edit
            </button>
        </article>
    )
}
