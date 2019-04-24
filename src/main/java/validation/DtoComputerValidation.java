package validation;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.ValidationException;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.util.Util;

public class DtoComputerValidation implements Validator{
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return ComputerDto.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ComputerDto computerDto = (ComputerDto) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dtoValidatorError.namerequired");
		checkId(computerDto.getId(),errors);
		checkDate(computerDto,errors);
		checkCompanyId(computerDto.getCompanyId(),errors);
	}
	
	
	public static Computer checkDataComputer(ComputerDto computerDto, CompanyService companyService, boolean update) throws DaoException, ValidationException {
		Integer id = new Integer(computerDto.getId());
		
		if (update && id == 0) {
			throw new ValidationException("dtoValidatorError.idnullupdate");
		}

		Optional<Company> company = checkCompany(computerDto.getCompanyId(), companyService);

		Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(computerDto.getIntroduced());
		Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(computerDto.getDiscontinued());

		ComputerBuilder computerBuilder = new ComputerBuilder().setName(computerDto.getName())
				.setIntroduced(OptIntroduced.isPresent() ? OptIntroduced.get() : null)
				.setDiscontinued(OptDiscontinued.isPresent() ? OptDiscontinued.get() : null)
				.setCompany(company.isPresent() ? company.get() : null)
				.setId(id);

		return computerBuilder.build();
	}
	
	private static void checkId(Integer id, Errors errors) {
		if (id != null && id < 0) {
			errors.rejectValue("id", "dtoValidatorError.negativeId");
		}
	}
	private static void checkDate(ComputerDto computerDto, Errors errors) {
		Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(computerDto.getIntroduced());
		Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(computerDto.getDiscontinued());

		if (!OptIntroduced.isPresent() && OptDiscontinued.isPresent()) {
			errors.rejectValue("discontinued", "dtoValidatorError.disconWithoutIntro");
		}
		if (OptIntroduced.isPresent() && OptDiscontinued.isPresent()
				&& OptIntroduced.get().after(OptDiscontinued.get())) {
			errors.rejectValue("introduced", "dtoValidatorError.introAfterDiscon");
		}
	}
	
	private static void checkCompanyId(Integer companyId, Errors errors) {
		if (companyId < 0){
			errors.rejectValue("companyId", "dtoValidatorError.negativeCompanyId");
		}
	}
	
	private static Optional<Company> checkCompany(Integer companyId, CompanyService companyService) throws ValidationException, DaoException {
		if (companyId == 0) {
			return Optional.empty();
		}
		Optional<CompanyDto> dtoCompany = companyService.findCompanyById(companyId);
		Optional<Company> company = Optional.empty();
		if (dtoCompany.isPresent()) {
			company = CompanyMapper.dtoCompanyToCompany(dtoCompany.get());
			if (!company.isPresent()) {
				throw new ValidationException("dtoValidatorError.companymapper");
			}
		} else {
			throw new ValidationException("dtoValidatorError.companynotfound");
		}
		
		return company;
	}
}
