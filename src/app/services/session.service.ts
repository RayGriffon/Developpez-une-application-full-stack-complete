import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../models/sessionInformation.model';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private readonly storageKey = 'session';

  private sessionInformation?: SessionInformation;
  private isLoggedSubject: BehaviorSubject<boolean>;

  constructor() {
    const saved = localStorage.getItem(this.storageKey);
    this.sessionInformation = saved ? JSON.parse(saved) : undefined;
    this.isLoggedSubject = new BehaviorSubject<boolean>(!!this.sessionInformation);
  }

  getSession(): SessionInformation | undefined {
    return this.sessionInformation;
  }

  $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  logIn(session: SessionInformation): void {
    this.sessionInformation = session;
    localStorage.setItem(this.storageKey, JSON.stringify(session));
    this.isLoggedSubject.next(true);
  }

  update(session: SessionInformation): void {
    this.sessionInformation = session;
    localStorage.setItem(this.storageKey, JSON.stringify(session));
  }

  logOut(): void {
    this.sessionInformation = undefined;
    localStorage.removeItem(this.storageKey);
    this.isLoggedSubject.next(false);
  }
}
