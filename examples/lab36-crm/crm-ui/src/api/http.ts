import {ApiError} from './ApiError'
import {emitAuthExpired} from '../auth/authEvents'
import {tokenStore} from '../auth/tokenStore'

const DEFAULT_BASE_URL = 'http://localhost:8080'
const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? DEFAULT_BASE_URL).replace(/\/+$/, '')
const API_ORIGIN = new URL(API_BASE_URL).origin
const CORRELATION_ID = 'lab-request-001'

function resolveUrl(path: string): URL {
    if (path.startsWith('http://') || path.startsWith('https://')) {
        return new URL(path)
    }

    return new URL(path, `${API_BASE_URL}/`)
}

function buildHeaders(url: URL, headers?: HeadersInit): Headers {
    const nextHeaders = new Headers(headers)
    nextHeaders.set('Content-Type', 'application/json')

    if (url.origin === API_ORIGIN) {
        const token = tokenStore.get()
        if (token) {
            nextHeaders.set('Authorization', `Bearer ${token}`)
        }
        nextHeaders.set('X-Correlation-Id', CORRELATION_ID)
    }

    return nextHeaders
}

export async function request<T>(path: string, init: RequestInit = {}): Promise<T> {
    let response: Response
    const url = resolveUrl(path)

    try {
        response = await fetch(url.toString(), {
            ...init,
            headers: buildHeaders(url, init.headers),
        })
    } catch (error) {
        if (error instanceof DOMException && error.name === 'AbortError') {
            throw new ApiError('Request cancelled', 'abort')
        }

        throw new ApiError('Cannot reach the CRM service', 'network')
    }

    if (!response.ok) {
        if (response.status === 401) {
            tokenStore.clear()
            emitAuthExpired()
        }

        throw await ApiError.from(response)
    }
    if (response.status === 204) return undefined as T

    try {
        return (await response.json()) as T
    } catch {
        throw new ApiError('Received an unreadable CRM response', 'parse', response.status)
    }
}
