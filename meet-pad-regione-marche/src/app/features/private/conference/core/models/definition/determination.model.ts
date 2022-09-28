/* tslint:disable:no-inferrable-types */

export class Determination {

    determinationObject: string = '';

    constructor(determination: Partial<Determination>) {
        if (determination) {
            this.determinationObject = determination.determinationObject || '';
        }
    }

    update(determination: Partial<Determination>) {
        if (determination) {
            this.determinationObject = determination.determinationObject;
        }
    }
}
