package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.NFTAssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.NFTAssetModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/NFTAssetCtl" })

public class NFTAssetCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(NFTAssetCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("nftCode"))) {

			request.setAttribute("nftCode", PropertyReader.getValue("error.require", "NFT Code"));

			pass = false;

		} else if (!DataValidator.isCode(request.getParameter("nftCode"))) {

			request.setAttribute("nftCode", "Please Enter Valid NFT Code");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assetName"))) {

			request.setAttribute("assetName", PropertyReader.getValue("error.require", "Asset Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("assetName"))) {

			request.setAttribute("assetName", "Please Enter Valid Asset Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("ownerName"))) {

			request.setAttribute("ownerName", PropertyReader.getValue("error.require", "Owner Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("ownerName"))) {

			request.setAttribute("ownerName", "Please Enter Valid Owner Name");

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

		NFTAssetDTO dto = new NFTAssetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setNftCode(DataUtility.getString(request.getParameter("nftCode")));

		dto.setAssetName(DataUtility.getString(request.getParameter("assetName")));

		dto.setOwnerName(DataUtility.getString(request.getParameter("ownerName")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("NFTAssetCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		NFTAssetModelInt model = ModelFactory.getInstance().getNFTAssetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			NFTAssetDTO dto;

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

		log.debug("NFTAssetCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		NFTAssetModelInt model = ModelFactory.getInstance().getNFTAssetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			NFTAssetDTO dto = (NFTAssetDTO) populateDTO(request);

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

				ServletUtility.setErrorMessage("NFT Asset already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			NFTAssetDTO dto = (NFTAssetDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.NFTASSET_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				log.error(e);

				ServletUtility.handleDBDown(getView(), request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.NFTASSET_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.NFTASSET_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("NFTAssetCtl doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.NFTASSET_VIEW;
	}
}