package com.excilys.computer_database.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.InvalidInputException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.validation.DtoComputerValidation;

@RestController
@RequestMapping(path = "/computers", produces = "application/json")
public class ComputerRestController {
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public ResponseEntity<List<ComputerDto>> findAll(Model model,
			@RequestParam(name = "index", required = false, defaultValue = "1") String index,
			@RequestParam(name = "size", required = false, defaultValue = "10") String size,
			@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@RequestParam(name = "order", required = false, defaultValue = "") String order) {
		
		Page<ComputerDto> showComputers = null;
		try {
			showComputers = computerService.pageDtoComputer("", index, size, search, order);

		} catch (DaoException | ValidationException e) {
			return new ResponseEntity<List<ComputerDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<List<ComputerDto>>(showComputers.getContent(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ComputerDto> findById(@PathVariable Integer id) {
		ComputerDto computerDto;
			try {
				computerDto = computerService.showDetails(id.toString()).get();
				return new ResponseEntity<ComputerDto>(computerDto, HttpStatus.OK);
			} catch (DaoException | InvalidInputException e) {
				return new ResponseEntity<ComputerDto>(HttpStatus.INTERNAL_SERVER_ERROR);

			}
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> createComputer(@RequestBody ComputerDto dtoComputer) {
		try {
			Computer computer = DtoComputerValidation.checkDataComputer(dtoComputer, this.companyService, false);
			computerService.createComputer(computer);

			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (DaoException | ValidationException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> updateComputer(@RequestBody ComputerDto dtoComputer, @PathVariable Integer id) {
		try {
			Computer computer = DtoComputerValidation.checkDataComputer(dtoComputer, this.companyService, true);
			computerService.updateComputer(computer);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (DaoException | ValidationException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
		try {
			computerService.deleteComputer(id.toString());
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (InvalidInputException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
