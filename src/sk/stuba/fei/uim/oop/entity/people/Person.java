package sk.stuba.fei.uim.oop.entity.people;
import sk.stuba.fei.uim.oop.entity.organization.*;
import sk.stuba.fei.uim.oop.utility.*;
import sk.stuba.fei.uim.oop.entity.grant.*;
import java.util.*;

public class Person implements PersonInterface{
    private String name;
    private String address;
    private Set<OrganizationInterface> employers;
    public Person() {
        this.employers = new HashSet<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addEmployer(OrganizationInterface organization) {
        employers.add(organization);
    }

    public Set<OrganizationInterface> getEmployers() {
        return employers;
    }
}
