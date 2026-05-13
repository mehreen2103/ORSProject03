package in.co.rays.project_3.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.WeatherDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.WeatherModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/WeatherCtl" })

public class WeatherCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(WeatherCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("alertCode"))) {

			request.setAttribute("alertCode", PropertyReader.getValue("error.require", "Alert Code"));

			pass = false;

		} else if (!DataValidator.isCode(request.getParameter("alertCode"))) {

			request.setAttribute("alertCode", "Please Enter Valid Alert Code");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cityName"))) {

			request.setAttribute("cityName", PropertyReader.getValue("error.require", "City Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("cityName"))) {

			request.setAttribute("cityName", "Please Enter Valid City Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("temperature"))) {

			request.setAttribute("temperature", PropertyReader.getValue("error.require", "Temperature"));

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

		WeatherDTO dto = new WeatherDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setAlertCode(DataUtility.getString(request.getParameter("alertCode")));

		dto.setCityName(DataUtility.getString(request.getParameter("cityName")));

		dto.setTemperature(DataUtility.getString(request.getParameter("temperature")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("WeatherCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		WeatherModelInt model = ModelFactory.getInstance().getWeatherModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			WeatherDTO dto;

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

		log.debug("WeatherCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		WeatherModelInt model = ModelFactory.getInstance().getWeatherModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			WeatherDTO dto = (WeatherDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Weather already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			WeatherDTO dto = (WeatherDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.WEATHER_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WEATHER_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.WEATHER_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("WeatherCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.WEATHER_VIEW;
	}
}