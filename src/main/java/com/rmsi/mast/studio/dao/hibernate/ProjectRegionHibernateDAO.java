

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;









import com.rmsi.mast.studio.dao.ProjectRegionDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;


@Repository
public class ProjectRegionHibernateDAO extends GenericHibernateDAO<Project, Long>
		implements ProjectRegionDAO {

	@SuppressWarnings("unchecked")
	public List<ProjectRegion> findAllCountry() {

		List<ProjectRegion> project = new ArrayList<ProjectRegion>();
		String hqlstr="Select p from ProjectRegion p where p.laSpatialunitgroup.spatialunitgroupid=1 ";

		try {
			project =getEntityManager().createQuery(hqlstr).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return project;
	}
	

	@SuppressWarnings("unchecked")
	public List<ProjectRegion> findRegionByCountry(Integer Id) {

		List<ProjectRegion> project = new ArrayList<ProjectRegion>();

		try {
			project =getEntityManager().createQuery("Select p from ProjectRegion p where  p.laSpatialunitgroup.spatialunitgroupid=2 and  p.uperhierarchyid = :Id").setParameter("Id", Id).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return project;
	}
	

	@SuppressWarnings("unchecked")
	public List<ProjectRegion> findDistrictByRegion(Integer Id) {
		List<ProjectRegion> project = new ArrayList<ProjectRegion>();

		try {
			project =getEntityManager().createQuery("Select p from ProjectRegion p where  p.laSpatialunitgroup.spatialunitgroupid=3 and  p.uperhierarchyid = :Id").setParameter("Id", Id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return project;
	}
		
	
		
		
		@SuppressWarnings("unchecked") 
		public List<ProjectRegion> findVillageByDistrict(Integer Id) {
			List<ProjectRegion> project =new ArrayList<ProjectRegion>();

			try {
				project =getEntityManager().createQuery("Select p from ProjectRegion p where  p.laSpatialunitgroup.spatialunitgroupid=4 and  p.uperhierarchyid = :Id").setParameter("Id", Id).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return project;
		} 
	
		@SuppressWarnings("unchecked") 
		public List<ProjectRegion> findPlaceByVillage(Integer Id) {
			List<ProjectRegion> project =new ArrayList<ProjectRegion>();
	
			
			try {
				project =getEntityManager().createQuery("Select p from ProjectRegion p where  p.laSpatialunitgroup.spatialunitgroupid=5 and  p.uperhierarchyid = :Id").setParameter("Id", Id).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			return project;
		}


		@SuppressWarnings("unchecked")
		@Override
		public ProjectRegion findProjectRegionById(Integer id) {
			
			List<ProjectRegion> project =new ArrayList<ProjectRegion>();

			try {
				project =getEntityManager().createQuery("Select p from ProjectRegion p where p.isactive=true and  p.hierarchyid = :Id").setParameter("Id", id).getResultList();
				if(project.size()>0)
					return project.get(0);
				else
					return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}


		@Override
		public List<ProjectRegion> findAllProjectRegion() {
		
			List<ProjectRegion> project =new ArrayList<ProjectRegion>();

			try {
				project =getEntityManager().createQuery("Select p from ProjectRegion p where p.isactive=true").getResultList();
				if(project.size()>0)
					return project;
				else
					return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		} 
		
		
				
				
	
	
}
