package models;

public class Employee extends Person {
    private String job;

    public Employee(String name, Integer age, String job) {
        super(name, age);
        this.job = job;
    }

    public String getJob() {
        return job;
    }
}
