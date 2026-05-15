package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PodcastModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PodcastCtl" })

public class PodcastCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PodcastCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("podcastCode"))) {

			request.setAttribute("podcastCode", PropertyReader.getValue("error.require", "Podcast Code"));

			pass = false;

		} else if (!DataValidator.isCode(request.getParameter("podcastCode"))) {

			request.setAttribute("podcastCode", "Please Enter Valid Podcast Code");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("podcastTitle"))) {

			request.setAttribute("podcastTitle", PropertyReader.getValue("error.require", "Podcast Title"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("podcastTitle"))) {

			request.setAttribute("podcastTitle", "Please Enter Valid Podcast Title");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("hostname"))) {

			request.setAttribute("hostname", PropertyReader.getValue("error.require", "Hostname"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("hostname"))) {

			request.setAttribute("hostname", "Please Enter Valid Hostname");

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

		PodcastDTO dto = new PodcastDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setPodcastCode(DataUtility.getString(request.getParameter("podcastCode")));

		dto.setPodcastTitle(DataUtility.getString(request.getParameter("podcastTitle")));

		dto.setHostname(DataUtility.getString(request.getParameter("hostname")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("PodcastCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			PodcastDTO dto;

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

		log.debug("PodcastCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		PodcastModelInt model = ModelFactory.getInstance().getPodcastModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PodcastDTO dto = (PodcastDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Podcast already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PodcastDTO dto = (PodcastDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PODCAST_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PODCAST_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("PodcastCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.PODCAST_VIEW;
	}
}