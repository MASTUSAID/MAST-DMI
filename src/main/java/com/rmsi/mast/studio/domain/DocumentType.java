package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="la_ext_documenttype")
public class DocumentType implements Serializable {
    private static final long serialVersionUID = 1L;

	    @Id
	    @Column(name="documenttypeid")
	    private Long code;
	    @Column(name="documenttype")
        private String name;
	    @Column(name="documenttype_en")
        private String nameOtherLang;
	    @Column(name="isactive")
	    private Boolean active;
	    
	    private Integer processid;
       
	    
        public DocumentType(){
            
        }


		public Integer getProcessid() {
			return processid;
		}


		public void setProcessid(Integer processid) {
			this.processid = processid;
		}


		public Long getCode() {
			return code;
		}


		public void setCode(Long code) {
			this.code = code;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getNameOtherLang() {
			return nameOtherLang;
		}


		public void setNameOtherLang(String nameOtherLang) {
			this.nameOtherLang = nameOtherLang;
		}


		public Boolean getActive() {
			return active;
		}


		public void setActive(Boolean active) {
			this.active = active;
		}
        
       
    
    
    
}
