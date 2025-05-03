import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { MeComponent } from './pages/auth/me/me.component';
import { TopicListComponent } from './pages/topic-list/topic-list.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { PostDetailComponent } from './pages/post-detail/post-detail.component';
import { PostsFeedComponent } from './pages/posts-feed/posts-feed.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  {
    path: 'create-post', 
    component: CreatePostComponent,
  },
  {
    path: 'post/:id',
    component: PostDetailComponent,
  },
  { 
    path: 'feed', 
    component: PostsFeedComponent },
  {
    path: 'me',
    component: MeComponent,
  },
  {
    path: 'topics',
    component: TopicListComponent,
  },
  {
    path: 'auth/login',
    component: LoginComponent,
  },
  {
    path: 'auth/register',
    component: RegisterComponent,
  },
  {
    path: '',
    component: HomeComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
