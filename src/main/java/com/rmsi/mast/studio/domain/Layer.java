package com.rmsi.mast.studio.domain;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the layer database table.
 * 
 */
@Entity
@Table(name = "la_spatialsource_layer")
public class Layer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="layerid")
	private Long layerid;
	
	@Column(name="layername")
	private String alias;
	
	private Integer minresolution;
	private Integer maxresolution;
	@Column(name="zoomlevelextent")
	private Integer numzoomlevels;
	private String minextent;
	private String maxextent;
	private Integer minscale;
	private Integer maxscale;
	@Column(name="location")
	private String url;
	@Column(name="geometrytype")
	private String geomtype;
	private Integer buffer;
	private String displayname;
	private String filter;
	private String version;
	private Boolean displayinlayermanager;
	private Boolean displayoutsidemaxextent;
	private Boolean editable;
	private Boolean exportable;
	private boolean isbaselayer;
	private Boolean queryable;
	private Boolean selectable;
	private Boolean sphericalmercator;
	private Boolean tiled;
	@Column(name="isactive")
	private Boolean visibility;
	private Long createdby;
	private Long modifiedby;
	private Date createddate;
    private Date modifieddate;
    private String name;
	
   
    @ManyToOne
	@JoinColumn(name = "layertypeid")
    private Layertype layertype;
    

    @ManyToOne
	@JoinColumn(name = "documentformatid")
	private Outputformat outputformat;
    
 

	@ManyToOne
	@JoinColumn(name = "projectionid")
	private Projection projectionBean;
	
	
	@ManyToOne
	@JoinColumn(name = "unitid")
	private Unit unitBean;

	
	
	@OneToMany(mappedBy="layer", cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<LayerField> layerField;

	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	

	public Integer getMinresolution() {
		return minresolution;
	}

	public void setMinresolution(Integer minresolution) {
		this.minresolution = minresolution;
	}

	public Integer getMaxresolution() {
		return maxresolution;
	}

	public void setMaxresolution(Integer maxresolution) {
		this.maxresolution = maxresolution;
	}

	public Integer getNumzoomlevels() {
		return numzoomlevels;
	}

	public void setNumzoomlevels(Integer numzoomlevels) {
		this.numzoomlevels = numzoomlevels;
	}

	public String getMinextent() {
		return minextent;
	}

	public void setMinextent(String minextent) {
		this.minextent = minextent;
	}

	public String getMaxextent() {
		return maxextent;
	}

	public void setMaxextent(String maxextent) {
		this.maxextent = maxextent;
	}

	public Integer getMinscale() {
		return minscale;
	}

	public void setMinscale(Integer minscale) {
		this.minscale = minscale;
	}

	public Integer getMaxscale() {
		return maxscale;
	}

	public void setMaxscale(Integer maxscale) {
		this.maxscale = maxscale;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGeomtype() {
		return geomtype;
	}

	public void setGeomtype(String geomtype) {
		this.geomtype = geomtype;
	}

	public Integer getBuffer() {
		return buffer;
	}

	public void setBuffer(Integer buffer) {
		this.buffer = buffer;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean getDisplayinlayermanager() {
		return displayinlayermanager;
	}

	public void setDisplayinlayermanager(Boolean displayinlayermanager) {
		this.displayinlayermanager = displayinlayermanager;
	}

	public Boolean getDisplayoutsidemaxextent() {
		return displayoutsidemaxextent;
	}

	public void setDisplayoutsidemaxextent(Boolean displayoutsidemaxextent) {
		this.displayoutsidemaxextent = displayoutsidemaxextent;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getExportable() {
		return exportable;
	}

	public void setExportable(Boolean exportable) {
		this.exportable = exportable;
	}

	public boolean isIsbaselayer() {
		return isbaselayer;
	}

	public void setIsbaselayer(boolean isbaselayer) {
		this.isbaselayer = isbaselayer;
	}

	public Boolean getQueryable() {
		return queryable;
	}

	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public Boolean getSphericalmercator() {
		return sphericalmercator;
	}

	public void setSphericalmercator(Boolean sphericalmercator) {
		this.sphericalmercator = sphericalmercator;
	}

	public Boolean getTiled() {
		return tiled;
	}

	public void setTiled(Boolean tiled) {
		this.tiled = tiled;
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
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

	public Layertype getLayertype() {
		return layertype;
	}

	public void setLayertype(Layertype layertype) {
		this.layertype = layertype;
	}


	public Projection getProjectionBean() {
		return projectionBean;
	}

	public void setProjectionBean(Projection projectionBean) {
		this.projectionBean = projectionBean;
	}

	public Unit getUnitBean() {
		return unitBean;
	}

	public void setUnitBean(Unit unitBean) {
		this.unitBean = unitBean;
	}

	public Long getLayerid() {
		return layerid;
	}

	public void setLayerid(Long layerid) {
		this.layerid = layerid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Outputformat getOutputformat() {
		return outputformat;
	}

	public void setOutputformat(Outputformat outputformat) {
		this.outputformat = outputformat;
	}

	public Set<LayerField> getLayerField() {
		return layerField;
	}

	public void setLayerField(Set<LayerField> layerField) {
		this.layerField = layerField;
	}

	
	
	

}