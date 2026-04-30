package in.co.rays.project_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.StockModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/StockCtl" })
public class StockCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("stockName"))) {
            request.setAttribute("stockName",
                    PropertyReader.getValue("error.require", "Stock Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price",
                    PropertyReader.getValue("error.require", "Price"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("quantity"))) {
            request.setAttribute("quantity",
                    PropertyReader.getValue("error.require", "Quantity"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        StockDTO dto = new StockDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setStockName(DataUtility.getString(request.getParameter("stockName")));
        dto.setPrice(DataUtility.getString(request.getParameter("price")));
        dto.setQuantity(DataUtility.getString(request.getParameter("quantity")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        StockModelInt model = ModelFactory.getInstance().getStockModel();

        if (id > 0 || op != null) {
            StockDTO dto;
            try {
                dto = model.findByPk(id);
                ServletUtility.setDto(dto, request);

            } catch (ApplicationException e) {
                ServletUtility.handleDBDown(getView(), request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        StockModelInt model = ModelFactory.getInstance().getStockModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            StockDTO dto = (StockDTO) populateDTO(request);

            try {

                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Data updated successfully", request);
                    ServletUtility.setDto(dto, request);

                } else {

                    try {
                        model.add(dto);
                        ServletUtility.setSuccessMessage("Data saved successfully", request);

                    } catch (DuplicateRecordException e) {
                        ServletUtility.setDto(dto, request);
                        ServletUtility.setErrorMessage("Record already exists", request);
                    }
                }

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleDBDown(getView(), request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage("Record already exists", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            StockDTO dto = (StockDTO) populateDTO(request);

            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.STOCK_LIST_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                ServletUtility.handleDBDown(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.STOCK_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.STOCK_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.STOCK_VIEW;
    }
}