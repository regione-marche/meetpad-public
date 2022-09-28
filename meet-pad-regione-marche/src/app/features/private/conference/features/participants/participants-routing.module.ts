import { NgModule } from '@angular/core';
import { ParticipantsComponent } from './main/participants.component';
import { Routes, RouterModule } from '@angular/router';
import { ParticipantsModule } from './participants.module';

const routes: Routes = [
    {
        path: '',
        component: ParticipantsComponent
    }
];

@NgModule({
    imports: [ParticipantsModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ParticipantsWithRoutingModule {}
