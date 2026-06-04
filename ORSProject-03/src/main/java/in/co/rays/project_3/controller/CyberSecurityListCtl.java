package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CyberSecurityDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.CyberSecurityModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "CyberSecurityListCtl", urlPatterns = { "/ctl/CyberSecurityListCtl" })

public class CyberSecurityListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CyberSecurityListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<String, String>();

		statusMap.put("Open", "Open");
		statusMap.put("Investigating", "Investigating");
		statusMap.put("Resolved", "Resolved");
		statusMap.put("Closed", "Closed");

		request.setAttribute("statusMap", statusMap);

		HashMap<String, String> severityMap = new HashMap<String, String>();

		severityMap.put("Low", "Low");
		severityMap.put("Medium", "Medium");
		severityMap.put("High", "High");
		severityMap.put("Critical", "Critical");

		request.setAttribute("severityMap", severityMap);
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		CyberSecurityDTO dto = new CyberSecurityDTO();

		dto.setThreatType(DataUtility.getString(request.getParameter("threatType")));

		dto.setSeverity(DataUtility.getString(request.getParameter("severity")));

		dto.setDetectedTime(DataUtility.getDate(request.getParameter("detectedTime")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CyberSecurityListCtl doGet Start");

		List list = null;
		List next = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CyberSecurityDTO dto = (CyberSecurityDTO) populateDTO(request);

		CyberSecurityModelInt model = ModelFactory.getInstance().getCyberSecurityModel();

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

		log.debug("CyberSecurityListCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CyberSecurityListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CyberSecurityDTO dto = (CyberSecurityDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		CyberSecurityModelInt model = ModelFactory.getInstance().getCyberSecurityModel();

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

				ServletUtility.redirect(ORSView.CYBERSECURITY_CTL, request, response);

				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.CYBERSECURITY_LIST_CTL, request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					CyberSecurityDTO deleteDto = new CyberSecurityDTO();

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

				ServletUtility.redirect(ORSView.CYBERSECURITY_LIST_CTL, request, response);

				return;
			}

			dto = (CyberSecurityDTO) populateDTO(request);

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

		log.debug("CyberSecurityListCtl doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.CYBERSECURITY_LIST_VIEW;
	}
}