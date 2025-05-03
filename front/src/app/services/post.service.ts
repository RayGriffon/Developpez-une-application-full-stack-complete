import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Post } from "../models/post.model";
import { Observable } from "rxjs";
import { Page } from "../models/page.model";

@Injectable({ providedIn: 'root' })
export class PostService {

  constructor(private http: HttpClient) {}

  getFeed(page: number, size: number, sortDir: string): Observable<Page<Post>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortDir', sortDir);
  
    return this.http.get<Page<Post>>(`/api/posts/feed`, { params });
  }
  
  getPostById(postId: number): Observable<Post> {
    return this.http.get<Post>(`/api/posts/${postId}`);
  }

}