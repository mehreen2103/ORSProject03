package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CourierDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CourierModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/CourierCtl" })

public class CourierCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CourierCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("senderName"))) {

			request.setAttribute("senderName", PropertyReader.getValue("error.require", "Sender Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("senderName"))) {

			request.setAttribute("senderName", "Please Enter Valid Sender Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("recieverName"))) {

			request.setAttribute("recieverName", PropertyReader.getValue("error.require", "Receiver Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("recieverName"))) {

			request.setAttribute("recieverName", "Please Enter Valid Receiver Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("weight"))) {

			request.setAttribute("weight", PropertyReader.getValue("error.require", "Weight"));

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

		CourierDTO dto = new CourierDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setSenderName(DataUtility.getString(request.getParameter("senderName")));

		dto.setRecieverName(DataUtility.getString(request.getParameter("recieverName")));

		dto.setWeight(DataUtility.getString(request.getParameter("weight")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("CourierCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CourierModelInt model = ModelFactory.getInstance().getCourierModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			CourierDTO dto;

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

		log.debug("CourierCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CourierModelInt model = ModelFactory.getInstance().getCourierModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CourierDTO dto = (CourierDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Courier already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CourierDTO dto = (CourierDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.COURIER_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURIER_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURIER_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("CourierCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.COURIER_VIEW;
	}
}