package BOLayer;

import java.io.Serializable;
public class Admin implements Serializable{
    private String Username;
    private String Pin;

    public Admin(String username, String pin){
        setUsername(username);
        setPin(pin);
    }

    public Admin() {

    }

    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getPin() {
        return Pin;
    }
    public void setPin(String pin) {
        Pin = pin;
    }
}
