package in.co.rays.project_3.test;

import java.util.List;

import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PhotographerModelInt;

public class TestPhotographerModel {

    public static PhotographerModelInt model = ModelFactory.getInstance().getPhotographerModel();

    public static void main(String[] args) throws Exception {

//         testAdd();
//         testUpdate();
//         testDelete();
//         testFindByPK();
//         testFindByName();
        testSearch();
    }

    // 🔹 Add
    public static void testAdd() throws ApplicationException, DuplicateRecordException {

        PhotographerDTO dto = new PhotographerDTO();

        dto.setPhotographerName("Riya Studio");
        dto.setEventType("Pre Wedding");
        dto.setCharges(31000.0);

        model.add(dto);

        System.out.println("Add Success");
    }

    // 🔹 Update
    public static void testUpdate() throws ApplicationException, DuplicateRecordException {

        PhotographerDTO dto = new PhotographerDTO();

        dto.setId(1L); // existing ID
        dto.setPhotographerName("Aman Studio Updated");
        dto.setEventType("Pre-Wedding");
        dto.setCharges(2000.0);

        model.update(dto);

        System.out.println("Update Success");
    }

    // 🔹 Delete
    public static void testDelete() throws ApplicationException {

        PhotographerDTO dto = new PhotographerDTO();
        dto.setId(3L); // existing ID

        model.delete(dto);

        System.out.println("Delete Success");
    }

    // 🔹 Find By PK
    public static void testFindByPK() throws ApplicationException {

        PhotographerDTO dto = model.findbypk(1);

        if (dto != null) {
            System.out.println("ID: " + dto.getId());
            System.out.println("Name: " + dto.getPhotographerName());
            System.out.println("Event: " + dto.getEventType());
            System.out.println("Charges: " + dto.getCharges());
        } else {
            System.out.println("Record not found");
        }
    }

    // 🔹 Find By Name
    public static void testFindByName() throws ApplicationException {

        PhotographerDTO dto = model.findbyName("Neha Studio");

        if (dto != null) {
            System.out.println("ID: " + dto.getId());
            System.out.println("Name: " + dto.getPhotographerName());
            System.out.println("Event: " + dto.getEventType());
            System.out.println("Charges: " + dto.getCharges());
        } else {
            System.out.println("Record not found");
        }
    }

    // 🔹 Search
    public static void testSearch() throws ApplicationException {

        PhotographerDTO dto = new PhotographerDTO();

        dto.setPhotographerName("Aman"); // filter

        List list = model.search(dto, 1, 10);

        for (Object obj : list) {

            PhotographerDTO p = (PhotographerDTO) obj;

            System.out.println(p.getId() + "\t"
                    + p.getPhotographerName() + "\t"
                    + p.getEventType() + "\t"
                    + p.getCharges());
        }
    }
}