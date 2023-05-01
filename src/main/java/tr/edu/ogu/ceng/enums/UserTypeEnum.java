package tr.edu.ogu.ceng.enums;

public enum UserTypeEnum {
    STUDENT(1),
    COMPANYSUPERVISOR(2),
    FACULTYSUPERVISOR(3),
	ADMIN(4);

    private long id;

    UserTypeEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}