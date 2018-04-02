package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PersonOfIntrest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long Id;
	
	private String firstname;
	private String middlename ;
	private String  lastname;
	private String gender;
	
	
	@Temporal(TemporalType.DATE)
	private Date dateofbirth;
	
	private String relation;
	
	
	
}
