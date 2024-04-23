package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.entity.grant.GrantInterface;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.factory.DataFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LinkedList<AgencyInterface> agencies;
        LinkedList<GrantInterface> grants;
        LinkedList<OrganizationInterface> organizations;
        LinkedList<ProjectInterface> projects;
        LinkedList<PersonInterface> persons;
        agencies = DataFactory.getAgencies(2);
        agencies.get(0).setName("VEGA");
        agencies.get(1).setName("APVV");

        grants = DataFactory.getGrants(4);
        List<Integer> grantBudgets = Arrays.asList(100000, 8000, 100000, 60000);
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

        List<OrganizationInterface> projectOrganizations = Arrays.asList(organizations.get(0), organizations.get(1), organizations.get(2), organizations.get(2), organizations.get(0), organizations.get(1), organizations.get(1), organizations.get(2));
        List<Integer> projectYears = Arrays.asList(2022, 2023, 2024, 2024, 2024, 2024, 2024, 2024);
        projects = DataFactory.getProjects(8);
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


        ProjectInterface P1 = projects.get(0);
        /*P1.addParticipant(persons.get(0));
        P1.addParticipant(persons.get(1));
        P1.setStartingYear(2022);*/
        GrantInterface grant1 = grants.get(0);
        grant1.callForProjects();
        grant1.registerProject(P1);
        grant1.evaluateProjects();
        grant1.closeGrant();


        ProjectInterface P2 = projects.get(1);
        GrantInterface grant2 = grants.get(1);
        grant2.callForProjects();
        grant2.registerProject(P2);
        grant2.evaluateProjects();
        grant2.closeGrant();
        System.out.println(P2.getTotalBudget());
        System.out.println(grant2.getRemainingBudget());

        ProjectInterface P3 =  projects.get(2);
        /*P3.addParticipant(persons.get(0));
        P3.setStartingYear(2024);*/
        GrantInterface grant3 = grants.get(2);
        grant3.callForProjects();
        grant3.registerProject(P3);
        grant3.evaluateProjects();
        grant3.closeGrant();

        System.out.println("P3 totalbudget = " + P3.getTotalBudget());
        System.out.println(grant3.getRemainingBudget());

        ProjectInterface P4 =  projects.get(3);
        ProjectInterface P5 =  projects.get(4);
        ProjectInterface P6 =  projects.get(5);
        ProjectInterface P7 =  projects.get(6);
        ProjectInterface P8 =  projects.get(7);
        P8.addParticipant(persons.get(0));
        GrantInterface grant4 = grants.get(3);
        grant4.callForProjects();
        System.out.println("REGISTER PROJECT P(4): " + grant4.registerProject(P4));
        /*System.out.println("REGISTER PROJECT P(8): " + grant4.registerProject(P8));*/
        System.out.println("REGISTER PROJECT P(5): " + grant4.registerProject(P5));
        System.out.println("REGISTER PROJECT P(6): " + grant4.registerProject(P6));
        System.out.println("REGISTER PROJECT P(7): " + grant4.registerProject(P7));
        grant4.evaluateProjects();
        grant4.closeGrant();
        System.out.println("BUDGET PROJECT P(4): " + P4.getTotalBudget());
        System.out.println("BUDGET PROJECT P(5): " + P5.getTotalBudget());
        System.out.println("BUDGET PROJECT P(6): " + P6.getTotalBudget());
        System.out.println("BUDGET PROJECT P(7): " + P7.getTotalBudget());
        /*System.out.println("BUDGET PROJECT P(8): " + P8.getTotalBudget());*/
        System.out.println(P4.getBudgetForYear(2024));
        System.out.println(P4.getBudgetForYear(2025));
        System.out.println(P4.getBudgetForYear(2026));
        System.out.println(P4.getBudgetForYear(2027));
        System.out.println(P4.getApplicant().getBudgetForAllProjects());
    }
}