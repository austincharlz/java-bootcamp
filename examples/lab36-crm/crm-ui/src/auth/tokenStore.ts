let accessToken: string | null = null;
export const tokenStore = {
    get: () => accessToken,
    set: (value: string) => {
        accessToken = value;
    },
    clear: () => {
        accessToken = null;
    },
};