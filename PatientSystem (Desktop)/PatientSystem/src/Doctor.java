import java.util.ArrayList;

public class Doctor {
	String name,username,password,securityQ,securityA;
	ArrayList<Patient> Patients = new ArrayList<Patient>();
	
	public Doctor(String name1, String username1, String password1, String securityQ1, String securityA1)
	{
		name = name1;
		username = username1;
		password = password1;
		securityQ = securityQ1;
		securityA = securityA1;
	}	
	
	//setters
	public void setName(String name1)
	{
		name = name1;
	}
	public void setUsername(String username1)
	{
		username = username1;
	}
	
	public void setPassword(String password1)
	{
		password = password1;
	}
	
	public void setSecurityQ(String securityQ1)
	{
		securityQ = securityQ1;
	}
	
	public void setSecurityA(String securityA1)
	{
		securityA = securityA1;
	}
	
	//getters
	public String getName()
	{
		return name;
	}
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getSecurityQ()
	{
		return securityQ;
	}
	
	public String getSecurityA()
	{
		return securityA;
	}
	
	/*
	public String getPatient(Patient patient)
	{
		Patient.get(patient); //NEED GET PATIENT METHOD
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}