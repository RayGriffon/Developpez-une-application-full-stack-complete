import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/post.model';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts-feed',
  templateUrl: './posts-feed.component.html',
  styleUrls: ['./posts-feed.component.scss']
})
export class PostsFeedComponent implements OnInit {

  constructor(private postService: PostService, private router: Router) {}

  posts: Post[] = [];
  currentPage = 0;
  totalPages = 0;
  sortDir: 'asc' | 'desc' = 'desc';
  loading = false;
  
  ngOnInit() {
    this.loadPosts();
  }
  
  loadPosts() {
    this.loading = true;
    this.postService.getFeed(this.currentPage, 10, this.sortDir).subscribe(page => {
      this.posts = page.content;
      this.totalPages = page.totalPages;
      this.loading = false;
    });
  }

  loadMorePosts() {
    this.loading = true;
    this.postService.getFeed(this.currentPage, 10, this.sortDir).subscribe(page => {
      this.posts = [...this.posts, ...page.content];
      this.totalPages = page.totalPages;
      this.loading = false;
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

  isMobile = window.innerWidth <= 600;

  @HostListener('window:scroll', [])
onWindowScroll() {
  if (!this.isMobile || this.loading || this.currentPage + 1 >= this.totalPages) return;

  const scrollPosition = window.scrollY + window.innerHeight;
  const threshold = document.body.offsetHeight - 100;

  if (scrollPosition >= threshold) {
    this.currentPage++;
    this.loadMorePosts();
  }
}


}