/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

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