import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';

import { AccreditationTabComponent } from './main/accreditation-tab.component';
import { AccreditationTableComponent } from './components/accreditation-table/accreditation-table.component';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [AccreditationTableComponent, AccreditationTabComponent],
    exports: [AccreditationTabComponent]
})
export class AccreditationTabModule {}
