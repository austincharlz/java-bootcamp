export interface CustomerToolbarProps {
    onAdd: () => void
}

export function CustomerToolbar({onAdd}: CustomerToolbarProps) {
    return (
        <section className="customer-toolbar" aria-label="Customer actions">
            <h2>Dashboard</h2>
            <button type="button" onClick={onAdd}>
                Add Customer
            </button>
        </section>
    )
}
