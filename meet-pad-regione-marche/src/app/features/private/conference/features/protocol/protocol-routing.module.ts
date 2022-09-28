import { NgModule } from '@angular/core';
import { ProtocolTabComponent } from './protocol/protocol.component';
import { Routes, RouterModule } from '@angular/router';
import { ProtocolModule } from './protocol.module';
const routes: Routes = [
    {
        path: '',
        component: ProtocolTabComponent
    }
];

@NgModule({
    imports: [ProtocolModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProtocolModuleWithRoutingModule {}