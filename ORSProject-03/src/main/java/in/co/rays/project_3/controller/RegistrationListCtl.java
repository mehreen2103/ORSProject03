package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.RegistrationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RegistrationModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "RegistrationListCtl", urlPatterns = { "/ctl/RegistrationListCtl" })

public class RegistrationListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(RegistrationListCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		RegistrationDTO dto = new RegistrationDTO();

		dto.setRegistrationCode(DataUtility.getString(request.getParameter("registrationCode")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("RegistrationListCtl doGet Start");

		List list = null;
		List next = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		RegistrationDTO dto = (RegistrationDTO) populateDTO(request);

		RegistrationModelInt model = ModelFactory.getInstance().getRegistrationModel();

		try {

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);
			}

			if (next == null || next.size() == 0) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleListDBDown(getView(), dto, pageNo, pageSize, request, response);

			return;
		}

		log.debug("RegistrationListCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("RegistrationListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		RegistrationDTO dto = (RegistrationDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		RegistrationModelInt model = ModelFactory.getInstance().getRegistrationModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.REGISTRATION_CTL, request, response);

				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.REGISTRATION_LIST_CTL, request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					RegistrationDTO deleteDto = new RegistrationDTO();

					for (String id : ids) {

						deleteDto.setId(DataUtility.getLong(id));

						model.delete(deleteDto);
					}

					ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);

				} else {

					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}

			if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.REGISTRATION_LIST_CTL, request, response);

				return;
			}

			dto = (RegistrationDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {

				if (!OP_DELETE.equalsIgnoreCase(op)) {

					ServletUtility.setErrorMessage("No record found", request);
				}
			}

			if (next == null || next.size() == 0) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);

			return;

		} catch (Exception e) {

			e.printStackTrace();
		}

		log.debug("RegistrationListCtl doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.REGISTRATION_LIST_VIEW;
	}
}