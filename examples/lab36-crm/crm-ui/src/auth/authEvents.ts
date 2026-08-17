const listeners = new Set<() => void>()

export function emitAuthExpired() {
    for (const listener of listeners) {
        listener()
    }
}

export function onAuthExpired(listener: () => void) {
    listeners.add(listener)
    return () => {
        listeners.delete(listener)
    }
}
