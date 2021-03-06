package db.essp.timesheet;

import java.util.Date;

/**
 * TsMethod generated by MyEclipse Persistence Tools
 */

public class TsMethod implements java.io.Serializable {

	// Fields

	private Long rid;
	private String name;
	private String description;
	private String rst;
	private Date rct;
	private Date rut;
	private String rcu;
	private String ruu;

	// Constructors

	/** default constructor */
	public TsMethod() {
	}

	/** minimal constructor */
	public TsMethod(Long rid, String name) {
		this.rid = rid;
		this.name = name;
	}

	/** full constructor */
	public TsMethod(Long rid, String name, String description, String rst,
			Date rct, Date rut, String rcu, String ruu) {
		this.rid = rid;
		this.name = name;
		this.description = description;
		this.rst = rst;
		this.rct = rct;
		this.rut = rut;
		this.rcu = rcu;
		this.ruu = ruu;
	}

	// Property accessors

	public Long getRid() {
		return this.rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRst() {
		return this.rst;
	}

	public void setRst(String rst) {
		this.rst = rst;
	}

	public Date getRct() {
		return this.rct;
	}

	public void setRct(Date rct) {
		this.rct = rct;
	}

	public Date getRut() {
		return this.rut;
	}

	public void setRut(Date rut) {
		this.rut = rut;
	}

	public String getRcu() {
		return this.rcu;
	}

	public void setRcu(String rcu) {
		this.rcu = rcu;
	}

	public String getRuu() {
		return this.ruu;
	}

	public void setRuu(String ruu) {
		this.ruu = ruu;
	}

}