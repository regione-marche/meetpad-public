import { NgModule } from '@angular/core';
import { MainEditProceedingComponent } from './main/main.component';
import { EditAuthoritiesRoutingModule } from './proceeding-authorities.routing';
import { CommonModule } from '@common';
import { MessageService } from '@eng-ds/ng-toolkit';
import { SharedAdminModule } from '../../../../shared/shared.module';
import { AuthoritiesProceedingComponent } from './components/proceeding/proceeding.component';
import { SharedAuthoritiesModule } from '../../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        EditAuthoritiesRoutingModule,
        SharedAdminModule,
        SharedAuthoritiesModule
    ],
    declarations: [MainEditProceedingComponent, AuthoritiesProceedingComponent],
    providers: [MessageService],
    exports: [AuthoritiesProceedingComponent]
})
export class EditAuthoritiesModule {}
