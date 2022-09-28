import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { MainManagerComponent } from './main/main.component';
import { ManagerSearchComponent } from './components/manager-search/manager-search.component';
import { ManagerListComponent } from './components/manager-list/manager-list.component';
import { ManagerComponent } from './components/manager/manager.component';
import { UtilityService } from '../../../../core/services/utility.service';
import { SharedAdminModule } from '../../../../shared/shared.module';

@NgModule({
    imports: [CommonModule, SharedAdminModule],
    providers: [UtilityService],
    declarations: [
        MainManagerComponent,
        ManagerSearchComponent,
        ManagerListComponent,
        ManagerComponent
    ],
    exports: [ManagerSearchComponent]
})
export class ManagerModule {}
