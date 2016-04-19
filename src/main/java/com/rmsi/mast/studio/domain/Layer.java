

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * The persistent class for the layer database table.
 * 
 */
@Entity
public class Layer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;
	private String apikey;
	private Integer buffer;
	private Boolean displayinlayermanager;
	private String displayname;
	private Boolean displayoutsidemaxextent;
	private Boolean editable;
	private Boolean exportable;
	private String featurens;
	private String featuretype;
	private String filter;
	private String geometryname;
	private String geomtype;
	// private Integer id;
	private Boolean isbaselayer;
	private String maxextent;
	private Integer maxresolution;
	private Integer maxscale;
	private String minextent;
	private Integer minresolution;
	private Integer minscale;
	private String name;
	private Integer numzoomlevels;
	private Boolean queryable;
	private String schemaname;
	private Boolean sphericalmercator;
	private String style;
	private String tenantid;
	private Integer tilesize;
	private String transitioneffect;
	private String url;
	private String version;
	private String wfsname;
	private Boolean wrapdateline;
	private Boolean visibility;
	// private Set<Maptip> maptips;
	// private Set<Attachment> attachments;
	private Layertype layertype;
	private Outputformat outputformat;
	private Projection projectionBean;
	private Unit unitBean;
	private Set<LayerField> layerFields;
	private Set<Savedquery> savedqueries;
	@Transient
	private String keyField;
	private Boolean selectable;
	private Boolean tiled;

	public Boolean getTiled() {
		return tiled;
	}

	public void setTiled(Boolean tiled) {
		this.tiled = tiled;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	@Transient
	public String getKeyField() {
		return keyField;
	}

	@Transient
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public Layer() {
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public Integer getBuffer() {
		return this.buffer;
	}

	public void setBuffer(Integer buffer) {
		this.buffer = buffer;
	}

	public Boolean getDisplayinlayermanager() {
		return this.displayinlayermanager;
	}

	public void setDisplayinlayermanager(Boolean displayinlayermanager) {
		this.displayinlayermanager = displayinlayermanager;
	}

	public String getDisplayname() {
		return this.displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Boolean getDisplayoutsidemaxextent() {
		return this.displayoutsidemaxextent;
	}

	public void setDisplayoutsidemaxextent(Boolean displayoutsidemaxextent) {
		this.displayoutsidemaxextent = displayoutsidemaxextent;
	}

	public Boolean getEditable() {
		return this.editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getExportable() {
		return this.exportable;
	}

	public void setExportable(Boolean exportable) {
		this.exportable = exportable;
	}

	public String getFeaturens() {
		return this.featurens;
	}

	public void setFeaturens(String featurens) {
		this.featurens = featurens;
	}

	public String getFeaturetype() {
		return this.featuretype;
	}

	public void setFeaturetype(String featuretype) {
		this.featuretype = featuretype;
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getGeometryname() {
		return this.geometryname;
	}

	public void setGeometryname(String geometryname) {
		this.geometryname = geometryname;
	}

	public String getGeomtype() {
		return this.geomtype;
	}

	public void setGeomtype(String geomtype) {
		this.geomtype = geomtype;
	}

	/*
	 * public Integer getId() { return this.id; }
	 * 
	 * public void setId(Integer id) { this.id = id; }
	 */

	public Boolean getIsbaselayer() {
		return this.isbaselayer;
	}

	public void setIsbaselayer(Boolean isbaselayer) {
		this.isbaselayer = isbaselayer;
	}

	public String getMaxextent() {
		return this.maxextent;
	}

	public void setMaxextent(String maxextent) {
		this.maxextent = maxextent;
	}

	public Integer getMaxresolution() {
		return this.maxresolution;
	}

	public void setMaxresolution(Integer maxresolution) {
		this.maxresolution = maxresolution;
	}

	public Integer getMaxscale() {
		return this.maxscale;
	}

	public void setMaxscale(Integer maxscale) {
		this.maxscale = maxscale;
	}

	public String getMinextent() {
		return this.minextent;
	}

	public void setMinextent(String minextent) {
		this.minextent = minextent;
	}

	public Integer getMinresolution() {
		return this.minresolution;
	}

	public void setMinresolution(Integer minresolution) {
		this.minresolution = minresolution;
	}

	public Integer getMinscale() {
		return this.minscale;
	}

	public void setMinscale(Integer minscale) {
		this.minscale = minscale;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumzoomlevels() {
		return this.numzoomlevels;
	}

	public void setNumzoomlevels(Integer numzoomlevels) {
		this.numzoomlevels = numzoomlevels;
	}

	public Boolean getQueryable() {
		return this.queryable;
	}

	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}

	public String getSchemaname() {
		return this.schemaname;
	}

	public void setSchemaname(String schemaname) {
		this.schemaname = schemaname;
	}

	public Boolean getSphericalmercator() {
		return this.sphericalmercator;
	}

	public void setSphericalmercator(Boolean sphericalmercator) {
		this.sphericalmercator = sphericalmercator;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public Integer getTilesize() {
		return this.tilesize;
	}

	public void setTilesize(Integer tilesize) {
		this.tilesize = tilesize;
	}

	public String getTransitioneffect() {
		return this.transitioneffect;
	}

	public void setTransitioneffect(String transitioneffect) {
		this.transitioneffect = transitioneffect;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWfsname() {
		return this.wfsname;
	}

	public void setWfsname(String wfsname) {
		this.wfsname = wfsname;
	}

	public Boolean getWrapdateline() {
		return this.wrapdateline;
	}

	public void setWrapdateline(Boolean wrapdateline) {
		this.wrapdateline = wrapdateline;
	}

	// bi-directional many-to-one association to Attachment
	// @OneToMany(mappedBy = "layer", fetch = FetchType.EAGER,
	// cascade=CascadeType.ALL)
	// public Set<Attachment> getAttachments() {
	// return this.attachments;
	// }
	//
	// public void setAttachments(Set<Attachment> attachments) {
	// this.attachments = attachments;
	// }

	// Uni-directional many-to-one association to Layertype
	@ManyToOne
	@JoinColumn(name = "type")
	public Layertype getLayertype() {
		return this.layertype;
	}

	public void setLayertype(Layertype layertype) {
		this.layertype = layertype;
	}

	// Uni-directional many-to-one association to Outputformat
	@ManyToOne
	@JoinColumn(name = "format")
	public Outputformat getOutputformat() {
		return this.outputformat;
	}

	public void setOutputformat(Outputformat outputformat) {
		this.outputformat = outputformat;
	}

	// Uni-directional many-to-one association to Projection
	@ManyToOne
	@JoinColumn(name = "projection")
	public Projection getProjectionBean() {
		return this.projectionBean;
	}

	public void setProjectionBean(Projection projectionBean) {
		this.projectionBean = projectionBean;
	}

	// Uni-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name = "unit")
	public Unit getUnitBean() {
		return this.unitBean;
	}

	public void setUnitBean(Unit unitBean) {
		this.unitBean = unitBean;
	}

	// bi-directional many-to-one association to LayerField
	@OneToMany(mappedBy = "layerBean", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<LayerField> getLayerFields() {
		return this.layerFields;
	}

	public void setLayerFields(Set<LayerField> layerFields) {
		this.layerFields = layerFields;
	}

	// bi-directional many-to-one association to Savedquery
	@OneToMany(mappedBy = "layerBean", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Savedquery> getSavedqueries() {
		return this.savedqueries;
	}

	public void setSavedqueries(Set<Savedquery> savedqueries) {
		this.savedqueries = savedqueries;
	}

	// bi-directional many-to-one association to Maptip
	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(mappedBy="layerBean", fetch=FetchType.EAGER,
	 * cascade=CascadeType.ALL) public Set<Maptip> getMaptips() { return
	 * this.maptips; }
	 * 
	 * @JsonIgnore public void setMaptips(Set<Maptip> maptips) { this.maptips =
	 * maptips; }
	 */

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

}