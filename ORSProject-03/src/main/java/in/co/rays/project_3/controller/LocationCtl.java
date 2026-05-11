package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LocationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.LocationModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/LocationCtl" })

public class LocationCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LocationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("city"))) {

			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("city"))) {

			request.setAttribute("city", "Please Enter Valid City");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {

			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("state"))) {

			request.setAttribute("state", "Please Enter Valid State");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("country"))) {

			request.setAttribute("country", PropertyReader.getValue("error.require", "Country"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("country"))) {

			request.setAttribute("country", "Please Enter Valid Country");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("locationstatus"))) {

			request.setAttribute("locationstatus", PropertyReader.getValue("error.require", "Location Status"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		LocationDTO dto = new LocationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setCity(DataUtility.getString(request.getParameter("city")));

		dto.setState(DataUtility.getString(request.getParameter("state")));

		dto.setCountry(DataUtility.getString(request.getParameter("country")));

		dto.setLocationstatus(DataUtility.getString(request.getParameter("locationstatus")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("LocationCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		LocationModelInt model = ModelFactory.getInstance().getLocationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			LocationDTO dto;

			try {

				dto = model.FindByPK(id);

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

		log.debug("LocationCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		LocationModelInt model = ModelFactory.getInstance().getLocationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			LocationDTO dto = (LocationDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("City already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			LocationDTO dto = (LocationDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.LOCATION_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {
				
				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOCATION_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOCATION_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("LocationCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.LOCATION_VIEW;
	}
}