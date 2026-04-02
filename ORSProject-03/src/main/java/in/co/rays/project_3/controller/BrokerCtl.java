package in.co.rays.project_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BrokerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BrokerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/BrokerCtl" })
public class BrokerCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("brokerName"))) {
			request.setAttribute("brokerName", PropertyReader.getValue("error.require", "Broker Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", PropertyReader.getValue("error.require", "Contact Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("company"))) {
			request.setAttribute("company", PropertyReader.getValue("error.require", "Company"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BrokerDTO dto = new BrokerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setBrokerName(DataUtility.getString(request.getParameter("brokerName")));
		dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));
		dto.setCompany(DataUtility.getString(request.getParameter("company")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		BrokerModelInt model = ModelFactory.getInstance().getBrokeModel();

		if (id > 0 || op != null) {
			BrokerDTO dto;
			try {
				dto = model.findByPk(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				ServletUtility.handleDBDown(getView(), request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		BrokerModelInt model = ModelFactory.getInstance().getBrokeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BrokerDTO dto = (BrokerDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data updated successfully", request);
					ServletUtility.setDto(dto, request);

				} else {

					try {
						model.add(dto);
						System.out.println(" IN Do Post Methidd...");
						ServletUtility.setSuccessMessage("Data saved successfully", request);

					} catch (DuplicateRecordException e) {
						e.printStackTrace();
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Broker already exists", request);
					}
				}

			} catch (ApplicationException e) {
				ServletUtility.handleDBDown(getView(), request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Broker already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BrokerDTO dto = (BrokerDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BROKER_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				ServletUtility.handleDBDown(getView(), request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BROKER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BROKER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BROKER_VIEW;
	}
}