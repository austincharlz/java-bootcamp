export interface LoadingStateProps {
    message?: string
}

export function LoadingState({message = 'Loading customers...'}: LoadingStateProps) {
    return (
        <section className="loading-state" role="status" aria-live="polite">
            <p>{message}</p>
        </section>
    )
}
