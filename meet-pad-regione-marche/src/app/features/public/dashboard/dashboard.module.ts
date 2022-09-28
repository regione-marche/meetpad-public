import { NgModule } from '@angular/core';
import { MainComponent } from './main/main.component';
import { SharedModule } from '@app/shared';

@NgModule({
    declarations: [MainComponent],
    imports: [SharedModule]
})
export class DashboardModule {}
