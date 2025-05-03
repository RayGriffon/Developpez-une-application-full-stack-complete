import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/post.model';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts-feed',
  templateUrl: './posts-feed.component.html',
  styleUrls: ['./posts-feed.component.scss']
})
export class PostsFeedComponent implements OnInit {

  constructor(private postService: PostService, private authService: AuthService, private router: Router) {}

  posts: Post[] = [];
  currentPage = 0;
  totalPages = 0;
  sortDir: 'asc' | 'desc' = 'desc';
  
  ngOnInit() {
    this.loadPosts();
  }
  
  loadPosts() {
    this.postService.getFeed(this.currentPage, 10, this.sortDir).subscribe(page => {
      this.posts = page.content;
      this.totalPages = page.totalPages;
    });
  }
  
  toggleSort() {
    this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    this.currentPage = 0;
    this.loadPosts();
  }
  
  changePage(offset: number) {
    this.currentPage += offset;
    this.loadPosts();
  }
  
  goToPostDetail(postId: number) {
    this.router.navigate(['/post', postId]);
  }

}