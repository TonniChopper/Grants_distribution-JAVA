package sk.stuba.fei.uim.oop.entity.people;

import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;

import java.util.Set;

public interface PersonInterface {

    public String getName();

    /**
     * This parameter should be used to distinguish one person from another one.
     * @param name unique name of the person
     */
    public void setName(String name);

    public String getAddress();

    public void setAddress(String address);


    /**
     * Adds an employer.
     * @param organization instance of an OrganizationInterface implementation.
     */
    void addEmployer(OrganizationInterface organization);

    /**
     *
     * @return the employers of the person.
     */
    Set<OrganizationInterface> getEmployers();

}
