package com.rmsi.mast.studio.domain;
import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_reporting database table.
 * 
 */
@Entity
//@Table(name="user_reporting")
public class UserReporting implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private User user1;
	private User user2;

    public UserReporting() {
    }


	@Id
	@SequenceGenerator(name="USER_REPORTING_ID_GENERATOR", sequenceName="USER_REPORTING_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_REPORTING_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_name", nullable=false)
	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}
	

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="manager_name")
	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}
	
}