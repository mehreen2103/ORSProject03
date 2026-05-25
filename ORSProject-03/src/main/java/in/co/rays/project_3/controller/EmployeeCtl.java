package in.co.rays.project_3.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EmployeeModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/EmployeeCtl" })

public class EmployeeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(EmployeeCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("employeeCode"))) {

			request.setAttribute("employeeCode", PropertyReader.getValue("error.require", "Employee Code"));

			pass = false;
		} else if (!DataValidator.isCode(request.getParameter("employeeCode"))) {

			request.setAttribute("employeeCode", "Please Enter Valid Employee Code");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("employeeName"))) {

			request.setAttribute("employeeName", PropertyReader.getValue("error.require", "Employee Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("employeeName"))) {

			request.setAttribute("employeeName", "Please Enter Valid Employee Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("basicSalary"))) {

			request.setAttribute("basicSalary", PropertyReader.getValue("error.require", "Basic Salary"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("bonus"))) {

			request.setAttribute("bonus", PropertyReader.getValue("error.require", "Bonus"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("netsalary"))) {

			request.setAttribute("netsalary", PropertyReader.getValue("error.require", "Net Salary"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EmployeeDTO dto = new EmployeeDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setEmployeeCode(DataUtility.getString(request.getParameter("employeeCode")));

		dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));

		dto.setBasicSalary(DataUtility.getString(request.getParameter("basicSalary")));

		dto.setBonus(DataUtility.getString(request.getParameter("bonus")));

		dto.setNetsalary(DataUtility.getString(request.getParameter("netsalary")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EmployeeCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			EmployeeDTO dto;

			try {

				dto = model.findByPk(id);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EmployeeCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {

					model.add(dto);

					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
				}

			} catch (ApplicationException e) {

				log.error(e);

				e.printStackTrace();

				ServletUtility.handleDBDown(getView(), request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Employee already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("EmployeeCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.EMPLOYEE_VIEW;
	}
}