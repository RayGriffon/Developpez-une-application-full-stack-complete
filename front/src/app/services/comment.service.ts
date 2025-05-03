import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Comment } from "../models/comment.model";

@Injectable({ providedIn: 'root' })
export class CommentService {
  private api = '/comments';

  constructor(private http: HttpClient) {}

  addComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(`/api/comments/add`, comment);
  }

  getByPost(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`/api/comments/post/${postId}`);
  }
}