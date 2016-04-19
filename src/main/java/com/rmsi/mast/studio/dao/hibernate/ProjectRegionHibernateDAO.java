

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;




import com.rmsi.mast.studio.dao.ProjectRegionDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;


@Repository
public class ProjectRegionHibernateDAO extends GenericHibernateDAO<Project, Long>
		implements ProjectRegionDAO {

	
	
	/* ************@RMSI/NK add for region to district*******************************/
	//@RMSI NK for country name 1
	@SuppressWarnings("unchecked")
	public List<ProjectRegion> findAllCountry() {
		
		//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
		String hqlstr="Select distinct p.countryName from ProjectRegion p ";
		
		//SELECT DISTINCT c.name FROM Customer cSELECT DISTINCT c.name FROM Customer c where p.admincreated=true
		List<ProjectRegion> project =
			getEntityManager().createQuery(hqlstr).getResultList();
	
		
		return project;
	}
	
	//end country
	
	//@RMSI NK for Region by country 2
	@SuppressWarnings("unchecked")
	public List<ProjectRegion> findRegionByCountry(String countryname) {
		
		//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
		//String hqlstr="Select distinct  p from ProjectRegion p where p.countryname = :countryname").setParameter("countryname", countryname)";
		//String hqlstr="Select distinct p.region from ProjectRegion p where p.countryName = :countryName";
		
		
		List<ProjectRegion> project =
			getEntityManager().createQuery("Select distinct  p.region from ProjectRegion p where p.countryName = :countryname").setParameter("countryname", countryname).getResultList();
		
		/*List<ProjectRegion> project =
				getEntityManager().createQuery(hqlstr).getResultList();*/
		
		
	
		
		return project;
	}
	
	//end region
	
	
	//@RMSI NK for District by Region 3
		@SuppressWarnings("unchecked")
		public List<ProjectRegion> findDistrictByRegion(String countryname) {
			
			//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
			//String hqlstr="Select distinct  p from ProjectRegion p where p.countryname = :countryname").setParameter("countryname", countryname)";
			//String hqlstr="Select distinct p.districtName from ProjectRegion p where p.countryName = :countryName";
			
			
			List<ProjectRegion> project =
					getEntityManager().createQuery("Select distinct  p.districtName from ProjectRegion p where p.countryName = :countryname").setParameter("countryname", countryname).getResultList();
				
			/*List<ProjectRegion> project =
					getEntityManager().createQuery(hqlstr).getResultList();*/
			
			
		
			
			return project;
		}
		
		//end district
		
		
		
		//@RMSI NK for village by District 4
				@SuppressWarnings("unchecked") 
				public List<ProjectRegion> findVillageByDistrict(String countryname) {
					
					//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
					//String hqlstr="Select distinct  p from ProjectRegion p where p.countryname = :countryname").setParameter("countryname", countryname)";
					//String hqlstr="Select distinct p.village from ProjectRegion p where p.countryName = :countryName";
					
					
					List<ProjectRegion> project =
							getEntityManager().createQuery("Select distinct p.village from ProjectRegion p where p.countryName = :countryname").setParameter("countryname", countryname).getResultList();
						
					/*List<ProjectRegion> project =
							getEntityManager().createQuery(hqlstr).getResultList();
					*/
					
				
					
					return project;
				} 
		//end village
		
		//@RMSI NK for hamlet by village 5
		@SuppressWarnings("unchecked") 
		public List<ProjectRegion> findHamletByVillage(String countryname) {
			
			//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
			//String hqlstr="Select distinct  p from ProjectRegion p where p.countryname = :countryname").setParameter("countryname", countryname)";
			//String hqlstr="Select distinct p.hamlet from ProjectRegion p where p.countryName = :countryName";
			
			
			List<ProjectRegion> project =
					getEntityManager().createQuery("Select distinct  p.hamlet from ProjectRegion p where p.countryName = :countryname").setParameter("countryname", countryname).getResultList();
				
			/*List<ProjectRegion> project =
					getEntityManager().createQuery(hqlstr).getResultList();
			*/
			
		
			
			return project;
		} 
		
		//end hamlet
				
				
	
	
}
