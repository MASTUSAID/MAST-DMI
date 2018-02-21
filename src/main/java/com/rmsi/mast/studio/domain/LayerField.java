package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the la_ext_layerfield database table.
 * 
 */
@Entity
@Table(name="la_ext_layerfield")
public class LayerField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LA_EXT_LAYERFIELD_LAYERFIELDID_GENERATOR",sequenceName="la_ext_layerfield_layerfieldid_seq",allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LA_EXT_LAYERFIELD_LAYERFIELDID_GENERATOR")
	private Integer layerfieldid;

	private Boolean isactive;

	private String keyfield;

	private String layerfield;
	
	private String alias;
	
	

	@Column(name="layerfield_en")
	private String layerfieldEn;

	//bi-directional many-to-one association to LaSpatialsourceLayer
	@ManyToOne
	@JoinColumn(name="layerid")
	private Layer layer;

	public LayerField() {
	}

	public Integer getLayerfieldid() {
		return this.layerfieldid;
	}

	public void setLayerfieldid(Integer layerfieldid) {
		this.layerfieldid = layerfieldid;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getKeyfield() {
		return this.keyfield;
	}

	public void setKeyfield(String keyfield) {
		this.keyfield = keyfield;
	}

	public String getLayerfield() {
		return this.layerfield;
	}

	public void setLayerfield(String layerfield) {
		this.layerfield = layerfield;
	}

	public String getLayerfieldEn() {
		return this.layerfieldEn;
	}

	public void setLayerfieldEn(String layerfieldEn) {
		this.layerfieldEn = layerfieldEn;
	}

	@JsonIgnore
	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	

}