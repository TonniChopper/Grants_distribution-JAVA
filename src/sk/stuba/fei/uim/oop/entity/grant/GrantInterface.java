package sk.stuba.fei.uim.oop.entity.grant;


import java.util.Set;

public interface GrantInterface {

    /**
     * @return the identifier of the grant.
     */
    String getIdentifier();

    /**
     * This parameter should be used to distinguish one grant from another one.
     * @param identifier of the grant which is unique.
     */
    void setIdentifier(String identifier);

    /**
     * @return the year when the grant was issued.
     */
    int getYear();

    /**
     * Each grant is issued in a specific year.
     * @param year in a <strong>yyyy</strong> format.
     */
    void setYear(int year);

    /**
     *
     * @return the issuer of the grant.
     */
    AgencyInterface getAgency();

    void setAgency(AgencyInterface agency);

    /**
     *
     * @return the allocated budget of the grant. This budget is distributed among the registered projects.
     */
    int getBudget();

    /**
     *
     * @param budget the allocated budget of the grant.
     */
    void setBudget(int budget);

    /**
     * The remaining budget should be updated each time when a part of the funding is allocated to a project.
     * @return the remaining budget of the grant.
     */
    int getRemainingBudget();

    /**
     *
     * @param project instance of a ProjectInterface implementation.
     * @return the budget set for the specific project.
     */
    int getBudgetForProject(ProjectInterface project);

    /**
     * The registration is allowed if and only if the grant is in the <strong>STARTED</strong> state,
     * the project's starting year is the same as the grant's year,
     * and the proposed project has at least one participant.
     * A project should be stored only if the registration was successful.
     * @param project instance of a ProjectInterface implementation.
     * @return <strong>true</strong> if the registration was successful.
     */
    boolean registerProject(ProjectInterface project);

    /**
     * Projects must be sorted according to the order of their registration.
     * @return a set of registered projects.
     */
    Set<ProjectInterface> getRegisteredProjects();

    /**
     * State is specified in the GrantState enum.
     * @return state of the project's lifecycle.
     */
    GrantState getState();


    /**
     * The grant call is issued and registrations are possible.
     */
    void callForProjects();

    /**
     * The registered projects are evaluated according to the order of their registration and the grant's allocated budget is distributed among them.
     * The research capacity of the participants of each project must be checked.
     * If a project fails to pass a check on the research capacity of the participants, the project's funding is <strong>0</strong> and is not included in the funding distribution process.
     * The grant's allocated budget is distributed equally to the half of the projects that have been included in the distribution process.
     * At least one valid project is funded.
     */
    void evaluateProjects();

    /**
     * The grant is closed and all the registered projects are notified regarding the allocated funding to them
     * using the <strong>setBudgetForYear(int year, int budget)</strong> method defined in the <strong>ProjectInterface</strong>.
     * The funding allocated to each project is divided equally in to <strong>PROJECT_DURATION_IN_YEARS</strong> parts defined in the <strong>Constants</> class,
     * which represents the project's yearly funding.
     */
    void closeGrant();


}
