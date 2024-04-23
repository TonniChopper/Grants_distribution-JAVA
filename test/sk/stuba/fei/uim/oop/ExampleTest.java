package sk.stuba.fei.uim.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.entity.grant.GrantInterface;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.factory.DataFactory;
import sk.stuba.fei.uim.oop.utility.Constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleTest {

    private LinkedList<AgencyInterface> agencies;
    private LinkedList<GrantInterface> grants;
    private LinkedList<OrganizationInterface> organizations;
    private LinkedList<ProjectInterface> projects;
    private LinkedList<PersonInterface> persons;

    @BeforeEach
    void setUp() {
        agencies = DataFactory.getAgencies(2);
        agencies.get(0).setName("VEGA");
        agencies.get(1).setName("APVV");

        grants = DataFactory.getGrants(4);
        List<Integer> grantBudgets = Arrays.asList(100000, 8000, 100000, 8000);
        List<AgencyInterface> grantAgencies = Arrays.asList(agencies.get(1), agencies.get(0), agencies.get(1), agencies.get(0));
        List<Integer> grantYears = Arrays.asList(2022, 2023, 2024, 2024);
        for (int i = 0; i < grants.size(); i++) {
            GrantInterface grant = grants.get(i);
            grant.setIdentifier(String.valueOf(i + 1));
            grant.setAgency(grantAgencies.get(i));
            grantAgencies.get(i).addGrant(grant, grantYears.get(i));
            grant.setBudget(grantBudgets.get(i));
            grant.setYear(grantYears.get(i));
        }

        List<String> organizationNames = Arrays.asList("STU", "UK", "Eset");
        organizations = new LinkedList<>();
        organizations.addAll(DataFactory.getUniversities(2));
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
        organizations.get(0).addEmployee(persons.get(0), 2);
        persons.get(0).addEmployer(organizations.get(0));
        organizations.get(0).addEmployee(persons.get(1), 3);
        persons.get(1).addEmployer(organizations.get(0));
        // UK Anna, Karol
        organizations.get(1).addEmployee(persons.get(2), 3);
        persons.get(2).addEmployer(organizations.get(1));
        organizations.get(1).addEmployee(persons.get(3), 1);
        persons.get(3).addEmployer(organizations.get(1));
        // Eset Peter, Anna, Karol
        organizations.get(2).addEmployee(persons.get(0), 4);
        persons.get(0).addEmployer(organizations.get(2));
        organizations.get(2).addEmployee(persons.get(2), 2);
        persons.get(2).addEmployer(organizations.get(2));
        organizations.get(2).addEmployee(persons.get(3), 1);
        persons.get(3).addEmployer(organizations.get(2));

        List<OrganizationInterface> projectOrganizations = Arrays.asList(organizations.get(0), organizations.get(1), organizations.get(2), organizations.get(2), organizations.get(0), organizations.get(1), organizations.get(1));
        List<Integer> projectYears = Arrays.asList(2022, 2023, 2024, 2024, 2024, 2024, 2024);

        projects = DataFactory.getProjects(7);
        for (int i = 0; i < projects.size(); i++) {
            ProjectInterface proj = projects.get(i);
            proj.setProjectName("P" + (i + 1));
            OrganizationInterface applicant = projectOrganizations.get(i);
            proj.setApplicant(applicant);
            applicant.registerProjectInOrganization(projects.get(i));
            proj.setStartingYear(projectYears.get(i));
        }
        projects.get(0).addParticipant(persons.get(0)); // Peter
        projects.get(0).addParticipant(persons.get(1)); // Jozef
        projects.get(1).addParticipant(persons.get(2)); // Anna
        projects.get(1).addParticipant(persons.get(3)); // Karol
        projects.get(2).addParticipant(persons.get(0)); // Peter
        projects.get(3).addParticipant(persons.get(2)); // Anna
        projects.get(3).addParticipant(persons.get(3)); // Karol
        projects.get(4).addParticipant(persons.get(0)); // Peter
        projects.get(4).addParticipant(persons.get(1)); // Jozef
        projects.get(5).addParticipant(persons.get(2)); // Anna
        projects.get(6).addParticipant(persons.get(3)); // Karol
    }

    @Test
    void testProjectEndingYears() {
        // project ending year test
        List<Integer> projectYears = Arrays.asList(2022, 2023, 2024, 2024, 2024, 2024, 2024);
        for (int i = 0; i < projects.size(); i++) {
            assertEquals(projectYears.get(i) + (Constants.PROJECT_DURATION_IN_YEARS - 1), projects.get(i).getEndingYear());
        }
    }

    @Test
    void testEmployerEmployeeRelation() {
        // if employer and employee relations are set correctly
        //Peter
        assertTrue(organizations.get(0).getEmployees().contains(persons.get(0)));
        assertFalse(organizations.get(1).getEmployees().contains(persons.get(0)));
        assertTrue(organizations.get(2).getEmployees().contains(persons.get(0)));
        //Jozef
        assertTrue(organizations.get(0).getEmployees().contains(persons.get(1)));
        assertFalse(organizations.get(1).getEmployees().contains(persons.get(1)));
        assertFalse(organizations.get(2).getEmployees().contains(persons.get(1)));
        //Anna
        assertFalse(organizations.get(0).getEmployees().contains(persons.get(2)));
        assertTrue(organizations.get(1).getEmployees().contains(persons.get(2)));
        assertTrue(organizations.get(2).getEmployees().contains(persons.get(2)));
        //Karol
        assertFalse(organizations.get(0).getEmployees().contains(persons.get(3)));
        assertTrue(organizations.get(1).getEmployees().contains(persons.get(3)));
        assertTrue(organizations.get(2).getEmployees().contains(persons.get(3)));

        // Peter STU, Eset
        assertTrue(persons.get(0).getEmployers().containsAll(Arrays.asList(organizations.get(0),organizations.get(2))));
        // Jozef STU
        assertTrue(persons.get(1).getEmployers().containsAll(Arrays.asList(organizations.get(0))));
        // Anna UK, Eset
        assertTrue(persons.get(2).getEmployers().containsAll(Arrays.asList(organizations.get(1),organizations.get(2))));
        // Karol UK, Eset
        assertTrue(persons.get(3).getEmployers().containsAll(Arrays.asList(organizations.get(1),organizations.get(2))));
    }

    @Test
    void testProjectRegistrationReturnValue(){
        // the registration return value should be true only between the callForProjects, and
        // the evaluateProjects methods
        ProjectInterface pi = projects.getFirst();
        GrantInterface gi = grants.getFirst();
        boolean reg = gi.registerProject(pi);
        assertFalse(reg);
        gi.callForProjects();
        reg = gi.registerProject(pi);
        gi.evaluateProjects();
        assertTrue(reg);
        gi.closeGrant();
        reg = gi.registerProject(pi);
        assertFalse(reg);
    }

    @Test
    void testAllProjectsPassed() {
        Constants.PROJECT_DURATION_IN_YEARS = 2;
        // non overlapping projects, both projects will pass the test

        ProjectInterface P1 = projects.get(0);
        P1.addParticipant(persons.get(0));
        P1.addParticipant(persons.get(1));
        P1.setStartingYear(2022);
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        grant1.registerProject(P1);
        grant1.evaluateProjects();
        grant1.closeGrant();
        // the registered project must obtain the full grant budget
        assertEquals(100000, grant1.getBudgetForProject(P1));

        ProjectInterface P3 =  projects.get(2);
        P3.addParticipant(persons.get(0));
        P3.setStartingYear(2024);
        GrantInterface grant2 = grants.get(2);
        grant2.callForProjects();
        grant2.registerProject(P3);
        grant2.evaluateProjects();
        grant2.closeGrant();
        // the registered project must obtain the full grant budget
        // the grant doesn't know about the company dotation
        assertEquals(100000, grant2.getBudgetForProject(P3));
        Constants.PROJECT_DURATION_IN_YEARS = 4;
        Constants.MAX_EMPLOYMENT_PER_AGENCY = 5;
    }
}
