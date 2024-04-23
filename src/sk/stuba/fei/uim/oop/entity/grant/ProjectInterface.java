package sk.stuba.fei.uim.oop.entity.grant;

import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.Set;

public interface ProjectInterface {

    /**
     * @return the name of the project.
     */
    String getProjectName();

    /**
     * This parameter should be used to distinguish one project from another one.
     * @param name unique name of the project.
     */
    void setProjectName(String name);

    /**
     * @return the starting year of the project.
     */
    int getStartingYear();

    /**
     * @param year in a <strong>yyyy</strong> format.
     */
    void setStartingYear(int year);

    /**
     * The ending year of the project is determined by the <strong>PROJECT_DURATION_IN_YEARS</strong> value, defined in the <strong>Constants</> class.
     * The ending year is the last year in which the project is still running.
     * @return the ending year of the project.
     */
    int getEndingYear();

    /**
     * @param year in a <strong>yyyy</strong> format.
     * @return the funding allocated for the given year.
     */
    int getBudgetForYear(int year);

    /**
     * Notification about the funding allocation.
     * The project's budget for the given year is set.
     * @param year in a <strong>yyyy</strong> format.
     * @param budget the funding allocated for the given year.
     */
    void setBudgetForYear(int year, int budget);

    /**
     * @return the funding allocated for the entire duration of the project.
     */
    int getTotalBudget();

    /**
     * Add a participant to the project. Each participant must be an employee of the applicant organization.
     * The applicant must be set before adding the participants.
     * @param participant instance of a PersonInterface implementation.
     */
    void addParticipant(PersonInterface participant);

    Set<PersonInterface> getAllParticipants();


    OrganizationInterface getApplicant();

    /**
     * Sets the project applicant.
     * @param applicant instance of a OrganizationInterface implementation.
     */
    void setApplicant(OrganizationInterface applicant);


}
