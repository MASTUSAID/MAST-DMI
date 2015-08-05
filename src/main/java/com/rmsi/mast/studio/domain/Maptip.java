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
import javax.persistence.*;



/**
 * The persistent class for the maptip database table.
 * 
 */
@Entity
public class Maptip implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MaptipPK id;
	
	private String field;
	private String name;
	private String queryexpression;

	//bi-directional many-to-one association to Layer
    @ManyToOne(cascade = CascadeType.ALL, optional=false)
	@JoinColumn(name="layer", insertable=false, updatable=false)
	private Layer layerBean;

	//bi-directional many-to-one association to Project
    @ManyToOne(cascade = CascadeType.ALL, optional=false)
	@JoinColumn(name="project", insertable=false, updatable=false)
	private Project projectBean;

    public Maptip() {
    }

	public MaptipPK getId() {
		return this.id;
	}

	public void setId(MaptipPK id) {
		this.id = id;
	}
	
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getQueryexpression() {
		return this.queryexpression;
	}

	public void setQueryexpression(String queryexpression) {
		this.queryexpression = queryexpression;
	}

	public Layer getLayerBean() {
		return this.layerBean;
	}

	public void setLayerBean(Layer layerBean) {
		this.layerBean = layerBean;
	}
	
	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
}