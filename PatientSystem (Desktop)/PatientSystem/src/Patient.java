
//This is the Patient class that will server to record individual patients information
public class Patient {
	String Name;
	String Username;
	String Password;
	
	//constructor methods
	public Patient(String pName, String pUsername, String pPassword){
		Name = pName;
		Username = pUsername;
		Password = pPassword;
	}
	
	public Patient() {
		Name = "";
		Username = "";
		Password = "";
	}

	//setter methods
	public void setName(String pName){
		Name = pName;
	}
	
	public void setUsername(String pUsername){
		Username = pUsername;
	}
	
	public void setPassword(String pPassword){
		Password = pPassword;
	}
	
	//getter methods
	public String getName(){
		return Name;
	}
	
	public String getUsername(){
		return Username;
	}
	
	public String getPassword(){
		return Password;
	}
}
