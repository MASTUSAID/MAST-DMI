

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the layergroup database table.
 * 
 */
@Entity
@Table(name = "la_ext_layergroup")
public class Layergroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="pk_la_ext_layergroup",sequenceName="la_ext_layergroup_layergroupid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_layergroup")
	@Column(name="layergroupid")
	private Integer layergroupid;
	
	
	@Column(name="layergroupname")
	private String name;
	@Column(name="remarks")
	private String alias;
	
	@Column(name="isactive")
	private Boolean isactive;
	private Long createdby;
	private Long modifiedby;
	private Date createddate;
    private Date modifieddate;
   
    
   
  	@OneToMany(mappedBy="layergroupBean", fetch = FetchType.EAGER,cascade=CascadeType.ALL)
  	@javax.persistence.OrderBy("layerorder")
  	private List<LayerLayergroup> layerLayergroups;
  	
  	

		public Layergroup() {
	    }

	

		public Integer getLayergroupid() {
			return layergroupid;
		}

		public void setLayergroupid(Integer layergroupid) {
			this.layergroupid = layergroupid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public Boolean getIsactive() {
			return isactive;
		}

		public void setIsactive(Boolean isactive) {
			this.isactive = isactive;
		}

		public Long getCreatedby() {
			return createdby;
		}

		public void setCreatedby(Long createdby) {
			this.createdby = createdby;
		}

		public Long getModifiedby() {
			return modifiedby;
		}

		public void setModifiedby(Long modifiedby) {
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



		
		public List<LayerLayergroup> getLayerLayergroups() {
			return layerLayergroups;
		}



		public void setLayerLayergroups(List<LayerLayergroup> layerLayergroups) {
			this.layerLayergroups = layerLayergroups;
		}

		

		
		
		
		
	
}