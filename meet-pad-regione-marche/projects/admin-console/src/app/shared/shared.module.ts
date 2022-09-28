import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EngCardComponent } from './components/eng-card/eng-card.component';
import { EngSearchComponent } from './components/eng-search/eng-search.component';

@NgModule({
    imports: [CommonModule],
    declarations: [EngCardComponent, EngSearchComponent],
    exports: [EngCardComponent]
})
export class SharedAdminModule {}
