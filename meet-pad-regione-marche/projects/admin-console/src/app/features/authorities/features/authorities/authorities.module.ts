import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { MainAuthoritiesComponent } from './main/main.component';
import { SharedAuthoritiesModule } from '../../shared/shared.module';
import { AuthorityComponent } from './components/authority/authority.component';

@NgModule({
    imports: [CommonModule, SharedAuthoritiesModule],
    declarations: [MainAuthoritiesComponent, AuthorityComponent]
})
export class AuthoritiesModule {}
