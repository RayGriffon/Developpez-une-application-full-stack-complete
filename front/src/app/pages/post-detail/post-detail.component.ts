import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Post } from 'src/app/models/post.model';
import { Comment } from 'src/app/models/comment.model';
import { CommentService } from 'src/app/services/comment.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {
  postId!: number;
  post?: Post;
  comments: Comment[] = [];
  newComment: Partial<Comment> = {
    content: '',
    postId: 0
  };

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    this.postId = +this.route.snapshot.paramMap.get('id')!;
    this.loadPost();
    this.loadComments();
  }

  loadPost(): void {
    this.postService.getPostById(this.postId).subscribe({
      next: (post) => {
        this.post = post;
        this.newComment.postId = this.postId;
      },
      error: (err) => console.error('Erreur récupération post :', err)
    });
  }

  loadComments(): void {
    this.commentService.getByPost(this.postId).subscribe({
      next: (comments) => {
        this.comments = comments;
      },
      error: (err) => console.error('Erreur récupération commentaires :', err)
    });
  }

  addComment(): void {
    const commentDTO = {
      content: this.newComment.content!,
      postId: this.postId
    };

    this.commentService.addComment(commentDTO as Comment).subscribe({
      next: (comment) => {
        this.comments.push(comment);
        this.newComment.content = '';
      },
      error: (err) => console.error('Erreur ajout commentaire :', err)
    });
  }
}
