package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SocialTenureRelationType
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "share_type")
public class ShareType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int gid;

	@Column(name = "share_type", nullable = false)
	private String shareType;

	public ShareType() {
		super();
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
}
