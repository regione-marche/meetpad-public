import { Component, OnInit, Input } from '@angular/core';
import { FooterButtons } from '../../../enums/footer-buttons.enum';
import { FormButton } from '../../form/interfaces';

@Component({
    selector: 'app-footer-buttons',
    templateUrl: './footer-buttons.component.html',
    styleUrls: ['./footer-buttons.component.scss']
})
export class FooterButtonsComponent implements OnInit {
    @Input('groupButtons') groupButtons: Map<FooterButtons, FormButton>;

    constructor() {}

    ngOnInit() {}
}
