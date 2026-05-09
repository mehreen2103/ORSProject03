
package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.HostelModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/HostelCtl" })

public class HostelCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(HostelCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("roomNo"))) {
			request.setAttribute("roomNo", PropertyReader.getValue("error.require", "Room No"));
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("roomNo"))) {
			request.setAttribute("roomNo", "Please Enter Valid Room No.");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("capacity"))) {

			request.setAttribute("capacity", PropertyReader.getValue("error.require", "Capacity"));

			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("joinDate"))) {

			request.setAttribute("joinDate", PropertyReader.getValue("error.require", "Join Date"));

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

		HostelDTO dto = new HostelDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoomNo(DataUtility.getString(request.getParameter("roomNo")));

		dto.setCapacity(DataUtility.getString(request.getParameter("capacity")));

		System.out.println(request.getParameter("joinDate"));

		dto.setJoinDate(DataUtility.getDate(request.getParameter("joinDate")));

		System.out.println("date: " + dto.getJoinDate());
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("HostelCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		HostelModelInt model = ModelFactory.getInstance().getHostelModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			HostelDTO dto;

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

		log.debug("HostelCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		HostelModelInt model = ModelFactory.getInstance().getHostelModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HostelDTO dto = (HostelDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
					ServletUtility.setDto(dto, request);

				} else {

					model.add(dto);

					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
				}

//				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Room No already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			HostelDTO dto = (HostelDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.HOSTEL_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSTEL_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSTEL_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("HostelCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.HOSTEL_VIEW;
	}

}