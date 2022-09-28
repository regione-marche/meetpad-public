import { DefinitionComponent } from './main/definition.component';
import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [DefinitionComponent],
    exports: [DefinitionComponent]
})
export class DefinitionModule {}
