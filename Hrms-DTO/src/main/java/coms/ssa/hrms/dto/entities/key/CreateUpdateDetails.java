package coms.ssa.hrms.dto.entities.key;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.dto.mysqlentities.User;


@Embeddable
public class CreateUpdateDetails implements Serializable {
	private static final long serialVersionUID = -191523766722275403L;
	@Column(name="CREATED_BY")
	private Integer createdBy;
	@Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
	@Column(name="UPDATED_BY")
	private Integer updatedBy;
	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;
	
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userCreatedBy;
	
	@JoinColumn(name = "UPDATED_BY", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userUpdatedBy;
	
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	

}
