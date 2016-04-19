

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the layer_field database table.
 * 
 */
@Entity
@Table(name="layer_field")
public class LayerField implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;
	private String field;
	private Integer id;
	private String keyfield;
	private String tenantid;
	private Layer layerBean;

    public LayerField() {
    }


	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Id
	@SequenceGenerator(name="pk_layer_fields_id_seq_",sequenceName="layer_fields_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_layer_fields_id_seq_") 
	@Column(name="id", unique=true, nullable=false) 
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getKeyfield() {
		return this.keyfield;
	}

	public void setKeyfield(String keyfield) {
		this.keyfield = keyfield;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	//bi-directional many-to-one association to Layer
	@JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="layer")
	public Layer getLayerBean() {
		return this.layerBean;
	}
	@JsonIgnore
	public void setLayerBean(Layer layerBean) {
		this.layerBean = layerBean;
	}
	
}