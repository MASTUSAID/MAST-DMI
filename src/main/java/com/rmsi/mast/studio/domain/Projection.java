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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * The persistent class for the projection database table.
 * 
 */
@Entity
public class Projection implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String description;
	private Integer id;
	private String tenantid;
	//private Set<Layer> layers;
	//private Set<Project> projects1;
	//private Set<Project> projects2;

    public Projection() {
    }


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	//bi-directional many-to-one association to Layer
//	@JsonIgnore
//	@OneToMany(mappedBy="projectionBean")
//	public Set<Layer> getLayers() {
//		return this.layers;
//	}
//	@JsonIgnore
//	public void setLayers(Set<Layer> layers) {
//		this.layers = layers;
//	}
	

	//bi-directional many-to-one association to Project
//	@JsonIgnore
//	@OneToMany(mappedBy="projection1")
//	public Set<Project> getProjects1() {
//		return this.projects1;
//	}
//	@JsonIgnore
//	public void setProjects1(Set<Project> projects1) {
//		this.projects1 = projects1;
//	}
//	
//
//	//bi-directional many-to-one association to Project
//	@JsonIgnore
//	@OneToMany(mappedBy="projection2")
//	public Set<Project> getProjects2() {
//		return this.projects2;
//	}
//	@JsonIgnore
//	public void setProjects2(Set<Project> projects2) {
//		this.projects2 = projects2;
//	}
	
}