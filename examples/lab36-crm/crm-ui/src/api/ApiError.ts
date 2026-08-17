export type ApiErrorKind = 'network' | 'http' | 'abort' | 'parse'

type ApiErrorPayload = {
    message?: unknown
    error?: unknown
    fieldErrors?: unknown
    errors?: unknown
}

export class ApiError extends Error {
    readonly kind: ApiErrorKind
    readonly status?: number
    readonly fieldErrors?: Record<string, string>

    constructor(message: string, kind: ApiErrorKind, status?: number, fieldErrors?: Record<string, string>) {
        super(message)
        this.name = 'ApiError'
        this.kind = kind
        this.status = status
        this.fieldErrors = fieldErrors
    }

    static async from(response: Response): Promise<ApiError> {
        const fallback = `CRM request failed (${response.status})`

        const contentType = response.headers.get('content-type') ?? ''
        if (!contentType.toLowerCase().includes('application/json')) {
            const text = await response.text()
            const message = text.trim() || fallback
            return new ApiError(message, 'http', response.status)
        }

        let payload: ApiErrorPayload | null = null
        try {
            payload = (await response.json()) as ApiErrorPayload
        } catch {
            return new ApiError('Received an unreadable CRM error response', 'parse', response.status)
        }

        const message = ApiError.readMessage(payload) ?? fallback
        const fieldErrors = ApiError.readFieldErrors(payload)
        return new ApiError(message, 'http', response.status, fieldErrors)
    }

    private static readMessage(payload: ApiErrorPayload | null): string | undefined {
        if (!payload) return undefined
        if (typeof payload.message === 'string' && payload.message.trim()) return payload.message
        if (typeof payload.error === 'string' && payload.error.trim()) return payload.error
        return undefined
    }

    private static readFieldErrors(payload: ApiErrorPayload | null): Record<string, string> | undefined {
        if (!payload) return undefined
        const errorsFromFieldErrors = ApiError.toFieldMap(payload.fieldErrors)
        if (errorsFromFieldErrors) return errorsFromFieldErrors
        return ApiError.toFieldMap(payload.errors)
    }

    private static toFieldMap(value: unknown): Record<string, string> | undefined {
        if (!value) return undefined
        if (Array.isArray(value)) {
            const entries = value
                .map((item) => {
                    if (!item || typeof item !== 'object') return null
                    const candidate = item as Record<string, unknown>
                    const field = typeof candidate.field === 'string' ? candidate.field : undefined
                    const message =
                        typeof candidate.message === 'string'
                            ? candidate.message
                            : typeof candidate.defaultMessage === 'string'
                                ? candidate.defaultMessage
                                : undefined
                    if (!field || !message) return null
                    return [field, message] as const
                })
                .filter((entry): entry is readonly [string, string] => entry !== null)
            if (entries.length === 0) return undefined
            return Object.fromEntries(entries)
        }

        if (typeof value === 'object') {
            const entries = Object.entries(value as Record<string, unknown>)
                .filter(([, message]) => typeof message === 'string' && message.trim())
                .map(([field, message]) => [field, String(message)] as const)
            if (entries.length === 0) return undefined
            return Object.fromEntries(entries)
        }

        return undefined
    }
}
