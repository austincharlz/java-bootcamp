import {ApiError} from './ApiError'

const DEFAULT_BASE_URL = 'http://localhost:8080'
const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? DEFAULT_BASE_URL).replace(/\/+$/, '')
const CORRELATION_ID = 'lab-request-001'

export async function request<T>(path: string, init: RequestInit = {}): Promise<T> {
    let response: Response

    try {
        response = await fetch(`${API_BASE_URL}${path}`, {
            ...init,
            headers: {
                'Content-Type': 'application/json',
                'X-Correlation-Id': CORRELATION_ID,
                ...init.headers,
            },
        })
    } catch (error) {
        if (error instanceof DOMException && error.name === 'AbortError') {
            throw new ApiError('Request cancelled', 'abort')
        }

        throw new ApiError('Cannot reach the CRM service', 'network')
    }

    if (!response.ok) throw await ApiError.from(response)
    if (response.status === 204) return undefined as T

    try {
        return (await response.json()) as T
    } catch {
        throw new ApiError('Received an unreadable CRM response', 'parse', response.status)
    }
}
