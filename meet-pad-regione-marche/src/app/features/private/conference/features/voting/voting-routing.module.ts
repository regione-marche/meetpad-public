import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VotingComponent } from './voting/voting.component';
import { SharedModule } from '@app/shared';
import { VotingModule } from './voting.module';

const routes: Routes = [
    {
        path: '',
        component: VotingComponent
    }
];

@NgModule({
    imports: [SharedModule, RouterModule.forChild(routes), VotingModule],
    exports: [RouterModule]
})
export class VotingRoutingModule {}
