import model.Department;
import repository.DepartmentRepository;
import repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JDBCApplication {
    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/humanresources",
                "root",
                "1234")){
            Repository repository = new DepartmentRepository(conn);
            //Krijojme nje departament dhe i japim emrin
            Department department = new Department();
            department.setName("Flex Core");
            //shtojme departamentin ne databaze
            repository.createDepartment(department);
            //Krijojme nje liste per te ruajtur departamentet dhe i ruajme
            List<Department> departmentList = repository.findAll();
            //me ciklin for shfaqim departamentet e ruajtura
            for(Department d: departmentList){
                System.out.println("Department id: " + d.getDepartmentId() + " Department name: " + d.getName());
            }
            System.out.println();
            //Krijojme nje optional per te ruajtur resultatin dhe shfaqur ne ekran
            Optional<Department> optionalDepartment = repository.findById(3);
            if(optionalDepartment.isPresent()){
                Department departmentresult = optionalDepartment.get();
                System.out.println("Result of method findById " + departmentresult);
            }
            System.out.println();
            List<Department> departmentList1 = repository.findByName("Dev");
            for(Department dep: departmentList1){
                System.out.println("Result of method findByName");
                System.out.println(" " + dep.getDepartmentId() + " " + dep.getName());
            }
            Scanner in = new Scanner(System.in);
            System.out.print("Jepni id qe doni te ndryshoni: ");
            int depId = in.nextInt();
            for (Department dep: departmentList){
                if (dep.getDepartmentId() == depId){
                    repository.updates(dep, "Development");
                }
                System.out.println("Department name: " + dep.getName());
            }
            System.out.println();
            repository.deleteById(2);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
