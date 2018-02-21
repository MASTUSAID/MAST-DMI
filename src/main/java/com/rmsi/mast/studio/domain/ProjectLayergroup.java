

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the project_layergroup database table.
 * 
 */
@Entity
@Table(name="la_ext_projectlayergroupmapping")
public class ProjectLayergroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    @Id
		@SequenceGenerator(name="pk_la_ext_projectlayergroupmapping",sequenceName="la_ext_projectlayergroupmapping_projectlayergroupid_seq", allocationSize=1) 
		@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_projectlayergroupmapping") 
		@Column(name="projectlayergroupid")
	    private Integer projectlayergroupid;
	
	    private boolean isactive;
	 
	    private Integer createdby;
		private Integer modifiedby;
		
		@Temporal( TemporalType.DATE)
		private Date createddate;
		
		@Temporal( TemporalType.DATE)
	    private Date modifieddate;
	    
		@ManyToOne
		@JoinColumn(name="layergroupid")
	    private Layergroup layergroupBean;
		
		
		
		//@JsonIgnore
	    @ManyToOne
		@JoinColumn(name="projectnameid")
		private Project projectBean;
	 
    
		public ProjectLayergroup() {
    
        }
		
				
			@JsonIgnore
			public Project getProjectBean() {
				return this.projectBean;
			}
			
			public void setProjectBean(Project projectBean) {
				this.projectBean = projectBean;
			}
	
		
		


		public Integer getCreatedby() {
				return createdby;
			}


			public void setCreatedby(Integer createdby) {
				this.createdby = createdby;
			}


			public Integer getModifiedby() {
				return modifiedby;
			}


			public void setModifiedby(Integer modifiedby) {
				this.modifiedby = modifiedby;
			}


		public Date getCreateddate() {
			return createddate;
		}


		public void setCreateddate(Date createddate) {
			this.createddate = createddate;
		}


		public Date getModifieddate() {
			return modifieddate;
		}


		public void setModifieddate(Date modifieddate) {
			this.modifieddate = modifieddate;
		}


		public Boolean getIsactive() {
			return isactive;
		}


		public void setIsactive(Boolean isactive) {
			this.isactive = isactive;
		}


		public void setIsactive(boolean isactive) {
			this.isactive = isactive;
		}


		public Layergroup getLayergroupBean() {
			return layergroupBean;
		}


		public void setLayergroupBean(Layergroup layergroupBean) {
			this.layergroupBean = layergroupBean;
		}


		public Integer getProjectlayergroupid() {
			return projectlayergroupid;
		}


		public void setProjectlayergroupid(Integer projectlayergroupid) {
			this.projectlayergroupid = projectlayergroupid;
		}

		
		
    		
		
		
}