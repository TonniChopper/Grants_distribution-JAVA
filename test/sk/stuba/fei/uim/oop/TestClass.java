package sk.stuba.fei.uim.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.oop.entity.grant.*;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.factory.DataFactory;
import sk.stuba.fei.uim.oop.utility.Constants;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestClass {

    private LinkedList<AgencyInterface> agencies;
    private LinkedList<GrantInterface> grants;
    private LinkedList<OrganizationInterface> organizations;
    private LinkedList<ProjectInterface> projects;
    private LinkedList<PersonInterface> persons;

    @BeforeEach
    void setUp() {
        Constants.PROJECT_DURATION_IN_YEARS = 4;
        Constants.MAX_EMPLOYMENT_PER_AGENCY = 5;
        Constants.COMPANY_INIT_OWN_RESOURCES = 20_000;

        agencies = DataFactory.getAgencies(2);
        agencies.get(0).setName("VEGA");
        agencies.get(1).setName("APVV");
        grants = DataFactory.getGrants(3);
        //VEGA = 200k, APVV = 10k
        List<Integer> grantBudgets = Arrays.asList(200_000, 200_000, 10_000);
        List<AgencyInterface> grantAgencies = Arrays.asList(agencies.get(0), agencies.get(0), agencies.get(0));
        List<Integer> grantYears = Arrays.asList(2022, 2022, 2026);
        for (int i = 0; i < grants.size(); i++) {
            GrantInterface grant = grants.get(i);
            grant.setIdentifier(String.valueOf(i + 1));
            grant.setAgency(grantAgencies.get(i));
            grantAgencies.get(i).addGrant(grant, grantYears.get(i));
            grant.setBudget(grantBudgets.get(i));
            grant.setYear(grantYears.get(i));
        }
        List<String> organizationNames = Arrays.asList("STU", "Eset");
        organizations = new LinkedList<>();
        organizations.addAll(DataFactory.getUniversities(1));
        organizations.addAll(DataFactory.getCompanies(1));
        for (int i = 0; i < organizations.size(); i++) {
            organizations.get(i).setName(organizationNames.get(i));
        }
        List<String> personNames = Arrays.asList("Peter", "Jozef", "Anna", "Karol");
        persons = DataFactory.getPersons(personNames.size());
        for (int i = 0; i < persons.size(); i++) {
            PersonInterface person = persons.get(i);
            person.setName(personNames.get(i));
            person.setAddress("Address " + (i + 1));
        }
        // STU Peter, Jozef
        organizations.get(0).addEmployee(persons.get(0), 2);//Peter
        persons.get(0).addEmployer(organizations.get(0));
        organizations.get(0).addEmployee(persons.get(1), 2);//Jozef
        persons.get(1).addEmployer(organizations.get(0));
        organizations.get(0).addEmployee(persons.get(2), 2);//Anna
        persons.get(2).addEmployer(organizations.get(0));

        // Eset Peter, Anna, Karol
        organizations.get(1).addEmployee(persons.get(0), 2); //Peter
        persons.get(0).addEmployer(organizations.get(1));
        organizations.get(1).addEmployee(persons.get(2), 1); // Anna
        persons.get(2).addEmployer(organizations.get(1));
        organizations.get(1).addEmployee(persons.get(3), 3); // Karol
        persons.get(3).addEmployer(organizations.get(1));

        //get(0) = STU
        //get(1) = ESET


        List<OrganizationInterface> projectOrganizations = Arrays.asList(organizations.get(0), organizations.get(1), organizations.get(0), organizations.get(1), organizations.get(1), organizations.get(0));
        List<Integer> projectYears = Arrays.asList(2022, 2022, 2022, 2026, 2026, 2027);
        //("Peter", "Jozef", "Anna", "Karol");
        projects = DataFactory.getProjects(6);
        for (int i = 0; i < projects.size(); i++) {
            ProjectInterface proj = projects.get(i);
            proj.setProjectName("P" + (i + 1));
            OrganizationInterface applicant = projectOrganizations.get(i);
            proj.setApplicant(applicant);
            applicant.registerProjectInOrganization(projects.get(i));
            proj.setStartingYear(projectYears.get(i));
        }
        //P1
        projects.get(0).addParticipant(persons.get(0)); // Peter
        projects.get(0).addParticipant(persons.get(2)); // Anna

        //P2
        projects.get(1).addParticipant(persons.get(0)); // Anna
        projects.get(1).addParticipant(persons.get(3)); // Karol
        //P3
        projects.get(2).addParticipant(persons.get(2)); // Anna

        //P4
        projects.get(3).addParticipant(persons.get(0)); // Peter


        //P5
        projects.get(4).addParticipant(persons.get(2)); // Anna
        projects.get(4).addParticipant(persons.get(3)); // Karol

        //P6
        projects.get(5).addParticipant(persons.get(2)); // Anna
        projects.get(5).addParticipant(persons.get(3)); // Karol
        projects.get(5).addParticipant(persons.get(0)); // Peter



    }

    @Test
    void testGrantState() {
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        grant1.closeGrant();
        assertEquals(GrantState.STARTED, grant1.getState());

        grant1.evaluateProjects();
        assertEquals(GrantState.EVALUATING, grant1.getState());

        grant1.closeGrant();
        assertEquals(GrantState.CLOSED, grant1.getState());

        GrantInterface grant2 = grants.get(0);
        assertEquals(GrantState.CLOSED, grant2.getState());

        grant2 = grants.get(1);
        assertNull(grant2.getState());

        grant2.evaluateProjects();
        assertNull(grant2.getState());

        grant2.callForProjects();
        grant2.evaluateProjects();
        grant2.closeGrant();
        grant2.callForProjects();

        assertEquals(GrantState.CLOSED, grant2.getState());

    }

    @Test
    void testRegistration() {
        ProjectInterface project1 = projects.get(0);
        GrantInterface grant = grants.get(2);
        grant.callForProjects();
        assertFalse(grant.registerProject(project1));


        grant = grants.get(0);
        grant.callForProjects();
        assertTrue(grant.registerProject(project1));
        assertFalse(grant.registerProject(projects.get(4)));
        grant.registerProject(project1);
        assertEquals(1, grant.getRegisteredProjects().size());


        GrantInterface grant2 = grants.get(1);
        ProjectInterface project2 = projects.get(1);
        ProjectInterface project3 = projects.get(2);
        grant2.callForProjects();
        assertTrue(grant2.registerProject(project1));
        assertTrue(grant2.registerProject(project2));
        assertTrue(grant2.registerProject(project3));
        assertEquals(Set.of(project1, project2, project3),grant2.getRegisteredProjects());

    }

    @Test
    void testEvaluateProjects() {
        //запись в грант1 P1, P2. После евалуейт пытаемся записать P3;
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        grant1.registerProject(projects.get(0));
        grant1.registerProject(projects.get(1));
        grant1.evaluateProjects();


        assertFalse(grant1.registerProject(projects.get(3)));
        grant1.closeGrant();
        assertEquals(200_000, grant1.getBudgetForProject(projects.get(0)));
        assertEquals(0, grant1.getBudgetForProject(projects.get(1)));


        GrantInterface grant2 = grants.get(1);
        grant2.callForProjects();
        projects.get(4).setStartingYear(grant2.getYear());
        projects.get(3).setStartingYear(grant2.getYear());
        grant2.registerProject(projects.get(3));//P4
        grant2.registerProject(projects.get(4));//P5




        //Зарегали 4 проекта. Должно дать по 100к первым двум
        grant2.evaluateProjects();


        grant2.closeGrant();
        assertEquals(200000, grant2.getBudgetForProject(projects.get(3)));
        assertEquals(0, grant2.getBudgetForProject(projects.get(4)));

        GrantInterface grant3 = grants.get(2);
        grant3.setYear(2023);

        grant3.callForProjects();
        projects.get(5).setStartingYear(grant3.getYear());
        grant3.registerProject(projects.get(5));
        grant3.evaluateProjects();
        grant3.closeGrant();

         assertEquals(0, grant3.getBudgetForProject(projects.get(5)));


    }

    @Test
    void testEmptyProject() {
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        ProjectInterface emptyProject = new Project();
        emptyProject.setStartingYear(grant1.getYear());
        OrganizationInterface applicant = organizations.get(0);//STU
        emptyProject.setApplicant(applicant);;
        emptyProject.setProjectName("EMPTY");
        applicant.registerProjectInOrganization(emptyProject);


        assertFalse(grant1.registerProject(emptyProject));
    }

    @Test
    void testCloseGrant() {
        Constants.COMPANY_INIT_OWN_RESOURCES = 9999;
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        projects.get(3).setStartingYear(grant1.getYear());
        grant1.callForProjects();
        grant1.registerProject(projects.get(0));//P1
        grant1.registerProject(projects.get(3));//P4
        grant1.registerProject(projects.get(1));//P2
        grant1.registerProject(projects.get(2));//P3
        //Зарегали так же 4 проекта, но теперь проверка на то сколько денег дала компания + грант
        grant1.evaluateProjects();
        grant1.closeGrant();

        int projectBudget1 = projects.get(0).getApplicant().getProjectBudget(projects.get(0));
        int projectBudget2 = projects.get(3).getApplicant().getProjectBudget(projects.get(3));

        assertEquals(100_000, projectBudget1);
        assertEquals(109_999, projectBudget2);


        GrantInterface grant2 = grants.get(1);
        grant2.callForProjects();
        grant2.setYear(projects.get(4).getStartingYear());

        grant2.registerProject(projects.get(4));//P5
        assertFalse(grant2.registerProject(projects.get(5)));//P6


        grant2.evaluateProjects();
        grant2.closeGrant();

        int project4budget = projects.get(4).getApplicant().getProjectBudget(projects.get(4));
        int project4total = projects.get(4).getTotalBudget();

        assertEquals(200_000, project4total);//P5 бабки от гранта
        assertEquals(200_000, project4budget);//P5 бабки от гранта + компании
        assertEquals(50_000, projects.get(4).getBudgetForYear(2026));
    }

}