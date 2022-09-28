import { DefinitionComponent } from './main/definition.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DefinitionModule } from './definition.module';
const routes: Routes = [
    {
        path: '',
        component: DefinitionComponent
    }
];

@NgModule({
    imports: [DefinitionModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DefinitionWithRoutingModule {}
