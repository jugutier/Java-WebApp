package ar.edu.itba.grupo2.domain.report;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.user.User;
@Entity
@Table(name = "ReportResolution")
public class ReportResolution extends EntityBaseType {
	
	@OneToMany(mappedBy="reportResolution")
	private List<Report> reports;
	
	@OneToOne private User user;
	
	@Column(length=140,nullable=true)private String reason;
	
	@Column(length=40,nullable=false)@Enumerated(EnumType.STRING)private Resolution resolution;
	
	ReportResolution(){}
	
	public ReportResolution(User user, List<Report> reports, String reason, Resolution resolution){
		setId(null);
		this.user = user;
		this.reason = reason;
		this.resolution = resolution;
		this.reports = new LinkedList<Report>();
		this.reports.addAll(reports);			
	}
	
	public Resolution getResolution(){
		return resolution;
	}
	
}
