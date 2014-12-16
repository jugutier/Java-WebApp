package ar.edu.itba.grupo2.domain.report;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.user.User;

public class ReportResolution extends EntityBaseType {
	
	@OneToMany(mappedBy="comment", cascade = CascadeType.ALL)
	private List<Report> reports;
	
	@OneToOne private User user;
	
	@Column(length=140,nullable=true)private String reason;
	
	@Column(length=40,nullable=false)private Resolution resolution;
	
	ReportResolution(){}
	
	public ReportResolution(User user, List<Report> reports, String reason, Resolution resolution){
		setId(null);
		this.user = user;
		this.reason = reason;
		this.resolution = resolution;
		this.reports.addAll(reports);			
	}
	
	
}
