import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';

@Component({
    template: `
        <eng-content-loader [loading]="true"></eng-content-loader>
    `
})
// tslint:disable-next-line:component-class-suffix
export class AuthComponent {
    constructor(private authService: AuthService) {
        this.authService.tryLogin();
    }
}
