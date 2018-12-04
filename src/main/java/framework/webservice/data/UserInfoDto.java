package framework.webservice.data;

public class UserInfoDto {

    private String isadmin;
    private String dateOfBirth;
    private String email;
    private String name;
    private String pw;
    private String superpower;

    public UserInfoDto(String isadmin, String dateOfBirth, String email, String name, String pw, String superpower) {
        this.isadmin = isadmin;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.name = name;
        this.pw = pw;
        this.superpower = superpower;
    }

    public UserInfoDto() {
    }

    public String getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }
}
