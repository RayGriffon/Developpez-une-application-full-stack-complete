import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../models/topic.model';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {}

  getAll(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`/api/topic`);
  }

  getSubscribed(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`/api/topic/subscribed`);
  }

  subscribe(topicId: number): Observable<void> {
    return this.http.post<void>(`/api/topic/subscribe/${topicId}`, {});
  }

  unsubscribe(topicId: number): Observable<void> {
    return this.http.post<void>(`/api/topic/unsubscribe/${topicId}`, {});
  }
}

