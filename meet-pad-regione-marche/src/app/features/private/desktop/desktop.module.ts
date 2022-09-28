import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { DesktopComponent } from '@features/private/desktop/components/desktop/desktop.component';
import { SharedPrivateModule } from '@features/private/shared';

@NgModule({
  imports: [
    SharedModule,
    SharedPrivateModule
  ],
  declarations: [
    DesktopComponent
  ]
})
export class DesktopModule { }
