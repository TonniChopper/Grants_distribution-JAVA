package sk.stuba.fei.uim.oop.entity.grant;

import sk.stuba.fei.uim.oop.entity.organization.*;
import sk.stuba.fei.uim.oop.entity.people.*;
import sk.stuba.fei.uim.oop.utility.*;

import java.util.*;

public class Project implements sk.stuba.fei.uim.oop.entity.grant.ProjectInterface {
    private String name;
    private int year;
    private Set<PersonInterface> participants = new HashSet<>();
    private Map<Integer, Integer> budgetForYear = new HashMap<>();
    private int budget_t = 0, budget = 0;
    private OrganizationInterface organization;

    @Override
    public String getProjectName() {
        return name;
    }

    @Override
    public void setProjectName(String name) {
        this.name = name;
    }

    @Override
    public int getStartingYear() {
        return year;
    }

    @Override
    public void setStartingYear(int year) {
        this.year = year;
    }

    @Override
    public int getEndingYear() {
        return year + (Constants.PROJECT_DURATION_IN_YEARS - 1);
    }

    @Override
    public int getBudgetForYear(int year) {
        return budgetForYear.getOrDefault(year, 0);
    }
    public int setBudget_t(int budget) {
        this.budget_t = budget;
        this.budget = budget_t / Constants.PROJECT_DURATION_IN_YEARS;
        for (int i = this.getStartingYear(); i < year+Constants.PROJECT_DURATION_IN_YEARS; i++) {
            budgetForYear.put(i, budget);
        }
        return budget_t;
    }
    @Override
    public void setBudgetForYear(int year, int budget) {
        budgetForYear.put(year, budget);
//        for (int i = year+1; i < year+Constants.PROJECT_DURATION_IN_YEARS; i++) {
//            budgetForYear.put(i, budget);
//        }
    }

    @Override
    public int getTotalBudget() {
         budget_t = 0;
         for (int i = this.getStartingYear(); i <= year+Constants.PROJECT_DURATION_IN_YEARS; i++) {
//             this.getApplicant().projectBudgetUpdateNotification(this, i, budgetForYear.get(i));
             budget_t += budgetForYear.getOrDefault(i, 0);
         }
        return budget_t;
    }


    @Override
    public void addParticipant(PersonInterface participant) {
        if (organization.getEmployees().contains(participant)) {
            participants.add(participant);
        }
    }

    @Override
    public Set<PersonInterface> getAllParticipants() {
        return participants;
    }

    @Override
    public OrganizationInterface getApplicant() {
        return organization;
    }

    @Override
    public void setApplicant(OrganizationInterface applicant) {
        this.organization = applicant;
    }
}


