export interface ErrorStateProps {
    message: string
    onRetry?: () => void
}

export function ErrorState({message, onRetry}: ErrorStateProps) {
    return (
        <section className="error-state" role="alert">
            <p>{message}</p>
            {onRetry && (
                <button type="button" onClick={onRetry}>
                    Retry
                </button>
            )}
        </section>
    )
}
