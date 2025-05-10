import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(private router: Router, private route: ActivatedRoute, private snackBar: MatSnackBar, private authService: AuthService) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/feed']);
      return;
    }

    this.route.queryParams.subscribe(params => {
      if (params['message'] === 'created') {
        this.snackBar.open('Compte créé avec succès 🎉', 'Fermer', {
          duration: 3000
        });
      }
    });
  }

  register(): void {
    this.router.navigate(['/auth/register']);
  }

  login(): void {
    this.router.navigate(['/auth/login']);
  }
}
