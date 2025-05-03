import { Comment } from './comment.model';

export interface Post {
  id: number;
  title: string;
  content: string;
  author: string;
  topic: string;
  createdAt: Date;
  comments: Comment[];
}