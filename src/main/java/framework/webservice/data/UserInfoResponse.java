package framework.webservice.data;

public class UserInfoResponse {

    private String dateOfBirth;
    private String email;
    private String isAdmin;
    private String name;
    private String superpower;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", name='" + name + '\'' +
                ", superpower='" + superpower + '\'' +
                '}';
    }
}

