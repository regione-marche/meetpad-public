import { FormControl } from '@angular/forms';
export function SelectValueValidator(control: FormControl) {
    if (control.value && control.value.key === '-1') {
        return { error: true };
    }
    return null;
}
