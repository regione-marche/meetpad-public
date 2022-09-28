import { NgModule } from '@angular/core';
import { DocumentationComponent } from '@features/private/conference/features';
import { Routes, RouterModule } from '@angular/router';
import { DocumentationModule } from './documentation.module';
const routes: Routes = [
    {
        path: '',
        component: DocumentationComponent
    }
];

@NgModule({
    imports: [DocumentationModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DocumentationWithRoutingModule {}
