package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PasswordDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PasswordModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PasswordCtl" })

public class PasswordCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PasswordCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("passwordCode"))) {

			request.setAttribute("passwordCode", PropertyReader.getValue("error.require", "Password Code"));

			pass = false;

		} else if (!DataValidator.isCode(request.getParameter("passwordCode"))) {

			request.setAttribute("passwordCode", "Please Enter Valid Password Code");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("username"))) {

			request.setAttribute("username", PropertyReader.getValue("error.require", "Username"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("username"))) {

			request.setAttribute("username", "Please Enter Valid Username");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("passwordValue"))) {

			request.setAttribute("passwordValue", PropertyReader.getValue("error.require", "Password Value"));

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

		PasswordDTO dto = new PasswordDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setPasswordCode(DataUtility.getString(request.getParameter("passwordCode")));

		dto.setUsername(DataUtility.getString(request.getParameter("username")));

		dto.setPasswordValue(DataUtility.getString(request.getParameter("passwordValue")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("PasswordCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		PasswordModelInt model = ModelFactory.getInstance().getPasswordModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			PasswordDTO dto;

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

		log.debug("PasswordCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		PasswordModelInt model = ModelFactory.getInstance().getPasswordModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PasswordDTO dto = (PasswordDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Password already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PasswordDTO dto = (PasswordDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.PASSWORD_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PASSWORD_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PASSWORD_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("PasswordCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.PASSWORD_VIEW;
	}
}