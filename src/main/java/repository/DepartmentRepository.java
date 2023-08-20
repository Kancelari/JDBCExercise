package repository;

import model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository implements Repository{
    private final Connection conn;
    private final String message = "Failed to connect to database: ";
    public DepartmentRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try(Statement findAllStmt = conn.createStatement()){
            ResultSet rs = findAllStmt.executeQuery("select * from departments");
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                departments.add(new Department(id, name));
            }
            }catch(SQLException e){
            throw new DatabaseActionException( message, e);
        }
        return departments;
    }

    @Override
    public Optional<Department> findById(int departmentId) {
        try (PreparedStatement ptmt = conn.prepareStatement("Select * from departments where departmentId = ?")){
           ptmt.setInt(1,departmentId);
           ResultSet rs = ptmt.executeQuery();
           while(rs.next()){
               int id = rs.getInt(1);
               String name = rs.getString(2);
               return Optional.of(new Department(id, name));
           }
        }catch (SQLException e){
            throw new DatabaseActionException(message, e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int departmentId) {
        try (PreparedStatement ptmt = conn.prepareStatement("Delete from departments where departmentId = ?")){
            ptmt.setInt(1,departmentId);
            int affctedRows = ptmt.executeUpdate();
                System.out.println("Affected rows " + affctedRows);
        }catch (SQLException e){
            throw new DatabaseActionException(message, e);
        }
    }

    @Override
    public void createDepartment(Department department) {
        try (PreparedStatement createDepartmentStmt = conn.prepareStatement("insert into departments " +
                "(name)" +
                "VALUES(?)")){
            createDepartmentStmt.setString(1, department.getName());
            int affectedRows = createDepartmentStmt.executeUpdate();
            System.out.println("Affected rows " + affectedRows);

        }catch (SQLException e){
            throw new DatabaseActionException(message, e);
        }
    }

    @Override
    public void updates(Department department, String name) {
            try (PreparedStatement updateStmt = conn.prepareStatement("update departments set name= ? where departmentid =?")){
                updateStmt.setString(1,name);
                updateStmt.setInt(2, department.getDepartmentId());
                int affectedrows = updateStmt.executeUpdate();
                System.out.println("Affected rows " + affectedrows);
            }catch (SQLException e){
                throw new DatabaseActionException(message, e);
            }
    }

    @Override
    public List<Department> findByName(String name) {
        List<Department> departments = new ArrayList<>();
        try(PreparedStatement ptmt = conn.prepareStatement("Select * from departments where name = ?")){
            ptmt.setString(1, name);
            ResultSet rs = ptmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String depName = rs.getString(2);
                departments.add(new Department(id, depName));
            }
        }catch (SQLException e){
            throw new DatabaseActionException(message, e);
        }
        return departments;
    }
}
