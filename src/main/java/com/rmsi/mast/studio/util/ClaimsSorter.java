package com.rmsi.mast.studio.util;

import com.rmsi.mast.studio.domain.fetch.ClaimSummary;
import java.util.Comparator;

/**
 * Claims sorter, for sorting claims by hamlet and owners alphabet 
 */
public class ClaimsSorter implements Comparator<ClaimSummary> {

    @Override
    public int compare(ClaimSummary claim1, ClaimSummary claim2) {
        String hamlet1 = StringUtils.empty(claim1.getHamletName());
        String hamlet2 = StringUtils.empty(claim2.getHamletName());
        
        // Assuming that claims already sorted by hamlets
        if(!hamlet1.equals(hamlet2)){
            return 0;
        }
        
        String name1 = "";
        String name2 = "";
        
        if(claim1.getNaturalOwners() != null && claim1.getNaturalOwners().size() > 0){
            name1 = StringUtils.empty(claim1.getNaturalOwners().get(0).getFirstName());
        } else if (claim1.getNonNaturalOwners() != null && claim1.getNonNaturalOwners().size() > 0){
            name1 = StringUtils.empty(claim1.getNonNaturalOwners().get(0).getInstututionName());
        }
        
        if(claim2.getNaturalOwners() != null && claim2.getNaturalOwners().size() > 0){
            name2 = StringUtils.empty(claim2.getNaturalOwners().get(0).getFirstName());
        } else if (claim2.getNonNaturalOwners() != null && claim2.getNonNaturalOwners().size() > 0){
            name2 = StringUtils.empty(claim2.getNonNaturalOwners().get(0).getInstututionName());
        }
        
        return name1.compareToIgnoreCase(name2);
    }
    
}
