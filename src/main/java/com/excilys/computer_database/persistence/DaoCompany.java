package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoCompanyBuilder;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.zaxxer.hikari.HikariDataSource;

public class DaoCompany {
	
	private final static String SELECT_ALL = "SELECT id as cId, name as cName FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id=? ";
	private final static String DELETE_ID = "DELETE FROM company WHERE id = ? ";
	private final static String DELETE_COMPUTER_ID = "DELETE FROM computer WHERE company_id = ? ";

	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	private HikariDataSource dataSource;

	public DaoCompany(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public HikariDataSource getDataSource() {
		return this.dataSource;
	}

	public Optional<Company> findCompanyById(Integer id) throws ExceptionModel, ExceptionDao{
		
		Optional<Company> result = Optional.empty();
		
		try (Connection conn = dataSource.getConnection();
		    PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				if(resultSet.next()) {
					result= Optional.of(resultSetToCompany(resultSet));			
				}
			}
		} catch (SQLException e) {
			logger.error("Error when searching the company id " + id);
			throw new ExceptionDao("Sql error when searching the computer.");
		}
		return result;
	}
	
	public ArrayList<Company> listAllCompany(){
		
		ArrayList<Company> companyList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
			 Statement statement = conn.createStatement();){
			
			try (ResultSet resultSet = statement.executeQuery(SELECT_ALL);) {
				while(resultSet.next()) {
					Optional<Company> company = Optional.of(resultSetToCompany(resultSet));
					if (company.isPresent()) {
						companyList.add(company.get());	
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all companies.");
		}
		return companyList;
	}
	
	public void deleteCompanyById(Integer id) throws ExceptionDao {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			try  {
				conn.setAutoCommit(false);
				PreparedStatement stmt = conn.prepareStatement(DELETE_COMPUTER_ID);
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt = conn.prepareStatement(DELETE_ID);
				stmt.setInt(1, id);
				stmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				if(conn != null) {
					conn.rollback();
				}
				logger.error("Error when deleting company " + id);
				throw new ExceptionDao("Error when deleting the company");
			} finally {
				if (conn != null && ! conn.isClosed()) {
					conn.close();
				}
			} 
		}catch (SQLException e1) {
			throw new ExceptionDao("Error when deleting the company");
		}
	}
	
	public Company resultSetToCompany(ResultSet resultSet) throws SQLException{
		Company company = null;
		CompanyBuilder companyBuilder = new CompanyBuilder();

		Integer id = resultSet.getInt("cId");
		String name = resultSet.getString("cName");
		
		if (id == 0 || id == null)  {
			company = null;
		}
		else {
			company = companyBuilder.setId(id).setName(name).build();
		}
		return company;
	}

	public Optional<DtoCompany> companyToDtoCompany(Company company){
		Optional<DtoCompany> dtoCompany = Optional.empty();
		if (company != null) {
			DtoCompanyBuilder dtoCompanyBuilder = new DtoCompanyBuilder();
			dtoCompany = Optional.of(dtoCompanyBuilder.setId(company.getId())
					.setName(company.getName()).build());
		}	
		return dtoCompany;
	}

	public Optional<Company> dtoCompanyToCompany(DtoCompany dtoCompany){
		
		if(dtoCompany == null) {
			return Optional.empty();
		} else {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		Company company = companyBuilder.setId(dtoCompany.getId())
										.setName(dtoCompany.getName())
										.build();
		return Optional.of(company);
		}
	}
}
