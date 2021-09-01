import { NgModule } from '@angular/core';
import { DesktopComponent } from '@app/features/desktop/main/desktop.component';
import { SharedModule } from '@app/shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    DesktopComponent
  ]
})
export class DesktopModule { }
