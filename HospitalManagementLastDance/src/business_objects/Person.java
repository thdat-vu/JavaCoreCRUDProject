
package business_objects;

/**
 *
 * @author LENOVO
 */
public abstract class Person {
    protected String id;
    protected String name;
    protected String gender;
    protected int age;
    protected String address;
    protected String phone;

    public Person() {
    }

    public Person(String id, String name, int age, String gender, String address, String phone) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", address=" + address + ", phone=" + phone ;
    }
}
