import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { MainComponent } from './main.component';
import { SharedAdminModule } from '../../shared/shared.module';

@NgModule({
    imports: [CommonModule, SharedAdminModule],
    declarations: [MainComponent],
    exports: [MainComponent]
})
export class MainModule {}
