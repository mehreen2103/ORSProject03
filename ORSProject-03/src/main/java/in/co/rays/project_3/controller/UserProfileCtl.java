package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.UserProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.UserProfileModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/UserProfileCtl" })

public class UserProfileCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserProfileCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("profileCode"))) {
			request.setAttribute("profileCode",PropertyReader.getValue("error.require", "Profile Code"));
			pass = false;

		} else if (!DataValidator.isCode(request.getParameter("profileCode"))) {
			request.setAttribute("profileCode", "Please Enter Valid Profile Code");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName",PropertyReader.getValue("error.require", "User Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("userName"))) {
			request.setAttribute("userName", "Please Enter Valid User Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {

			request.setAttribute("mobileNo",PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;

		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {

			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {

			request.setAttribute("status",PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		UserProfileDTO dto = new UserProfileDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setProfileCode(DataUtility.getString(request.getParameter("profileCode")));

		dto.setUserName(DataUtility.getString(request.getParameter("userName")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		log.debug("UserProfileCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserProfileModelInt model = ModelFactory.getInstance().getUserProfileModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			UserProfileDTO dto;

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
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		log.debug("UserProfileCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserProfileModelInt model = ModelFactory.getInstance().getUserProfileModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			UserProfileDTO dto = (UserProfileDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {

					model.add(dto);

					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Profile Code already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserProfileDTO dto = (UserProfileDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.USERPROFILE_LIST_CTL,request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USERPROFILE_LIST_CTL,request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USERPROFILE_CTL,request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserProfileCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.USERPROFILE_VIEW;
	}

}