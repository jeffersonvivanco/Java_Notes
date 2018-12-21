package models;

public class Student {
    private String fullName;
    private int age;

    public Student(String fullName, int age) {
        if(fullName == null)
            throw new NullPointerException();
        this.fullName = fullName;
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return this.fullName + " " + this.age;
    }
}
