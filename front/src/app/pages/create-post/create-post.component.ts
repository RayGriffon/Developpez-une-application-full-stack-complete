import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/post.model';
import { Topic } from 'src/app/models/topic.model';
import { TopicService } from 'src/app/services/topic.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {
  newPost: Partial<Post> = {
    title: '',
    content: ''
  };

  topics: Topic[] = [];
  topicId!: number;

  constructor(private http: HttpClient, private router: Router, private topicService: TopicService) {}

  ngOnInit(): void {
    this.topicService.getAll().subscribe(topics => {
      this.topics = topics;
      if (topics.length > 0) {
        this.topicId = topics[0].id;
      }
    });
  }

  createPost(): void {
    const dto = {
      topic: this.topicId,
      title: this.newPost.title,
      content: this.newPost.content
    };

    this.http.post<void>(`/api/posts/create`, dto).subscribe({
      next: () => this.router.navigate(['/feed']),
      error: (err) => console.error('Erreur création post :', err)
    });
  }
}
