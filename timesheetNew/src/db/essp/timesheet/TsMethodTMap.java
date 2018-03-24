package db.essp.timesheet;

import java.util.Date;

/**
 * TsMethodTMap generated by MyEclipse Persistence Tools
 */

public class TsMethodTMap implements java.io.Serializable {

	// Fields

	private TsMethodTMapId id;
	private String rst;
	private Date rct;
	private Date rut;
	private String rcu;
	private String ruu;

	// Constructors

	/** default constructor */
	public TsMethodTMap() {
	}

	/** minimal constructor */
	public TsMethodTMap(TsMethodTMapId id) {
		this.id = id;
	}

	/** full constructor */
	public TsMethodTMap(TsMethodTMapId id, String rst, Date rct, Date rut,
			String rcu, String ruu) {
		this.id = id;
		this.rst = rst;
		this.rct = rct;
		this.rut = rut;
		this.rcu = rcu;
		this.ruu = ruu;
	}

	// Property accessors

	public TsMethodTMapId getId() {
		return this.id;
	}

	public void setId(TsMethodTMapId id) {
		this.id = id;
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