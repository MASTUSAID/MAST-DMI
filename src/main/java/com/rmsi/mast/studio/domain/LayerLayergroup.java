

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the layer_layergroup database table.
 * 
 */
@Entity
@Table(name="la_ext_layer_layergroup")
public class LayerLayergroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer layerorder;
	
	@Column(name="isactive")
	private Boolean layervisibility;
	//private String tenantid;
	
	//private String grouptype_en;
	
	private Integer createdby;
	private Integer modifiedby;
	private Date createddate;
    private Date modifieddate;
	
    
    @ManyToOne
  	@JoinColumn(name="layergroupid")
  	private Layergroup layergroupBean;
  	
  	@ManyToOne
	@JoinColumn(name="layerid")
	private Layer layers;
  	
    

	public LayerLayergroup() {
    }
    
  
  	@Id
	@SequenceGenerator(name="pk_la_ext_layer_layergroup",sequenceName="la_ext_layer_layergroup_layer_layergroupid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_layer_layergroup") 
	@Column(name="layer_layergroupid") 
	private Integer id;
	
 

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLayerorder() {
		return layerorder;
	}

	public void setLayerorder(Integer layerorder) {
		this.layerorder = layerorder;
	}

	
	public Boolean getLayervisibility() {
		return layervisibility;
	}

	public void setLayervisibility(Boolean layervisibility) {
		this.layervisibility = layervisibility;
	}

	/**
	 * @return the grouptype_en
	 */
	
	

	public Date getCreateddate() {
		return createddate;
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

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	@JsonIgnore
	public Layergroup getLayergroupBean() {
		return layergroupBean;
	}

	public void setLayergroupBean(Layergroup layergroupBean) {
		this.layergroupBean = layergroupBean;
	}

	public Layer getLayers() {
		return layers;
	}

	public void setLayers(Layer layers) {
		this.layers = layers;
	}
	
	
	
	
}