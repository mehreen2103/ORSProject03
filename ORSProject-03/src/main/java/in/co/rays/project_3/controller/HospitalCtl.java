package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HospitalDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.HospitalModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/HospitalCtl" })

public class HospitalCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(HospitalCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("patientName"))) {

			request.setAttribute("patientName", PropertyReader.getValue("error.require", "Patient Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("doctorName"))) {

			request.setAttribute("doctorName", PropertyReader.getValue("error.require", "Doctor Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("disease"))) {

			request.setAttribute("disease", PropertyReader.getValue("error.require", "Disease"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomNumber"))) {

			request.setAttribute("roomNumber", PropertyReader.getValue("error.require", "Room Number"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		HospitalDTO dto = new HospitalDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setPatientName(DataUtility.getString(request.getParameter("patientName")));

		dto.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));

		dto.setDisease(DataUtility.getString(request.getParameter("disease")));

		dto.setRoomNumber(DataUtility.getString(request.getParameter("roomNumber")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("HospitalCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		HospitalModelInt model = ModelFactory.getInstance().getHospitalModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			HospitalDTO dto;

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

		log.debug("HospitalCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		HospitalModelInt model = ModelFactory.getInstance().getHospitalModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HospitalDTO dto = (HospitalDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Patient Name already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			HospitalDTO dto = (HospitalDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.HOSPITAL_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSPITAL_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSPITAL_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("HospitalCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.HOSPITAL_VIEW;
	}
}