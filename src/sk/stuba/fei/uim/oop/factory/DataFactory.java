package sk.stuba.fei.uim.oop.factory;

import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.entity.grant.GrantInterface;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.entity.grant.*;
import sk.stuba.fei.uim.oop.entity.organization.*;
import sk.stuba.fei.uim.oop.entity.people.*;
import java.util.LinkedList;

public class DataFactory {

    public static LinkedList<AgencyInterface> getAgencies(int count) {
        LinkedList<AgencyInterface> retVal = new LinkedList<>();
        for (int i = 0; i < count; i++) {
          AgencyInterface agency = new Agency();
          retVal.add(agency);
        }
        return retVal;
    }

    public static LinkedList<GrantInterface> getGrants(int count) {
        LinkedList<GrantInterface> retVal = new LinkedList<>();
        for (int i = 0; i < count; i++) {
          GrantInterface grant = new Grant();
          retVal.add(grant);
        }
        return retVal;
    }

    public static LinkedList<ProjectInterface> getProjects(int count) {
        LinkedList<ProjectInterface> retVal = new LinkedList<>();
        for (int i = 0; i < count; i++) {
          ProjectInterface project = new Project();
          retVal.add(project);
        }
        return retVal;
    }

    public static LinkedList<OrganizationInterface> getUniversities(int count) {
        LinkedList<OrganizationInterface> retVal = new LinkedList<>();
        for (int i = 0; i < count; i++) {
          OrganizationInterface org = new University();
          retVal.add(org);
        }
        return retVal;
    }

    public static LinkedList<OrganizationInterface> getCompanies(int count) {
        LinkedList<OrganizationInterface> retVal = new LinkedList<>();
        for (int i = 0; i < count; i++) {
          OrganizationInterface org = new Company();
          retVal.add(org);
        }
        return retVal;
    }

    public static LinkedList<PersonInterface> getPersons(int count) {
        LinkedList<PersonInterface> retVal = new LinkedList<>();
        for (int i = 0; i < count; i++) {
          PersonInterface person = new Person();
          retVal.add(person);
        }
        return retVal;
    }
}
