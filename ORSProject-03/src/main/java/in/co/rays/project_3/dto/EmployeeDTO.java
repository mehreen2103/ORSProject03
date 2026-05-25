package in.co.rays.project_3.dto;

public class EmployeeDTO extends BaseDTO {

//	private Long id;
	private String employeeCode;
	private String employeeName;
	private String basicSalary;
	private String bonus;
	private String netsalary;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(String basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getNetsalary() {
		return netsalary;
	}

	public void setNetsalary(String netsalary) {
		this.netsalary = netsalary;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public String getValue() {

		return employeeName;
	}

}
