import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { TopicService } from 'src/app/services/topic.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.scss']
})
export class TopicListComponent implements OnInit {
  topics: Topic[] = [];
  subscribedTopicIds: Set<number> = new Set();

  constructor(private topicService: TopicService) {}

  ngOnInit() {
    this.loadTopics();
  }

  loadTopics() {
    this.topicService.getAll().subscribe(all => {
      this.topics = all;
      this.topicService.getSubscribed().subscribe(subs => {
        this.subscribedTopicIds = new Set(subs.map(t => t.id));
      });
    });
  }

  isSubscribed(topic: Topic): boolean {
    return this.subscribedTopicIds.has(topic.id);
  }

  toggleFollow(topic: Topic) {
    if (this.isSubscribed(topic)) {
      this.topicService.unsubscribe(topic.id).subscribe(() => {
        this.subscribedTopicIds.delete(topic.id);
      });
    } else {
      this.topicService.subscribe(topic.id).subscribe(() => {
        this.subscribedTopicIds.add(topic.id);
      });
    }
  }
}
