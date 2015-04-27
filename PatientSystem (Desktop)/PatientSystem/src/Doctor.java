import javax.swing.DefaultListModel;

public class Doctor {
	String name, username, password, securityQ, securityA, phone, email;
	private DefaultListModel<String> patientList = new DefaultListModel<String>();
	
	//Constructor
	public Doctor(String name1, String username1, String password1, String securityQ1, String securityA1, String phone1, String email1)
	{
		name = name1;
		username = username1;
		password = password1;
		securityQ = securityQ1;
		securityA = securityA1;
		phone = phone1;
		email = email1;
	}	
	
	public Doctor(){
		name = "";
		username = "";
		password = "";
		securityQ = "";
		securityA = "";
		phone = "";
		email = "";
		
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
	
	public void setPhone(String phone1)
	{
		phone = phone1;
	}
	
	public void setEmail(String email1)
	{
		email = email1;
	}
	
	public void addPatientName(String pPatientName){
		patientList.addElement(pPatientName);
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
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public DefaultListModel<String> getList(){
		return patientList;
	}
	
	public boolean isPatientListEmpty(){
		return patientList.isEmpty();
	}
	
	public String getPatientName(int index){
		String name = patientList.get(index);
		return name;
	}
	
	public int getPatientCount(){
		return patientList.getSize();
	}
}
