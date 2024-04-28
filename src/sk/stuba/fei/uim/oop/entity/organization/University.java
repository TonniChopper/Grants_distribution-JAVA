package sk.stuba.fei.uim.oop.entity.organization;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.utility.Constants;
public class University extends Organization{

        @Override
        public int get_project_budget_internal(ProjectInterface pi) {
            return pi.getTotalBudget();
        }
}
