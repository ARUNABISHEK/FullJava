
class Employee {
    private String name;
    private int salary;
    private String team;
    private int id;	//readonly

    Employee(String name,int sal,String team,int id) {
        this.name = name;
        this.salary = sal;
        this.team = team;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }



}
public class Encaptulation {

    public static void main(String[] args) {

        Employee emp1 = new Employee("Arun",50000,"AppX-meeting-Android",15754);

        System.out.println("salary : " + emp1.getSalary());
        int empId = 15754;


        if(empId == emp1.getId()) {
            emp1.setSalary(60000);
        }
        System.out.println(emp1.getId()+" : "+emp1.getName()+" : "+emp1.getTeam());
        System.out.println("salary : " + emp1.getSalary());

    }

}
