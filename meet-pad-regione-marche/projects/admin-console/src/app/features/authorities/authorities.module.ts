import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { MainProceedingComponent } from './main/main.component';
import { EditAuthoritiesModule } from './features/edit-authorities/proceeding-authorities.module';
import { AuthorityService } from './core/services/authority.service';
import { SharedAdminModule } from '../../shared/shared.module';

@NgModule({
    imports: [CommonModule, EditAuthoritiesModule, SharedAdminModule],
    declarations: [MainProceedingComponent],
    providers: [AuthorityService],
    exports: [MainProceedingComponent, EditAuthoritiesModule]
})
export class AuthoritiesProcedingModule {}
