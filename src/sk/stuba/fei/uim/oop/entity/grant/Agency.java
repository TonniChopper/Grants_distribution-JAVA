package sk.stuba.fei.uim.oop.entity.grant;
import sk.stuba.fei.uim.oop.entity.grant.*;
import sk.stuba.fei.uim.oop.utility.*;
import sk.stuba.fei.uim.oop.entity.organization.*;
import sk.stuba.fei.uim.oop.entity.people.*;
import java.util.*;




public class Agency implements AgencyInterface{
    private String name;
    private Set<GrantInterface> grants;

    public Agency() {
        this.grants = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addGrant(GrantInterface gi, int year) {
        gi.setYear(year);
        this.grants.add(gi);
    }

    public Set<GrantInterface> getAllGrants() {
        return grants;
    }

    public Set<GrantInterface> getGrantsIssuedInYear(int year) {
        Set<GrantInterface> grantsIssuedInYear = new HashSet<>();
        for (GrantInterface grant : grants) {
            if (grant.getYear() == year) {
                grantsIssuedInYear.add(grant);
            }
        }
        return grantsIssuedInYear;
    }
}
