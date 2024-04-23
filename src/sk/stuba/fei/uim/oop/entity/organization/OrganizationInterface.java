package sk.stuba.fei.uim.oop.entity.organization;

import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.Set;

public interface OrganizationInterface {


    /**
     *
     * @return the name of the organization.
     */
    String getName();

    /**
     * This parameter should be used to distinguish one organization from another one.
     *
     * @param name unique name of the organization.
     */
    void setName(String name);

    /**
     * A new employee is hired with a specific employment.
     *
     * @param p instance of a PersonInterface implementation.
     * @param employment yearly per project employment of the employee in the organization.
     */
    void addEmployee(PersonInterface p, int employment);

    /**
     * @return all employees of the organization.
     */
    Set<PersonInterface> getEmployees();

    /**
     *
     * @param p instance of a PersonInterface implementation.
     * @return the yearly per project employment of the employee.
     */
    int getEmploymentForEmployee(PersonInterface p);

    /**
     * @return all projects registered at the organization.
     */
    Set<ProjectInterface> getAllProjects();

    /**
     *
     * @param year in a <strong>yyyy</strong> format.
     * @return all projects registered at the organization and running in a given year.
     */
    Set<ProjectInterface> getRunningProjects(int year);

    /**
     * New project is registered at the organization.
     * @param project instance of a ProjectInterface implementation.
     */
    void registerProjectInOrganization(ProjectInterface project);

    /**
     * If the OrganizationInterface implementation is a '<strong>university</strong>' type, the project is funded only by the grant agency.
     * If the OrganizationInterface implementation is a '<strong>company</strong>' type,
     * the project is funded by the grant agency and by the project applicant organization (company).
     * Each company has constant budget <strong>COMPANY_INIT_OWN_RESOURCES</strong> defined in the <strong>Constants</strong> class,
     * that can be used to fund their projects.
     * The company will annually support the projects with the same amount as the grant agency until its funds are exhausted.
     * If the project is not registered at the organization, the method should return <strong>0</strong>.
     *
     * @param pi instance of a ProjectInterface implementation.
     * @return the total funding for the given project.
     */
    int getProjectBudget(ProjectInterface pi);

    /**
     *
     * @return the total budget for all projects registered at the organization.
     */
    int getBudgetForAllProjects();

    /**
     * This method should be called when the notification about the grant agency funding allocation is received in a project.
     *
     * @param pi instance of a ProjectInterface implementation.
     * @param year in a <strong>yyyy</strong> format.
     * @param budgetForYear funding allocated by the grant agency for the given project for the given year.
     */
    void projectBudgetUpdateNotification(ProjectInterface pi, int year, int budgetForYear);
}
