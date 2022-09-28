import { Applicant } from './applicant.model';
import { Localization } from './localization.model';
import { Company } from './company.model';

export class Procedure {
    applicant: Applicant;
    localization?: Localization;
    company?: Company;
    readonly = false;

    constructor(procedure: Partial<Procedure>) {
        this.applicant = new Applicant(procedure && procedure.applicant);
        this.localization = new Localization(
            procedure && procedure.localization
        );
        this.company = new Company(procedure && procedure.company);
        if (procedure) {
            this.readonly = procedure.readonly;
        }
    }

    update(procedure: Partial<Procedure>) {
        this.applicant.update(procedure.applicant);
        this.localization.update(procedure.localization);
        this.company.update(procedure.company);
    }

    toDto(): Procedure {
        const _that = Object.assign({}, this);
        _that.applicant = _that.applicant.toDto();
        delete _that.readonly;
        return _that;
    }

    setApplicant(applicant: Partial<Applicant>): void {
        this.applicant = Object.assign(this.applicant, applicant);
    }

    setCompany(company: Partial<Company>): void {
        this.company = Object.assign(this.company, company);
    }
}
