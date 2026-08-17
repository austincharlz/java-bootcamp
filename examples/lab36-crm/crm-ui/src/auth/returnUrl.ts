const INTERNAL_PATH = /^\/(?!\/)/

export function sanitizeReturnUrl(value: string | null | undefined): string {
    if (!value) return '/'

    const trimmed = value.trim()
    if (!trimmed) return '/'
    if (/^[a-zA-Z][a-zA-Z\d+\-.]*:/.test(trimmed)) return '/'
    if (!INTERNAL_PATH.test(trimmed)) return '/'

    return trimmed
}
