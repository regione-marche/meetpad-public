import { ComboBox } from '@common';

import { Applicant, Company } from '../procedure';

export interface CompanySection {
    company: Company;
    applicant: Applicant | null;
}

export class ProcedureTemplate {
    applicant: Applicant | null;
    companySection: CompanySection[];

    constructor(template: Partial<ProcedureTemplate>) {
        if (template) {
            this.applicant = template.applicant;
            this.companySection = template.companySection;
        }
    }

    getCompanySection(companyId: string): CompanySection {
        const companySection = this.selectCompanyAndApplicant(companyId);
        if (!companySection.applicant) {
            companySection.applicant = this.applicant;
        }
        return companySection;
    }

    getApplicant(companyId?: string): Applicant | null {
        if (!companyId && this.applicant) {
            return this.applicant;
        }
        return null;
    }

    getCompanies(): ComboBox[] {
        return this.companySection.map((c: CompanySection) => {
            return {
                key: c.company.id.toString(),
                value: c.company.denomination
            };
        });
    }

    selectCompanyAndApplicant(companyId: string): CompanySection {
        return this.companySection.find((item: CompanySection) => {
            return item.company.id == companyId;
        });
    }

    hasCompanies(): boolean {
        if (this.companySection.length > 0) {
            return true;
        }
        return false;
    }

    extract(
        companyId?: string
    ): {
        company: Company;
        applicant: Applicant;
    } {
        let _extracted = {
            applicant: null,
            company: null
        };

        if (this.hasCompanies() && companyId) {
            _extracted = this.getCompanySection(companyId);
        } else {
            _extracted.applicant = this.getApplicant(companyId);
        }

        return _extracted;
    }
}
