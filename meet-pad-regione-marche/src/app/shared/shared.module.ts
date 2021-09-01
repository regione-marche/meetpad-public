import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { TranslateModule } from '@ngx-translate/core';

import { NgxEngToolkitModule } from '@eng-ds/ng-toolkit';

import { EngHeaderComponent } from '@app/shared/components/eng-header/eng-header.component';
import { EngFooterComponent } from '@app/shared/components/eng-footer/eng-footer.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    TranslateModule,
    NgxEngToolkitModule
  ],
  declarations: [
    EngHeaderComponent,
    EngFooterComponent
  ],
  exports: [
    ReactiveFormsModule,
    FormsModule,
    EngHeaderComponent,
    EngFooterComponent,
    NgxEngToolkitModule
  ]
})
export class SharedModule { }
