import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/loginRequest.model';
import { RegisterRequest } from '../models/registerRequest.model';
import { SessionInformation } from '../models/sessionInformation.model';
import { UpdateRequest } from '../models/updateRequest.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
  }

  register(req: RegisterRequest): Observable<string> {
    return this.http.post(`/api/auth/register`, req, { responseType: 'text' });
  }

  update(req: UpdateRequest): Observable<SessionInformation> {
    return this.http.put<SessionInformation>(`/api/user/profile`, req);
  }

  getProfile(): Observable<SessionInformation> {
    return this.http.get<SessionInformation>('/api/user/me');
  }
  

}
