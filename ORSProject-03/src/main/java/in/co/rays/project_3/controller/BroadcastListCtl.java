package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BroadcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.BroadcastModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "BroadcastListCtl", urlPatterns = { "/ctl/BroadcastListCtl" })
public class BroadcastListCtl extends BaseCtl {

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        BroadcastDTO dto = new BroadcastDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setBroadcastCode(DataUtility.getString(request.getParameter("broadcastCode")));
        dto.setMessage(DataUtility.getString(request.getParameter("message")));
        dto.setSentBy(DataUtility.getString(request.getParameter("sentBy")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        return dto;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List list;
        List next;

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        BroadcastDTO dto = (BroadcastDTO) populateDTO(request);

        BroadcastModelInt model = ModelFactory.getInstance().getBroadcastModel();

        try {

            list = model.Search(dto, pageNo, pageSize);
            next = model.Search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            ServletUtility.handleListDBDown(getView(), dto, pageNo, pageSize, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        BroadcastDTO dto = (BroadcastDTO) populateDTO(request);

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        BroadcastModelInt model = ModelFactory.getInstance().getBroadcastModel();

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.BROADCAST_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.BROADCAST_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    BroadcastDTO deleteBean = new BroadcastDTO();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage("Data Deleted Successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select atleast one record", request);
                }
            }

            list = model.Search(dto, pageNo, pageSize);
            next = model.Search(dto, pageNo + 1, pageSize);

            if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", "0");
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setDto(dto, request);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            ServletUtility.handleListDBDown(getView(), dto, pageNo, pageSize, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.BROADCAST_LIST_VIEW;
    }
}