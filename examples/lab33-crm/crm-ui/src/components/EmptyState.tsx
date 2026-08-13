export interface EmptyStateProps {
  title: string
  description?: string
}

export function EmptyState({ title, description }: EmptyStateProps) {
  return (
    <section className="empty-state" role="status" aria-live="polite">
      <h2>{title}</h2>
      {description && <p>{description}</p>}
    </section>
  )
}