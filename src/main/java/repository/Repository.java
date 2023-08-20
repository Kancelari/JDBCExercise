package repository;

import model.Department;

import java.util.List;
import java.util.Optional;

public interface Repository {
    List<Department> findAll();
    Optional<Department> findById(int departmentId);
    void deleteById(int departmentId);
    void createDepartment(Department department);
    void updates (Department department, String name);
    List<Department> findByName(String name);
}
