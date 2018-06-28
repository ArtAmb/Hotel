package hotel.ejb.controller;

import java.util.Optional;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.EmployeeDAO;
import hotel.dao.UserDAO;
import hotel.domain.Employee;
import hotel.domain.User;
import hotel.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@ManagedBean
public class CreateNewEmployeeAcountController {

	@Getter
	@Setter
	private User user = new User();
	@Getter
	@Setter
	private Employee employee = new Employee();

	@EJB
	private EmployeeDAO employeeDAO;

	@EJB
	private UserDAO userDAO;
	@Getter
	@Setter
	private String repeatedPassword;

	@Getter
	@Setter
	private String errorMessage;

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public void validateEmployeePart() {
		if (StringUtils.isNullOrEmpty(employee.getName()))
			throw new IllegalStateException("Imie jest wymagane");
		if (StringUtils.isNullOrEmpty(employee.getSurname()))
			throw new IllegalStateException("Nazwisko jest wymagane");
		if (StringUtils.isNullOrEmpty(employee.getEmail()))
			throw new IllegalStateException("Emial jest wymagany");
		if (StringUtils.isNullOrEmpty(employee.getPhone()))
			throw new IllegalStateException("Telefon jest wymagany");
		if (StringUtils.isNullOrEmpty(employee.getStreet()))
			throw new IllegalStateException("Ulica jest wymagana");
		if (StringUtils.isNullOrEmpty(employee.getHomeNr()))
			throw new IllegalStateException("Numer domu jest wymagany");
		if (StringUtils.isNullOrEmpty(employee.getPhone()))
			throw new IllegalStateException("Telefon jest wymagany");
		if (StringUtils.isNullOrEmpty(employee.getCity()))
			throw new IllegalStateException("Miasto jest wymagane");
		if (StringUtils.isNullOrEmpty(employee.getPesel()))
			throw new IllegalStateException("Pesel jest wymagane");
	}

	public void validateUserPart() {
		if (StringUtils.isNullOrEmpty(user.getLogin()))
			throw new IllegalStateException("Login jest wymagane");
		if (StringUtils.isNullOrEmpty(user.getPassword()))
			throw new IllegalStateException("Haslo jest wymagane");
		if (!user.getPassword().equals(repeatedPassword)) {
			throw new IllegalStateException("Oba has³a musza byc takie same");
		}
	}

	public String saveNewEmployee() {
		try {
			validateEmployeePart();
			validateUserPart();
		} catch (Exception e) {
			errorMessage = e.getMessage();
			return null;
		}
		Optional<Employee> optEmployee = employeeDAO.findByQuery(Employee.builder().email(employee.getEmail()).build())
				.stream().findFirst();
		if (optEmployee.isPresent()) {
			errorMessage = "Pracownik o takim adresie emial istnieje";
			return null;
		}

		Optional<User> optUser = userDAO
				.findByQuery(User.builder().login(user.getLogin()).active(true).build()).stream()
				.findFirst();
		if (optUser.isPresent()) {
			errorMessage = "Uzytkownik o takim loginie istnieje";
			return null;
		}
user.setRole(UserRole.EMPLOYEE);
		User newUser = userDAO.save(user);
		employee.setUser(newUser);

		employeeDAO.save(employee);
		return Utils.getViewUrl("authorization/create-account-success");

	}

	public String redirectToFrom() {
		return Utils.getTemplateUriRedirect("config/employee-config");
	}

}
