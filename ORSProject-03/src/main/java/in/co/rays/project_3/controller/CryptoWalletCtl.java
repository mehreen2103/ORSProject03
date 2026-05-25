package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CryptoWalletDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CryptoWalletModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/CryptoWalletCtl" })

public class CryptoWalletCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CryptoWalletCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("coinName"))) {

			request.setAttribute("coinName", PropertyReader.getValue("error.require", "Coin Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("coinName"))) {

			request.setAttribute("coinName", "Please Enter Valid Coin Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {

			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("currentPrice"))) {

			request.setAttribute("currentPrice", PropertyReader.getValue("error.require", "Current Price"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalValue"))) {

			request.setAttribute("totalValue", PropertyReader.getValue("error.require", "Total Value"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		CryptoWalletDTO dto = new CryptoWalletDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setCoinName(DataUtility.getString(request.getParameter("coinName")));

		dto.setQuantity(DataUtility.getString(request.getParameter("quantity")));

		dto.setCurrentPrice(DataUtility.getString(request.getParameter("currentPrice")));

		dto.setTotalValue(DataUtility.getString(request.getParameter("totalValue")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("CryptoWalletCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CryptoWalletModelInt model = ModelFactory.getInstance().getCryptoWalletModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			CryptoWalletDTO dto;

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

		log.debug("CryptoWalletCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CryptoWalletModelInt model = ModelFactory.getInstance().getCryptoWalletModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CryptoWalletDTO dto = (CryptoWalletDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("Crypto Wallet already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CryptoWalletDTO dto = (CryptoWalletDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.CRYPTOWALLET_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CRYPTOWALLET_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CRYPTOWALLET_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("CryptoWalletCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.CRYPTOWALLET_VIEW;
	}
}