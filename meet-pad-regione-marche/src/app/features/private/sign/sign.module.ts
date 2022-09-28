import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignTableComponent } from './components/sign-table/sign-table.component';
import { SignPanelComponent } from './components/sign-panel/sign-panel.component';

@NgModule({
  imports: [
    CommonModule, SignModule ],
  declarations: [SignTableComponent, SignPanelComponent],
  exports: [SignTableComponent,SignPanelComponent]

})
export class SignModule { }
