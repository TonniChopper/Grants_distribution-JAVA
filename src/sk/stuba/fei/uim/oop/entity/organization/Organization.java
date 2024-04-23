package sk.stuba.fei.uim.oop.entity.organization;

import sk.stuba.fei.uim.oop.entity.grant.*;
import sk.stuba.fei.uim.oop.entity.people.*;
import sk.stuba.fei.uim.oop.utility.*;

import java.util.*;

public class Organization implements OrganizationInterface{
    private String name;
    private int org_budget = Constants.COMPANY_INIT_OWN_RESOURCES;
    private Map<PersonInterface, Integer> employees;
    private Set<GrantInterface> grants ;
    private Set<ProjectInterface> projects;
    public Organization() {
        this.employees = new HashMap<>();
        this.projects = new HashSet<>();
        this.grants = new HashSet<>();
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addEmployee(PersonInterface p, int employment) {
        this.employees.put(p, employment);
    }
    @Override
    public Set<PersonInterface> getEmployees() {
        return this.employees.keySet();
    }

    @Override
    public int getEmploymentForEmployee(PersonInterface p) {
        return this.employees.get(p);
    }

    @Override
    public Set<ProjectInterface> getAllProjects() {
        return this.projects;
    }

    @Override
    public Set<ProjectInterface> getRunningProjects(int year) {
        Set<ProjectInterface> runningProjects = new HashSet<>();
        for (ProjectInterface project : this.projects) {
            if (project.getStartingYear() > year && year < project.getEndingYear()){
                runningProjects.add(project);
            }
        }
        return runningProjects;
    }

    @Override
    public void registerProjectInOrganization(ProjectInterface project) {
        this.projects.add(project);
    }

    @Override
    public int getProjectBudget(ProjectInterface pi) {
        if (!this.projects.contains(pi)) {
            return 0;
        }

        if (this instanceof University) {
            return pi.getTotalBudget();
        } else if (this instanceof Company) {
            int companyContribution = Math.min(org_budget, pi.getTotalBudget());
            return pi.getTotalBudget() + companyContribution;
        }

        return 0;
    }
    public void addGrant(GrantInterface grant) {
        this.grants.add(grant);
    }
    public Set<GrantInterface> getGrants() {
        return this.grants;
    }
    @Override
    public int getBudgetForAllProjects() {
        if (this instanceof University) {
            return 0;
        } else if (this instanceof Company) {
            return org_budget;
        }
        return 0;
    }

    @Override
    public void projectBudgetUpdateNotification(ProjectInterface pi, int year, int budgetForYear) {
        if (this.projects.contains(pi)) {
            if (this instanceof Company) {
                int companyContribution = Math.min(org_budget, budgetForYear);
                pi.setBudgetForYear(year, pi.getBudgetForYear(year) + companyContribution);
                org_budget -= companyContribution;
            }else
                pi.setBudgetForYear(year, budgetForYear);
        }
    }
}
