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
    return this.http.get<Topic[]>(`/topic`);
  }

  getSubscribed(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`/topic/subscribed`);
  }

  subscribe(userId: number, topicId: number): Observable<void> {
    return this.http.post<void>(`/topic/subscribe/${userId}/${topicId}`, {});
  }

  unsubscribe(userId: number, topicId: number): Observable<void> {
    return this.http.post<void>(`/topic/unsubscribe/${userId}/${topicId}`, {});
  }
}
