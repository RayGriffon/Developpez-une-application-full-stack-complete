import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { TopicService } from 'src/app/services/topic.service';
import { Topic } from 'src/app/models/topic.model';
import { UpdateRequest } from 'src/app/models/updateRequest.model';
import { SessionInformation } from 'src/app/models/sessionInformation.model';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    username: ['', [Validators.required, Validators.minLength(3)]],
    newPassword: ['', [Validators.minLength(8)]],
  });

  public onError = '';
  public topics: Topic[] = [];
  public passPopup = false;
  public password: string | null = null;

  private subscriptions: Subscription[] = [];
  private user = this.sessionService.getSession();

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService,
    private topicService: TopicService
  ) {}

  ngOnInit(): void {
    if (this.user) this.form.patchValue(this.user);

    const sub = this.topicService.getSubscribed().subscribe({
      next: (topics: Topic[]) => this.topics = topics,
      error: () => this.onError = 'Erreur lors du chargement des topics.'
    });

    this.subscriptions.push(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  submit(): void {
    if (!this.form.valid) {
      this.onError = 'Le nom d\'utilisateur doit contenir au moins 3 caractères.';
      return;
    }

    const newPassword = this.form.value.newPassword;
    if (newPassword && !this.isPasswordSecure(newPassword)) {
      this.onError = 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.';
      return;
    }

    this.passPopup = true;
  }

  valid(): void {
    if (!this.user) return;

    this.passPopup = false;

    const updateRequest: UpdateRequest = {
      email: this.form.value.email!,
      username: this.form.value.username!,
      password: this.password,
      newPassword: this.form.value.newPassword || null
    };

    const sub = this.authService.update(updateRequest).subscribe({
      next: (updated: SessionInformation) => {
        this.sessionService.update(updated);
        window.location.reload();
      },
      error: (err) => this.onError = err?.error || 'Une erreur est survenue.'
    });

    this.subscriptions.push(sub);
  }

  cancel(): void {
    this.passPopup = false;
  }

  logOut(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  unsubscribe(topic: Topic): void {
    if (!this.user) return;

    const sub = this.topicService.unsubscribe(this.user.id, topic.id).subscribe({
      next: () => window.location.reload(),
      error: () => this.onError = `Echec du unsubscribe"${topic.name}".`
    });

    this.subscriptions.push(sub);
  }

  private isPasswordSecure(password: string): boolean {
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/;
    return pattern.test(password);
  }
}
