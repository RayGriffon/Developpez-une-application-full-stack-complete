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

  register(req: RegisterRequest): Observable<SessionInformation> {
    return this.http.post<SessionInformation>(`/auth/register`, req);
  }

  login(req: LoginRequest): Observable<SessionInformation> {
    return this.http.post<SessionInformation>(`/auth/login`, req);
  }

  update(req: UpdateRequest): Observable<SessionInformation> {
    return this.http.put<SessionInformation>(`/user/profile`, req);
  }

}
