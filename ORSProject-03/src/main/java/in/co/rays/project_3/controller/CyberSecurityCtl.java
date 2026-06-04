package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CyberSecurityDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CyberSecurityModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/CyberSecurityCtl" })

public class CyberSecurityCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CyberSecurityCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("threatType"))) {

			request.setAttribute("threatType", PropertyReader.getValue("error.require", "Threat Type"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("severity"))) {

			request.setAttribute("severity", PropertyReader.getValue("error.require", "Severity"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("detectedTime"))) {

			request.setAttribute("detectedTime", PropertyReader.getValue("error.require", "Detected Time"));

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

		CyberSecurityDTO dto = new CyberSecurityDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setThreatType(DataUtility.getString(request.getParameter("threatType")));

		dto.setSeverity(DataUtility.getString(request.getParameter("severity")));

		dto.setDetectedTime(DataUtility.getDate(request.getParameter("detectedTime")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("CyberSecurityCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CyberSecurityModelInt model = ModelFactory.getInstance().getCyberSecurityModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			CyberSecurityDTO dto;

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

		log.debug("CyberSecurityCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CyberSecurityModelInt model = ModelFactory.getInstance().getCyberSecurityModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CyberSecurityDTO dto = (CyberSecurityDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Threat Type already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CyberSecurityDTO dto = (CyberSecurityDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.CYBERSECURITY_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CYBERSECURITY_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CYBERSECURITY_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("CyberSecurityCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.CYBERSECURITY_VIEW;
	}
}