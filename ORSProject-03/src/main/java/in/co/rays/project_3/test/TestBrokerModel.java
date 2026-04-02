package in.co.rays.project_3.test;

import in.co.rays.project_3.dto.BrokerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BrokerModelHibImpl;
import in.co.rays.project_3.model.BrokerModelInt;

import java.util.List;

public class TestBrokerModel {

    public static BrokerModelInt model = new BrokerModelHibImpl();

    public static void main(String[] args) throws Exception {

         testAdd();
//         testUpdate();
//         testDelete();
//         testFindByPk();
//         testFindByName();
//        testSearch();
    }

    // ------------------- ADD -------------------
    public static void testAdd() throws ApplicationException, DuplicateRecordException {

        BrokerDTO dto = new BrokerDTO();

        dto.setBrokerName("Rahul Broker");
        
        dto.setContactNumber("851651815");
        dto.setCompany(" Alaska Broker");

        model.add(dto);

        System.out.println("Add Success");
    }

    // ------------------- UPDATE -------------------
    public static void testUpdate() throws ApplicationException, DuplicateRecordException {

        BrokerDTO dto = model.findByPk(1);

        dto.setBrokerName("Updated Broker");
        dto.setCompany("Zerodha");

        model.update(dto);

        System.out.println("Update Success");
    }

    // ------------------- DELETE -------------------
    public static void testDelete() throws ApplicationException {

        BrokerDTO dto = new BrokerDTO();
        dto.setId(1L);

        model.delete(dto);

        System.out.println("Delete Success");
    }

    // ------------------- FIND BY PK -------------------
    public static void testFindByPk() throws ApplicationException {

        BrokerDTO dto = model.findByPk(2);

        if (dto != null) {
            System.out.println("ID: " + dto.getId());
            System.out.println("Name: " + dto.getBrokerName());
            System.out.println("Company: " + dto.getCompany());
        } else {
            System.out.println("Record Not Found");
        }
    }

    // ------------------- FIND BY NAME -------------------
    public static void testFindByName() throws ApplicationException {

        BrokerDTO dto = model.findBYName("Rahul Broker");

        if (dto != null) {
            System.out.println("ID: " + dto.getId());
            System.out.println("Name: " + dto.getBrokerName());
            System.out.println("Company: " + dto.getCompany());
        } else {
            System.out.println("Record Not Found");
        }
    }

    // ------------------- SEARCH -------------------
    public static void testSearch() throws ApplicationException {

        BrokerDTO dto = new BrokerDTO();

        dto.setBrokerName("A"); // filter

        List list = model.search(dto, 1, 10);

        for (Object obj : list) {
            BrokerDTO bdto = (BrokerDTO) obj;

            System.out.println(
                    bdto.getId() + "\t" +
                    bdto.getBrokerName() + "\t" +
                    bdto.getCompany()
            );
        }
    }
}