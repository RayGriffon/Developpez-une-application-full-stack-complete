export interface UpdateRequest {
    username: string;
    password: string | null;
    newPassword: string | null;
}