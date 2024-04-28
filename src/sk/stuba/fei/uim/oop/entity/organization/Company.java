package sk.stuba.fei.uim.oop.entity.organization;

import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.utility.Constants;

public class Company extends Organization{

    @Override
    public int get_project_budget_internal(ProjectInterface pi) {
        int companyContribution = Math.min(Constants.COMPANY_INIT_OWN_RESOURCES, pi.getTotalBudget());
        this.org_budget += companyContribution;
        if(this.org_budget > Constants.COMPANY_INIT_OWN_RESOURCES){
            return pi.getTotalBudget();
        }else
            return pi.getTotalBudget() + companyContribution;
    }
}
