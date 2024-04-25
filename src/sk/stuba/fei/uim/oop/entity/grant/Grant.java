package sk.stuba.fei.uim.oop.entity.grant;
import sk.stuba.fei.uim.oop.utility.*;
import sk.stuba.fei.uim.oop.entity.organization.*;
import sk.stuba.fei.uim.oop.entity.people.*;
import java.util.*;

public class Grant implements GrantInterface {
    private String name;
    private String identifier;
    private int year, budget, proj_budget, default_budget;
    private AgencyInterface agency;
    private Organization organization ;
    private GrantState State;
    private Set<ProjectInterface> registeredProjects = new HashSet<>();
//    List<ProjectInterface> validProjects = new ArrayList<>();
    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    @Override
    public int getYear() {
        return year;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public AgencyInterface getAgency() {
        return agency;
    }

    @Override
    public void setAgency(AgencyInterface agency) {
        this.agency = agency;
    }

    @Override
    public int getBudget() {
        return budget;
    }

    @Override
    public void setBudget(int budget) {
        this.budget = budget;
        this.default_budget = budget;
    }

    @Override
    public int getRemainingBudget() {
        for (ProjectInterface project : registeredProjects) { //?????????????????????????????????????????????????????????????
            default_budget -= getBudgetForProject(project);
        }
        return default_budget;
    }

    @Override
    public int getBudgetForProject(ProjectInterface project) { //?????????????????????????????????????????????????????77
        proj_budget = project.getBudgetForYear(year) - (project.getApplicant() instanceof Company ? Math.min(project.getBudgetForYear(year) , Constants.COMPANY_INIT_OWN_RESOURCES) : 0);
        proj_budget = proj_budget * Constants.PROJECT_DURATION_IN_YEARS;
        return proj_budget;
    }

    @Override
    public boolean registerProject(ProjectInterface project) {
        if (State == GrantState.STARTED && project.getStartingYear() == this.year && !project.getAllParticipants().isEmpty()){
            for (PersonInterface participant : project.getAllParticipants()) {
                if (!participant.getEmployers().contains(project.getApplicant())) {
                    return false;
                }
            }
            this.registeredProjects.add(project);
            OrganizationInterface organizations = project.getApplicant();
            organizations.registerProjectInOrganization(project);
            if (project.getApplicant() instanceof Organization) {
                ((Organization) project.getApplicant()).addGrant(this); // Добавляем грант в организацию
            }
            return true;
        }
        return false;
    }

    @Override
    public Set<ProjectInterface> getRegisteredProjects() {
        return registeredProjects;
    }

    @Override
    public GrantState getState() {
        return State;
    }

    @Override
    public void callForProjects() {
        State = GrantState.STARTED;
    }
    private boolean checkResearchCapacity(ProjectInterface project) {
        for (PersonInterface participant : project.getAllParticipants()) {
            int totalCommitment = 0;
            for (OrganizationInterface organizations : participant.getEmployers()) {
                for (ProjectInterface proj : organizations.getAllProjects()) {
                    for (GrantInterface grant : ((Organization) organizations).getGrants()) {
                        if (grant.getRegisteredProjects().contains(proj)) {
                            if (grant.getState() == GrantState.CLOSED
                            && this.getYear()>proj.getEndingYear())
                                break;
                            if (grant.getState() == GrantState.CLOSED
                                    || this.getState() == GrantState.EVALUATING
                                    && proj.getAllParticipants().contains(participant)
                                    && this.getYear() <= proj.getEndingYear()) {
                                totalCommitment += organizations.getEmploymentForEmployee(participant);
                                break;
                            }
                        }else
                            break;
                    }
                }
            }
            if (totalCommitment > Constants.MAX_EMPLOYMENT_PER_AGENCY) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void evaluateProjects() {
        List<ProjectInterface> projects = new ArrayList<>(getRegisteredProjects());
        List<ProjectInterface> validProjects = new ArrayList<>();
        State = GrantState.EVALUATING;
        for (ProjectInterface project : projects) {
            if (checkResearchCapacity(project)) {
                validProjects.add(project);
            } else {
                project.setBudgetForYear(getYear(), 0);
            }
        }

        int budgetPerProject;
        if (validProjects.size() == 1) {
            budgetPerProject = getBudget();
            budgetPerProject = budgetPerProject / Constants.PROJECT_DURATION_IN_YEARS;
            validProjects.get(0).setBudgetForYear(getYear(), budgetPerProject);
            for (int l = this.getYear(); l <= validProjects.get(0).getEndingYear(); l++) {
                validProjects.get(0).getApplicant().projectBudgetUpdateNotification(validProjects.get(0),l, budgetPerProject);
            }
        } else {
            if(!validProjects.isEmpty() && this.getRemainingBudget() > 0) {
                budgetPerProject = getBudget() / (validProjects.size() / 2);
                budgetPerProject = budgetPerProject / Constants.PROJECT_DURATION_IN_YEARS;
                for (int i = 0; i < validProjects.size() / 2; i++) {
                    for (int l = this.getYear(); l <= validProjects.get(i).getEndingYear(); l++) {
                        validProjects.get(i).setBudgetForYear(l, budgetPerProject);
                        validProjects.get(i).getApplicant().projectBudgetUpdateNotification(validProjects.get(i),l, budgetPerProject);
                    }
                }
            }else {
                budgetPerProject = 0;
                for (int i = 0; i < validProjects.size() / 2; i++) {
                    for (int l = this.getYear(); l <= validProjects.get(i).getEndingYear(); l++) {
                        validProjects.get(i).setBudgetForYear(l, budgetPerProject);
                    }
                }
            }
        }
    }

    @Override
    public void closeGrant() {
        State = GrantState.CLOSED;
    }
}
