
//This is the Patient class that will server to record individual patients information
public class Patient {
	String Name;
	String Username;
	String Password;
	String SecretQuestion;
	String SecretAnswer;
	String CareProvider;
	
	//constructor methods
	public Patient(String pName, String pUsername, String pPassword, String pSecretQuestion, String pSecretAnswer,
			String pCareProvider){
		Name = pName;
		Username = pUsername;
		Password = pPassword;
		SecretQuestion = pSecretQuestion;
		SecretAnswer = pSecretAnswer;
		CareProvider = pCareProvider;
	}
	
	public Patient() {
		Name = "";
		Username = "";
		Password = "";
		SecretQuestion = "";
		SecretAnswer = "";
		CareProvider = "";
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
	
	public void setSecretAnswer(String pSecretAnswer){
		SecretAnswer = pSecretAnswer;
	}
	
	public void setSecretQuestion(String pSecretQuestion){
		SecretQuestion = pSecretQuestion;
	}
	
	public void setCareProvider(String pCareProvider){
		CareProvider = pCareProvider;
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
	
	public String getSecretQuestion(){
		return SecretQuestion;
	}
	
	public String getSecretAnswer(){
		return SecretAnswer;
	}
	
	public String getCareProvider(){
		return CareProvider;
	}
}
