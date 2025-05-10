import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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

  form = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    newPassword: ['', [
      Validators.minLength(8),
      Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/)
    ]]
  });  

  user?: SessionInformation;
  topics: Topic[] = [];
  subscriptions: Subscription[] = [];
  editMode = false;
  passwordPopup = false;
  password: string | null = null;
  onError = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private sessionService: SessionService,
    private topicService: TopicService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['updated'] === 'true') {
        this.successMessage = 'Profil mis à jour avec succès.';
      }      
    });
    
    const sub = this.authService.getProfile().subscribe({
      next: (profile) => {
        this.user = profile;
        this.form.patchValue(profile);
  
        this.topicService.getSubscribed().subscribe({
          next: (topics: Topic[]) => this.topics = topics,
          error: () => this.onError = 'Erreur lors du chargement des sujets.'
        });
      },
      error: () => this.onError = 'Erreur lors du chargement du profil.'
    });
  
    this.subscriptions.push(sub);
  }
  

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  enableEdit(): void {
    this.editMode = true;
  }

  cancelEdit(): void {
    this.editMode = false;
    if (this.user) {
      this.form.patchValue({
        username: this.user.username,
        newPassword: ''
      });
    }
    this.onError = '';
  }

  submit(): void {
    this.successMessage = '';
    this.onError = '';

    if (!this.form.valid) {
      this.onError = 'Le formulaire est invalide.';
      return;
    }

    const newPassword = this.form.value.newPassword;
    if (newPassword && !this.isPasswordSecure(newPassword)) {
      this.onError = 'Le mot de passe n’est pas assez sécurisé.';
      return;
    }

    this.passwordPopup = true;
  }

  confirmPassword(): void {
    this.onError = '';
    if (!this.user || !this.password) {
      this.onError = 'Mot de passe requis pour confirmer.';
      return;
    }
  
    const updateRequest: UpdateRequest = {
      username: this.form.value.username!,
      password: this.password,
      newPassword: this.form.value.newPassword || null
    };
  
    const sub = this.authService.update(updateRequest).subscribe({
      next: updated => {
        this.sessionService.update(updated);
        this.router.navigate(['/me'], { queryParams: { updated: 'true' } });
      },
      error: err => {
        if (typeof err.error === 'string') {
          this.onError = err.error;
        } else if (err.error?.message) {
          this.onError = err.error.message;
        } else {
          this.onError = 'Une erreur est survenue.';
        }
      }
    });
  
    this.subscriptions.push(sub);
    this.passwordPopup = false;
    this.password = null;
  }

  unsubscribe(topic: Topic): void {
    if (!this.user) return;

    const sub = this.topicService.unsubscribe(topic.id).subscribe({
      next: () => this.topics = this.topics.filter(t => t.id !== topic.id),
      error: () => this.onError = `Erreur lors du désabonnement de "${topic.name}".`
    });

    this.subscriptions.push(sub);
  }

  private isPasswordSecure(password: string): boolean {
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/.test(password);
  }

  get usernameErrors(): string | null {
    const ctrl = this.form.get('username');
    if (ctrl?.hasError('required')) return 'Le nom d’utilisateur est requis.';
    if (ctrl?.hasError('minlength')) return 'Le nom d’utilisateur doit contenir au moins 3 caractères.';
    return null;
  }
  
  get passwordErrors(): string | null {
    const ctrl = this.form.get('newPassword');
    if (!ctrl || !ctrl.dirty) return null;
    if (ctrl.hasError('minlength')) return 'Le mot de passe doit contenir au moins 8 caractères.';
    if (ctrl.hasError('pattern')) {
      return 'Le mot de passe doit contenir une majuscule, une minuscule, un chiffre et un caractère spécial.';
    }
    return null;
  }
  
}
