package com.excilys.computer_database.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name="company")
public class Company {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Basic(optional = false)
	@Column(nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<Computer> computers;

	public Company(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	protected Company() {}

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

	public String toString() { 
		return "Id: '" + this.id 
				+ "', Name: '" + this.name  + "'";
	}

	@Override
	public int hashCode() {
		return Objects.hash(computers, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(computers, other.computers) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}
