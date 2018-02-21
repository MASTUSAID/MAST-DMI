package com.rmsi.mast.viewer.dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.AllocateUser;
import com.rmsi.mast.studio.domain.fetch.La_spatialunit_aoi;
import com.rmsi.mast.viewer.dao.AllocateUserDao;


@Repository
@Transactional
public class AllocateUserHibernateDao extends GenericHibernateDAO<AllocateUser, Long>
implements AllocateUserDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<AllocateUser> getAllocateUser() {
		List<AllocateUser> lstAllocateUsers = new ArrayList<AllocateUser>();
		try {
			
			String query = "Select lu.userid, lu.username from la_ext_user lu inner join la_ext_userrolemapping lm on lu.userid = lm.userid where lm.roleid = 1 order by lu.userid";
			
			List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
			for(Object [] object : arrObject){
				AllocateUser allocateUser = new AllocateUser();
				
				allocateUser.setUserid(Long.valueOf(object[0].toString()));
				allocateUser.setName((String)object[1]);
				
				lstAllocateUsers.add(allocateUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return lstAllocateUsers;
	}

	@Override
	public boolean updateUserAllocation(String[] userId, String[] allocID, long prjid, int created_by, int modifiedby, int applicationstatusid) {
		String resultuser = "";
		String resultallocID = "";
        if (userId != null && userId.length > 0) {
            StringBuilder sb = new StringBuilder();

            for (String s : userId) {
                sb.append(s).append(",");
            }

            resultuser = sb.deleteCharAt(sb.length() - 1).toString();
        }

        if (allocID != null && allocID.length > 0) {
            StringBuilder sb = new StringBuilder();

            for (String s : allocID) {
                sb.append(s).append(",");
            }

            resultallocID = sb.deleteCharAt(sb.length() - 1).toString();
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Calendar cal = Calendar.getInstance();
        
        String created_date ="";
		String modified_date ="";
		try {
			created_date = dateFormat.format(new Date());
			modified_date = dateFormat.format(new Date());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        try {
			String strquery = "update la_spatialunit_aoi set userid =" + userId[0] + ", projectnameid = " + prjid + ", applicationstatusid = "+ applicationstatusid +", createdby = "+created_by+", modifiedby ="+ modifiedby +", createddate = '"+ created_date +"', modifieddate = '"+ modified_date +"' where aoiid in (" + resultallocID + ") ";
			
			 int i=getEntityManager().createNativeQuery(strquery).executeUpdate();
			 if(i>0){  
				 return true;
			 }else{
				 return false;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        
        
		
	}
	
		@Override
		public boolean updateUserAllocationAoi( String[] allocID,  String aoiName) {// ADD HERE String aoiName from Controller to this Hibernate Dao

			String resultallocID = "";
	       
	        if (allocID != null && allocID.length > 0) {
	            StringBuilder sb = new StringBuilder();

	            for (String s : allocID) {
	                sb.append(s).append(",");
	            }

	            resultallocID = sb.deleteCharAt(sb.length() - 1).toString();
	        }
	        
	        try {
				String strquery = "update la_spatialunit_aoi set aoiname = '" + aoiName + "' where aoiid in (" + resultallocID + ") ";
				
				 int i=getEntityManager().createNativeQuery(strquery).executeUpdate();
				 if(i>0){  
					 return true;
				 }else{
					 return false;
				 }
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	        
	        
			
		}
		
	
		@Override
		public List<La_spatialunit_aoi> getResourceAllInfo(String[] allocID) {// ADD HERE String aoiid from Controller to this Hibernate Dao

			String resultallocID = "";
	       
	        if (null != allocID && allocID.length > 0) {
	            StringBuilder sb = new StringBuilder();

	            for (String s : allocID) {
	                sb.append(s).append(",");
	            }

	            resultallocID = sb.deleteCharAt(sb.length() - 1).toString();
	        }
	        
	        try {
				String strquery = "select LU.name, AI.aoiname,AI.aoiid ,PR.projectname from la_spatialunit_aoi AI "+
						 "inner join la_ext_user LU on AI.userid = LU.userid "+
						 "inner join la_spatialsource_projectname PR on AI.projectnameid = PR.projectnameid "+ 
                         "where AI.aoiid in (" + resultallocID +")";
				List<La_spatialunit_aoi> lstLa_spatialunit_aoi = new ArrayList<La_spatialunit_aoi>();
				 @SuppressWarnings("unchecked")
				List<Object[]> arrObject = getEntityManager().createNativeQuery(strquery).getResultList();
				 for(Object [] object : arrObject){
					 La_spatialunit_aoi La_spatialunit_aoi = new La_spatialunit_aoi();
					 La_spatialunit_aoi.setUsername(object[0].toString());
					 La_spatialunit_aoi.setAoiname((String)object[1]);
					 La_spatialunit_aoi.setAoiid(Integer.valueOf(object[2].toString()));
					 La_spatialunit_aoi.setProjectName(object[3].toString());
					 lstLa_spatialunit_aoi.add(La_spatialunit_aoi);
				 }
				  
					 return lstLa_spatialunit_aoi;
					 
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	        //
}
