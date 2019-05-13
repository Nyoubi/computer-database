
package com.excilys.computer_database.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="computer")
public class Computer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private String name;

	private Timestamp introduced;

	private Timestamp discontinued;

	@ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = Company.class)
	@JoinColumn(name = "company_id",
	referencedColumnName = "id")
	private Company company;

	public Computer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	protected Computer () {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String toString() { 
		return "Id: " + this.id 
				+ ", Name: " + this.name 
				+ ", Introduced: " + this.introduced 
				+ ", Discontinued: " + this.discontinued 
				+ ", Company: (" + this.company + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, discontinued, id, introduced, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		return Objects.equals(company, other.company) && Objects.equals(discontinued, other.discontinued)
				&& Objects.equals(id, other.id) && Objects.equals(introduced, other.introduced)
				&& Objects.equals(name, other.name);
	}
}
