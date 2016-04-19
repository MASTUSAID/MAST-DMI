

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="layer_layergroup")
public class LayerLayergroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String layer;
	private Integer layerorder;
	private String tenantid;
	private Layergroup layergroupBean;
	private Boolean layervisibility;
	
    public LayerLayergroup() {
    }

    
   // @Id
   // @GeneratedValue(strategy=GenerationType.IDENTITY)
	
    public Boolean getLayervisibility() {
		return layervisibility;
	}


	public void setLayervisibility(Boolean layervisibility) {
		this.layervisibility = layervisibility;
	}


	@Id
	@SequenceGenerator(name="pk_layer_layergroup_id_seq",sequenceName="layer_layergroup_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_layer_layergroup_id_seq") 
	@Column(name="id", unique=true, nullable=false) 
    
    
    public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getLayer() {
		return this.layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}


	public Integer getLayerorder() {
		return this.layerorder;
	}

	public void setLayerorder(Integer layerorder) {
		this.layerorder = layerorder;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	//bi-directional many-to-one association to Layergroup
	@JsonIgnore
    @ManyToOne
	@JoinColumn(name="layergroup")
	public Layergroup getLayergroupBean() {
		return this.layergroupBean;
	}
	@JsonIgnore
	public void setLayergroupBean(Layergroup layergroupBean) {
		this.layergroupBean = layergroupBean;
	}
	
}