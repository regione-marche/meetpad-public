import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { PecComponent } from './main/pec.component';
import { PecSearchComponent } from './pec-search/pec-search.component';
import { SharedPrivateModule } from '@app/features/private/shared';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [PecComponent, PecSearchComponent],
    exports: [PecComponent]
})
export class PecModule {}
