export interface Comment {
  id?: number;
  content: string;
  createdAt?: Date;
  postId: number;
  author?: string;
}
