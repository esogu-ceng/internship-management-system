package tr.edu.ogu.ceng.enums;

public enum UserTypeEnum {
    STUDENT(1),
    COMPANYSUPERVISORS(2),
    FACULTYSUPERVISORS(3);

    private long id;

    UserTypeEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}