import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { RegisterRequest } from 'src/app/models/registerRequest.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnDestroy {
  public onError = '';
  private subscription = new Subscription();

  public form = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
  });

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService
  ) {}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private isPasswordValid(password: string): boolean {
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/;
    return pattern.test(password);
  }

  submit(): void {
    this.onError = '';
  
    if (!this.form.valid) {
      this.onError = 'Certains champs sont vides ou mal remplis.';
      return;
    }
  
    const { password } = this.form.value;
    if (!password || !this.isPasswordValid(password)) {
      this.onError = 'Le mot de passe doit faire au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.';
      return;
    }
  
    const registerRequest = this.form.value as RegisterRequest;
  
    let sub: Subscription;
    sub = this.authService.register(registerRequest)
      .pipe(finalize(() => sub.unsubscribe()))
      .subscribe({
        next: (_: string) => {
          this.router.navigate(['/'], {
            queryParams: { message: 'created' }
          });
        },
        error: (error) => {
          this.onError = error?.error || 'Erreur lors de l’inscription.';
          console.error(error);
        }
      });
  
    this.subscription.add(sub);
  }
  
  
}
