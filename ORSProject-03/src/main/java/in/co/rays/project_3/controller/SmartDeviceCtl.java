package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SmartDeviceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SmartDeviceModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/SmartDeviceCtl" })

public class SmartDeviceCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SmartDeviceCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("deviceName"))) {

			request.setAttribute("deviceName", PropertyReader.getValue("error.require", "Device Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("room"))) {

			request.setAttribute("room", PropertyReader.getValue("error.require", "Room"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {

			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("doubleUsage"))) {

			request.setAttribute("doubleUsage", PropertyReader.getValue("error.require", "Double Usage"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SmartDeviceDTO dto = new SmartDeviceDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setDeviceName(DataUtility.getString(request.getParameter("deviceName")));

		dto.setRoom(DataUtility.getString(request.getParameter("room")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		dto.setDoubleUsage(DataUtility.getString(request.getParameter("doubleUsage")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("SmartDeviceCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SmartDeviceModelInt model = ModelFactory.getInstance().getSmartDeviceModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			SmartDeviceDTO dto;

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

		log.debug("SmartDeviceCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SmartDeviceModelInt model = ModelFactory.getInstance().getSmartDeviceModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SmartDeviceDTO dto = (SmartDeviceDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Device Name already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			SmartDeviceDTO dto = (SmartDeviceDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.SMARTDEVICE_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SMARTDEVICE_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SMARTDEVICE_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("SmartDeviceCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.SMARTDEVICE_VIEW;
	}
}