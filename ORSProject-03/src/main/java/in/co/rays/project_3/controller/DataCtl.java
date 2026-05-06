package in.co.rays.project_3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DataDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DataModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/DataCtl" })
public class DataCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("mappingCode"))) {
            request.setAttribute("mappingCode",
                    PropertyReader.getValue("error.require", "Mapping Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("sourceField"))) {
            request.setAttribute("sourceField",
                    PropertyReader.getValue("error.require", "Source Field"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("targetField"))) {
            request.setAttribute("targetField",
                    PropertyReader.getValue("error.require", "Target Field"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        DataDTO dto = new DataDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setMappingCode(DataUtility.getString(request.getParameter("mappingCode")));
        dto.setSourceField(DataUtility.getString(request.getParameter("sourceField")));
        dto.setTargetField(DataUtility.getString(request.getParameter("targetField")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        DataModelInt model = ModelFactory.getInstance().getDataModel1();

        if (id > 0 || op != null) {
            DataDTO dto;
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

        DataModelInt model = ModelFactory.getInstance().getDataModel1();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            DataDTO dto = (DataDTO) populateDTO(request);

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

            DataDTO dto = (DataDTO) populateDTO(request);

            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.DATA_LIST_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                ServletUtility.handleDBDown(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DATA_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DATA_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.DATA_VIEW;
    }
}