package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ReportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ReportModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ReportCtl" })

public class ReportCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ReportCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("reportType"))) {

			request.setAttribute("reportType", PropertyReader.getValue("error.require", "Report Type"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("generatedDate"))) {

			request.setAttribute("generatedDate", PropertyReader.getValue("error.require", "Generated Date"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("remarks"))) {

			request.setAttribute("remarks", PropertyReader.getValue("error.require", "Remarks"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ReportDTO dto = new ReportDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setReportType(DataUtility.getString(request.getParameter("reportType")));

		dto.setGeneratedDate(DataUtility.getDate(request.getParameter("generatedDate")));

		dto.setRemarks(DataUtility.getString(request.getParameter("remarks")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("ReportCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ReportModelInt model = ModelFactory.getInstance().getReportModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			ReportDTO dto;

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

		log.debug("ReportCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ReportModelInt model = ModelFactory.getInstance().getReportModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ReportDTO dto = (ReportDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Report Type already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ReportDTO dto = (ReportDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.REPORT_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.REPORT_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.REPORT_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("ReportCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.REPORT_VIEW;
	}
}