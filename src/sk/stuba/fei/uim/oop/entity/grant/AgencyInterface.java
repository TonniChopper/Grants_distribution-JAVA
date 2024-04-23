package sk.stuba.fei.uim.oop.entity.grant;

import java.util.Set;

public interface AgencyInterface {

    /**
     * @return the name of the agency.
     */
    String getName();

    /**
     * This parameter should be used to distinguish one agency from another one.
     * @param name of the agency which is unique.
     */
    void setName(String name);

    /**
     *
     * @param gi - instance of a GrantInterface implementation.
     * @param year - each grant is issued in a specific year (in a <strong>yyyy</strong> format).
     */
    void addGrant(GrantInterface gi, int year);

    /**
     *
     * @return all grants, regardless of their year of issue.
     */
    Set<GrantInterface> getAllGrants();

    /**
     *
     * @param year in a <strong>yyyy</strong> format.
     * @return only the grants issued in a specific year.
     */
    Set<GrantInterface> getGrantsIssuedInYear(int year);


}
