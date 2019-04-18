package validation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.ModelException;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.util.Util;

public abstract class DtoComputerValidation {

	
	public static Computer checkDataComputer(ComputerDto dtoComputer, CompanyService companyService, boolean update) throws ModelException, DaoException {
		Integer id = null;
		if (update) {
			id = checkId(dtoComputer.getId());
		}
		String name = checkName(dtoComputer.getName());
		ArrayList<Optional<Timestamp>> date = checkDate(dtoComputer.getIntroduced(),dtoComputer.getDiscontinued());
		Optional<Company> company = checkCompany(dtoComputer.getCompanyId(), companyService);
		
		ComputerBuilder computerBuilder = new ComputerBuilder().setName(name)
				.setIntroduced(date.get(0).isPresent() ? date.get(0).get() : null)
				.setDiscontinued(date.get(1).isPresent() ? date.get(1).get() : null)
				.setCompany(company.isPresent() ? company.get() : null)
				.setId(id);
		return computerBuilder.build();
	}
	
	private static Integer checkId(Integer id) throws ModelException {
		if (id == null) {
			throw new ModelException("Failed to check computer : Incorrect id");
		}
		return id;
	}
	
	private static String checkName(String name) throws ModelException {
		if (name == null || name == "") {
			throw new ModelException("Failed to check computer : Invalid name");
		}
		return name;
	}
	
	private static ArrayList<Optional<Timestamp>> checkDate(String introduced, String discontinued) throws ModelException {
		Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(introduced);
		Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(discontinued);

		if (!OptIntroduced.isPresent() && OptDiscontinued.isPresent()) {
			throw new ModelException("Failed to check computer : Discontinued but not introduced");
		}
		if (OptIntroduced.isPresent() && OptDiscontinued.isPresent()
				&& OptIntroduced.get().after(OptDiscontinued.get())) {
			throw new ModelException("Failed to check computer : Introduced can't be after discontinued date");
		}
		
		return new ArrayList<Optional<Timestamp>>(Arrays.asList(OptIntroduced,OptDiscontinued));
	}
	
	private static Optional<Company> checkCompany(Integer companyId, CompanyService companyService) throws ModelException, DaoException {
		Optional<CompanyDto> dtoCompany = companyService.findCompanyById(companyId);
		Optional<Company> company = Optional.empty();
		if (dtoCompany.isPresent()) {
			company = CompanyMapper.dtoCompanyToCompany(dtoCompany.get());
			if (!company.isPresent()) {
				throw new ModelException("Mapper : Can't convert the dto company to company");
			}
		} else if (companyId != null){
			throw new ModelException("This company doesn't exist");
		}
		
		return company;
	}
}
