import { NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';

import { ResultTableComponent } from './components/result-table/result-table.component';

@NgModule({
    imports: [SharedModule],
    declarations: [ResultTableComponent],
    exports: [ResultTableComponent]
})
export class SharedPrivateModule {}
