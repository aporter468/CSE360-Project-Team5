public class ProviderInformation {
    private int providerid;
    private String name;
    private String email;
    private int phone;

    public ProviderInformation(int providerid, String name, String email, int phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getProviderid() {
        return providerid;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
