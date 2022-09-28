import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { UsersListComponent } from './features/components/users-list/users-list.component';
import { UsersComponent } from './features/components/users/users.component';
import { MainUsersComponent } from './features/main/main.component';
import { UsersSearchComponent } from './features/components/users-search/users-search.component';
import { UsersService } from './core/services/users.service';
import { SharedAdminModule } from '../../shared/shared.module';

@NgModule({
    imports: [CommonModule, SharedAdminModule],
    declarations: [
        UsersListComponent,
        UsersComponent,
        MainUsersComponent,
        UsersSearchComponent
    ],
    providers: [UsersService]
})
export class UsersModule {}
