package com.automation.pojos;

//import com.google.gson.annotations.SerializedName;

public class Employees {
//    @SerializedName("employee_id")
    private String employee_id ;
    private String first_name ;
    private String last_name ;
    private String  email;
    private String phone_number ;
    private String hire_date;
    private String job_id ;
    private int salary ;
    private String commission_pct ;
    private int manager_id ;
    private int department_id ;
    private transient String links ;

    public Employees() {
    }

    public Employees(String employee_id, String first_name, String last_name, String email, String phone_number, String hire_date, String job_id, int salary, String commission_pct, int manager_id, int department_id, String links) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.hire_date = hire_date;
        this.job_id = job_id;
        this.salary = salary;
        this.commission_pct = commission_pct;
        this.manager_id = manager_id;
        this.department_id = department_id;
        this.links = links;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "employee_id='" + employee_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", hire_date='" + hire_date + '\'' +
                ", job_id='" + job_id + '\'' +
                ", salary=" + salary +
                ", commission_pct='" + commission_pct + '\'' +
                ", manager_id=" + manager_id +
                ", department_id=" + department_id +
                ", links='" + links + '\'' +
                '}';
    }

    public void setemployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setCommission_pct(String commission_pct) {
        if(commission_pct!=null)
        this.commission_pct = commission_pct;
        else{}
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getemployee_id() {
        return employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getHire_date() {
        return hire_date;
    }

    public String getJob_id() {
        return job_id;
    }

    public int getSalary() {
        return salary;
    }

    public String getCommission_pct() {
        return commission_pct;
    }

    public int getManager_id() {
        return manager_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public String getLinks() {
        return links;
    }
}
