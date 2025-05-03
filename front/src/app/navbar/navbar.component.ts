import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  menuOpen = false;
  activeRoute: string = '';

  constructor(public authService: AuthService, private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
      this.activeRoute = event.url;
      }
    });
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  home(): void {
    this.router.navigate(['/']);
  }

  feeds(): void {
    this.router.navigate(['/feed']);
  }

  topics(): void {
    this.router.navigate(['/topics']);
  }

  me(): void {
    this.router.navigate(['/me']);
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  get shouldShowNavbar(): boolean {
    const isHome = this.activeRoute === '/' || this.activeRoute.startsWith('/home');
    return this.isAuthenticated() || !isHome;
  }
  
}
