package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.RegistrationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RegistrationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/RegistrationCtl" })

public class RegistrationCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(RegistrationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("registrationCode"))) {

			request.setAttribute("registrationCode", PropertyReader.getValue("error.require", "Registration Code"));

			pass = false;

		}else if (!DataValidator.isCode(request.getParameter("registrationCode"))) {

			request.setAttribute("registrationCode", "Please Enter Valid Registration Code");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("firstName"))) {

			request.setAttribute("firstName", "Please Enter Valid First Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {

			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastName"))) {

			request.setAttribute("lastName", "Please Enter Valid Last Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {

			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		RegistrationDTO dto = new RegistrationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRegistrationCode(DataUtility.getString(request.getParameter("registrationCode")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("RegistrationCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		RegistrationModelInt model = ModelFactory.getInstance().getRegistrationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			RegistrationDTO dto;

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

		log.debug("RegistrationCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		RegistrationModelInt model = ModelFactory.getInstance().getRegistrationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			RegistrationDTO dto = (RegistrationDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Registration already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			RegistrationDTO dto = (RegistrationDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.REGISTRATION_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.REGISTRATION_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.REGISTRATION_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("RegistrationCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.REGISTRATION_VIEW;
	}
}