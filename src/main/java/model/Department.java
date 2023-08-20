package model;

public class Department {
    private int DepartmentId;
    private String name;

    public Department() {

    }
    public Department(int departmentId, String name) {
        DepartmentId = departmentId;
        this.name = name;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
