import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../models/sessionInformation.model';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private readonly storageKey = 'session';

  private sessionInformation?: SessionInformation;

  constructor() {
    const saved = localStorage.getItem(this.storageKey);
    this.sessionInformation = saved ? JSON.parse(saved) : undefined;
  }

  update(session: SessionInformation): void {
    this.sessionInformation = session;
    localStorage.setItem(this.storageKey, JSON.stringify(session));
  }

}
