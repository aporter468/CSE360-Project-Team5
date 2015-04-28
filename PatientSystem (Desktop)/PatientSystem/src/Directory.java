import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Directory {
    //declaration of private variables
	private JFrame frmEsasSystem;
	private JPanel panelLogin; 
	private JPanel panelSignUp;
	private JPanel panelForgottenCredentials;
	private JPanel panelMainMenu;
	private JPanel panelCompleteSurvey;
	private JPanel panelViewCareProviderInfo;
	private JPanel panelViewHistory;
	private JPanel panelDoctorSignUp;
	private JPanel panelMainMenuDoctor;
	private JPanel panelViewHistoryDoctor;
	private JPanel panelViewPatientInfo;
	
	//textField and passwordField from Login Panel
	private JTextField textField_LoginUsername;
	private JPasswordField passwordField_Login;
	
	//textFields and PasswordField from Sign Up Panel
	private JTextField textField_SignUpFirstName;
	private JTextField textField_SignUpLastName;
	private JTextField textField_SignUpUsername;
	private JTextField textField_SignUpSecretQuestion;
	private JTextField textField_SignUp_PhoneNumber;
	private JPasswordField passwordField_SignUpPassword;
	private JPasswordField passwordField_SignUpConfirmPassword;
	private JTextField textField_SignUpSecretAnswer;

	//textFields from panelForgottenCredentials
	private JTextField textField_ForgottenCredentialsUsername;
	private JTextField textField_ForgottenCredentialsSecretAnswer;
	private JTextField textField_ForgottenCredentialsSecretQuestion;
	
	//JSpinner, JList, and JTextArea from CompleteSurvey Panel
	private JSpinner spinnerCompleteSurveyDate;
	private JList <String>survey_list;
	private JTextArea textArea_ViewHistory_Surveys;
	private JScrollPane scroll_survey_list;
	
	//Patient List & Doctor List
	private ArrayList<Patient> PatientList = new ArrayList<Patient>();
	private ArrayList<Doctor> DoctorList = new ArrayList<Doctor>();
	private Patient currentPatient = new Patient();
	private Doctor currentDoctor = new Doctor();
	
	//textFields and PasswordFields for the Doctor SignUp Panel
	private JTextField textField_DoctorSignUp_FirstName;
	private JTextField textField_DoctorSignUp_LastName;
	private JTextField textField_DoctorSignUp_Username;
	private JTextField textField_DoctorSignUp_SecurityQuestion;
	private JTextField textField_DoctorSignUp_SecurityAnswer;
	private JPasswordField passwordField_DoctorSignUp_Password;
	private JPasswordField passwordField_DoctorSignUp_ConfirmPassword;
	private JTextField textField_DoctorPhone;
	private JTextField textField_DoctorEmail;
	
	//JList and JTextArea from ViewPatientHistory as a Doctor
	private JList <String>patient_list;
	private JTextArea textArea_ViewPatient_Names;
	private JScrollPane scroll_patient_list;
	private JList <String> survey_list_doctor;
	private JScrollPane scroll_survey_list_doctor;
    
	//JList and JTextArea from ViewPatientInfo as a Doctor
	private JList <String>patient_list_view_patient_info;
	private JTextArea textArea_ViewPatient_Info;
	private JScrollPane scroll_patient_list_view_patient_info;
	
	//Put the Button here to access later
	JButton btnLogin = new JButton("Log In");  // Log In 
	JButton btnMainMenuViewHistory = new JButton("View History"); //View History button

	
	//main method
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Directory window = new Directory(); //instantiate a new Directory
					window.loadDoctors(); //load information from file
					window.loadPatients(); //load information from file
					window.frmEsasSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//constructor method
	public Directory(){
		initialize();
	}

	//initialize contents of frame
	private void initialize() {
		frmEsasSystem = new JFrame();
		frmEsasSystem.setTitle("ESAS System");
		frmEsasSystem.setBounds(100, 100, 450, 300);
		frmEsasSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Set Exit_On_close behavior when window is closed
		frmEsasSystem.getContentPane().setLayout(new CardLayout(0, 0)); //set CardLayout
		
		//the following method overrides the DefaultCloseOperation to be able to execute the saving methods when closing the application
		frmEsasSystem.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        try {
					savePatientToFile();
					saveDoctorToFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		    	System.exit(0);
		    }
		});
		
		//Login Panel ************************************************************************************
		panelLogin = new JPanel();
		frmEsasSystem.getContentPane().add(panelLogin, "name_136526590665903");
		panelLogin.setLayout(null);
		
		btnLogin.addActionListener(new ActionListener() { //Action Listener for Log In Button
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				//check if user name exists
				//call the function findUser that will find if the user name is in the Patient List
				int position = findPatient(textField_LoginUsername.getText());
				if (position >= 0){ //check to see if the user name was found and in the Patient List
					Patient temp = new Patient(); //make a temporary patient
					temp = PatientList.get(position); //retrieve the corresponding user
					if (passwordField_Login.getText().equals(temp.getPassword())){ //check that password matches stored password
						textField_LoginUsername.setText("");//clear the fields
						passwordField_Login.setText("");
						panelLogin.setVisible(false); //set the panelLogin to not visible
						panelMainMenu.setVisible(true);  //set the MainMenu panel to visible
						currentPatient = temp;  //save the currently logged patient in currentPatient
					}
					else {//display a message if the password is incorrect
						JOptionPane.showMessageDialog(null, "Incorrect password. Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else { //look for the user name in the Doctor List
						position = findDoctor(textField_LoginUsername.getText());
						Doctor tempDoctor = new Doctor();
						if (position >=0) {
							tempDoctor = DoctorList.get(position); //retrieve the corresponding user
							textField_LoginUsername.setText("");//clear the fields
							passwordField_Login.setText("");
							panelMainMenuDoctor.setVisible(true); //set the MainMenuDoctor panel to visible
							panelLogin.setVisible(false);//set the panelLogin to not visible
							currentDoctor = tempDoctor; //save the currently logged patient in currentDoctor
							currentDoctor = tempDoctor;
						}
						
					} if (position == -1){ //if the user is not in the Patient List, then check the Doctor List
						position = findDoctor(textField_LoginUsername.getText());
						if (position >=0 ){ //the user name was found in the Doctor List
							Doctor tempDoctor = new Doctor(); //make a temporary doctor
							tempDoctor = DoctorList.get(position); //retrieve the corresponding user
							if (passwordField_Login.getText().equals(tempDoctor.getPassword())){ //check that password matches stored password
								textField_LoginUsername.setText("");//clear the fields
								passwordField_Login.setText("");
								panelLogin.setVisible(false); //set the panelLogin to not visible
								panelMainMenu.setVisible(true);  //set the MainMenu panel to visible
								currentDoctor = tempDoctor;
							}
							else { //display a message if the password is incorrect
								JOptionPane.showMessageDialog(null, "Incorrect Password. Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						else if (position==-1){//display message if user name does not exist
							JOptionPane.showMessageDialog(null, "Username does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} 
				
				}
		});
		btnLogin.setBounds(207, 154, 89, 23);
		panelLogin.add(btnLogin); //add the Login button to the JPanel
		
		JButton btnLoginSignUp = new JButton("Sign Up"); //SignUp Button
		btnLoginSignUp.addActionListener(new ActionListener() { //Action Listener for SignUp Button
			public void actionPerformed(ActionEvent e) {
				textField_LoginUsername.setText(""); //clear the user name and password fields
				passwordField_Login.setText("");
				panelLogin.setVisible(false); //set the Login panel to not visible
				panelSignUp.setVisible(true); //set the sign up panel to visible
			}
		});
		btnLoginSignUp.setBounds(108, 154, 89, 23);
		panelLogin.add(btnLoginSignUp);    //add the sign up button to the JPanel
		
		textField_LoginUsername = new JTextField(); //textField for Login User name
		textField_LoginUsername.setBounds(181, 85, 112, 20);
		panelLogin.add(textField_LoginUsername); //add the textField to the panel Login
		textField_LoginUsername.setColumns(10);
		
		JButton btnLoginForgottenCredentials = new JButton("Forgot you password?"); //Forgot Password Button
		btnLoginForgottenCredentials.addActionListener(new ActionListener() { //Action Listener for Forgot Pass button
			public void actionPerformed(ActionEvent e) {
				textField_LoginUsername.setText(""); //clear the textField and passwordField 
				passwordField_Login.setText("");
				panelLogin.setVisible(false); //set Login panel to not visible
				panelForgottenCredentials.setVisible(true); //set forgotten credentials panel to visible
			}
		});
		btnLoginForgottenCredentials.setBounds(108, 184, 188, 23);
		panelLogin.add(btnLoginForgottenCredentials);  //add the Forgotten Password button to the JPanel
		
		JLabel lblLoginTitle = new JLabel("Welcome to the ESAS System"); //Window Title
		lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLoginTitle.setBounds(73, 23, 272, 23);
		panelLogin.add(lblLoginTitle);
		
		JLabel lblLoginUsername = new JLabel("Username:");  //User name Label
		lblLoginUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginUsername.setBounds(108, 88, 65, 14);
		panelLogin.add(lblLoginUsername);
		
		JLabel lblLoginPassword = new JLabel("Password:");  //password label
		lblLoginPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginPassword.setBounds(108, 119, 65, 14);
		panelLogin.add(lblLoginPassword);
		
		passwordField_Login = new JPasswordField();   //JPasswordField for the password
		passwordField_Login.setBounds(181, 116, 112, 20);
		panelLogin.add(passwordField_Login);
		JButton btnDoctorSignUp = new JButton("Doctor Sign Up"); //JButton to navigate to the Doctor Sign Up panel
		btnDoctorSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelLogin.setVisible(false); //change the visibility of the panels
				panelDoctorSignUp.setVisible(true);
			}
		});
		btnDoctorSignUp.setBounds(108, 216, 188, 23); //set bounds
		panelLogin.add(btnDoctorSignUp);
		
		panelLogin.setVisible(true); //Set the panelLogin to be visible
		//----------------------------------------------------------------------------------------------------
		
		//SignUp Panel ***************************************************************************************
		panelSignUp = new JPanel();
		frmEsasSystem.getContentPane().add(panelSignUp, "name_136909708117179");
		panelSignUp.setLayout(null);
		
		JLabel lblSignUpTitle = new JLabel("Create a New User");
		lblSignUpTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUpTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSignUpTitle.setBounds(110, 25, 188, 28);
		panelSignUp.add(lblSignUpTitle);
		
		JLabel lblSignUpFirstname = new JLabel("First Name:");
		lblSignUpFirstname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpFirstname.setBounds(10, 64, 84, 14);
		panelSignUp.add(lblSignUpFirstname);
		
		JLabel lblSignUpLastName = new JLabel("Last Name:");
		lblSignUpLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpLastName.setBounds(20, 91, 73, 14);
		panelSignUp.add(lblSignUpLastName);
		
		JLabel lblSignUpUsername = new JLabel("Username:");
		lblSignUpUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpUsername.setBounds(246, 64, 73, 14);
		panelSignUp.add(lblSignUpUsername);
		
		JLabel lblSignUpPassword = new JLabel("Password:");
		lblSignUpPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpPassword.setBounds(256, 91, 63, 14);
		panelSignUp.add(lblSignUpPassword);
		
		textField_SignUpFirstName = new JTextField();
		textField_SignUpFirstName.setBounds(103, 64, 86, 20);
		panelSignUp.add(textField_SignUpFirstName);
		textField_SignUpFirstName.setColumns(10);
		
		textField_SignUpLastName = new JTextField();
		textField_SignUpLastName.setBounds(103, 88, 86, 20);
		panelSignUp.add(textField_SignUpLastName);
		textField_SignUpLastName.setColumns(10);
		
		textField_SignUpUsername = new JTextField();
		textField_SignUpUsername.setBounds(329, 61, 86, 20);
		panelSignUp.add(textField_SignUpUsername);
		textField_SignUpUsername.setColumns(10);
		
		textField_SignUpSecretQuestion = new JTextField();
		textField_SignUpSecretQuestion.setBounds(125, 181, 124, 20);
		panelSignUp.add(textField_SignUpSecretQuestion);
		textField_SignUpSecretQuestion.setColumns(10);
		
		JButton btnSignUpCreateNewUser = new JButton("Register"); //Register button
		btnSignUpCreateNewUser.addActionListener(new ActionListener() { //Action Listener for the Register button
			public void actionPerformed(ActionEvent e) {
				String firstName = textField_SignUpFirstName.getText(); //collect Strings from textFields
				String lastName = textField_SignUpLastName.getText();
				String name = firstName + " " + lastName;
				String userName = textField_SignUpUsername.getText();
				String PhoneNumber = textField_SignUp_PhoneNumber.getText();
				String secretQuestion = textField_SignUpSecretQuestion.getText();
				String secretAnswer = textField_SignUpSecretAnswer.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField_SignUpPassword.getText(); //collect password from password Fields
				@SuppressWarnings("deprecation")
				String passwordConfirmation = passwordField_SignUpConfirmPassword.getText();
				//check if passwords match
				if (!password.equals(passwordConfirmation)){ //check that both passwords fields match
					JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if password is at least 6 characters
				else if(password.length()<=5){ 
					JOptionPane.showMessageDialog(null, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if name is at least 3 characters long
				else if(name.length() <= 3){  
					JOptionPane.showMessageDialog(null, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);
				} //check that user name is at least 6 characters long
				else if(userName.length()<= 5){ 
					JOptionPane.showMessageDialog(null, "Username too short", "Error", JOptionPane.ERROR_MESSAGE);
				} //check that the user name is not taken already
				else if(checkUsername(userName)==true){
					JOptionPane.showMessageDialog(null, "Username already exists in the system!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{//create a new user and add it to the ArrayList
					Patient newUser = new Patient(name, userName, password, secretQuestion, secretAnswer, "", PhoneNumber);
					//check to see if there any existing Doctors in the system to be assigned this patient
					if (DoctorList.size()>0){ //if there are existing doctors a randomly assigned cared provider will be assigned to the patient
						int doctorIndex = randNumber(0,DoctorList.size()-1);
						String CareProvider = DoctorList.get(doctorIndex).getName();
						newUser.setCareProvider(CareProvider);
					}
					PatientList.add(newUser);
					JOptionPane.showMessageDialog(null, "User: " + userName +" was created!");
					System.out.println("New user: " + name + " was added!");
					//clear textFields
					textField_SignUpFirstName.setText("");
					textField_SignUpLastName.setText("");
					textField_SignUpUsername.setText("");
					textField_SignUp_PhoneNumber.setText("");
					textField_SignUpSecretQuestion.setText("");
					textField_SignUpSecretAnswer.setText("");
					passwordField_SignUpPassword.setText("");
					passwordField_SignUpConfirmPassword.setText("");
//*******************************************************************************************************************************
					for(int i = 0; i < DoctorList.size(); i++)
					{
						if(newUser.getCareProvider().equals(DoctorList.get(i).getName()))
							{
								DoctorList.get(i).addPatientName(name);
								System.out.print("Patient: "+newUser.getName());
								System.out.println(" was added to: " +DoctorList.get(i).getName());
								break;
							}
					}
				}
			}
		});
		btnSignUpCreateNewUser.setBounds(259, 180, 146, 23);
		panelSignUp.add(btnSignUpCreateNewUser);
		
		JButton btnSignUpGoBack = new JButton("Previous Screen");
		btnSignUpGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clear all of the textFields and passwordFields
				textField_SignUpFirstName.setText("");
				textField_SignUpLastName.setText("");
				textField_SignUpUsername.setText("");
				textField_SignUpSecretQuestion.setText("");
				textField_SignUp_PhoneNumber.setText("");
				passwordField_SignUpPassword.setText("");
				passwordField_SignUpConfirmPassword.setText("");
				textField_SignUpSecretAnswer.setText("");
				//change the visibility of the panels
				panelSignUp.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnSignUpGoBack.setBounds(259, 214, 146, 23);
		panelSignUp.add(btnSignUpGoBack); //add the previous screen button
		//text field for the Sign Up of a Doctor user
		textField_SignUp_PhoneNumber = new JTextField();
		textField_SignUp_PhoneNumber.setBounds(103, 119, 86, 20);
		panelSignUp.add(textField_SignUp_PhoneNumber);
		textField_SignUp_PhoneNumber.setColumns(10);
		
		JLabel lblSignUpPhoneNumber = new JLabel("Phone Number:");
		lblSignUpPhoneNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpPhoneNumber.setBounds(0, 116, 96, 14);
		panelSignUp.add(lblSignUpPhoneNumber);
		
		passwordField_SignUpPassword = new JPasswordField();
		passwordField_SignUpPassword.setBounds(329, 88, 86, 20);
		panelSignUp.add(passwordField_SignUpPassword);
		
		passwordField_SignUpConfirmPassword = new JPasswordField();
		passwordField_SignUpConfirmPassword.setBounds(329, 119, 86, 20);
		panelSignUp.add(passwordField_SignUpConfirmPassword);
		
		JLabel lblSignUpConfirmPassword = new JLabel("Confirm Password:");
		lblSignUpConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpConfirmPassword.setBounds(200, 122, 119, 14);
		panelSignUp.add(lblSignUpConfirmPassword);
		
		JLabel lblSignUpSecretQuestion = new JLabel("Secret Question:");
		lblSignUpSecretQuestion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpSecretQuestion.setBounds(10, 184, 105, 14);
		panelSignUp.add(lblSignUpSecretQuestion);
		
		textField_SignUpSecretAnswer = new JTextField();
		textField_SignUpSecretAnswer.setBounds(125, 215, 124, 20);
		panelSignUp.add(textField_SignUpSecretAnswer);
		textField_SignUpSecretAnswer.setColumns(10);
		
		JLabel lblSignUpSecretAnswer = new JLabel("Secret Answer:");
		lblSignUpSecretAnswer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpSecretAnswer.setBounds(10, 218, 105, 14);
		panelSignUp.add(lblSignUpSecretAnswer);
		
		JLabel lblNewLabel = new JLabel("Ex. (xxx) xxx-xxxx");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(81, 139, 131, 20);
		panelSignUp.add(lblNewLabel);
		//----------------------------------------------------------------------------------------------------
		
		//Forgotten Credentials Panel*************************************************************************
		panelForgottenCredentials = new JPanel();
		frmEsasSystem.getContentPane().add(panelForgottenCredentials, "name_136911632557425");
		panelForgottenCredentials.setLayout(null);
		//declaration of JButtons and JLabels for the panel Forgotten credentials
		JButton btnForgottenCredentialsGenerateQuestion;
		JButton btnForgottenCredentialsAnswer;
		JLabel lblRecoverPassword;
		JLabel lblForgottenCredentialsInstructions;
		JLabel lblForgottenCredentialsUsername;
		JButton btnForgottenCredentialsPreviousScreen;
		
		lblRecoverPassword = new JLabel("Recover Your Password");
		lblRecoverPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRecoverPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecoverPassword.setBounds(98, 23, 215, 35);
		panelForgottenCredentials.add(lblRecoverPassword);
		
		btnForgottenCredentialsGenerateQuestion = new JButton("Generate Secret Question");
		btnForgottenCredentialsGenerateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//check if user name exists in the Patient List
				int position = findPatient(textField_ForgottenCredentialsUsername.getText());
				if (position >= 0) { //if it is on the Patient List then retrieve the secret question
					Patient temp = new Patient();
					temp = PatientList.get(position);
					textField_ForgottenCredentialsSecretQuestion.setText(temp.getSecretQuestion());
				} else {
					//if user was not found on the Patient List, look for it on the Doctor List
					position = findDoctor(textField_ForgottenCredentialsUsername.getText());
					if (position >= 0){
						Doctor tempDoctor = new Doctor();
						tempDoctor = DoctorList.get(position);
						textField_ForgottenCredentialsSecretQuestion.setText(tempDoctor.getSecurityQ());
					} else { //display error message
						JOptionPane.showMessageDialog(null, "Username was not found!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnForgottenCredentialsGenerateQuestion.setBounds(58, 139, 280, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsGenerateQuestion);
		
		textField_ForgottenCredentialsSecretAnswer = new JTextField();
		textField_ForgottenCredentialsSecretAnswer.setBounds(236, 173, 102, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsSecretAnswer);
		textField_ForgottenCredentialsSecretAnswer.setColumns(10);
		
		btnForgottenCredentialsAnswer = new JButton("Check Answer");
		btnForgottenCredentialsAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if the answer provided matches the Secret Answer stored
				//get the position of the user in the ArrayList to get the information stored
				int position = findPatient(textField_ForgottenCredentialsUsername.getText());
				if (position == -1){ //find the user using the findPatient or findDoctor method
					position = findDoctor(textField_ForgottenCredentialsUsername.getText());
					if (position >= 0){ //retrieve the answer and use it to compare with the String from the Text Field
						String answer = textField_ForgottenCredentialsSecretAnswer.getText();
						Doctor tempDoctor = new Doctor();
						tempDoctor = DoctorList.get(position);
						//check if the answer matches what it is stored as the secret answer and provide the password
						if (answer.equals(tempDoctor.getSecurityA())){
							String password = tempDoctor.getPassword();
							JOptionPane.showMessageDialog(null, "Password is: "+password, "Password Recovery", JOptionPane.WARNING_MESSAGE);
						}
						else {//display an error if the answer doesn't match what was entered in the TextField
							JOptionPane.showMessageDialog(null, "Incorrect Answer!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else {
					String answer = textField_ForgottenCredentialsSecretAnswer.getText();
					Patient temp = new Patient();
					temp = PatientList.get(position); 	//check if the answer matches what it is stored as the secret answer and provide the password
					if (answer.equals(temp.getSecretAnswer())){
						String password = temp.getPassword();
						JOptionPane.showMessageDialog(null, "Password is: "+password, "Password Recovery", JOptionPane.WARNING_MESSAGE);
					}
					else {//display an error if the answer doesn't match what was entered in the TextField
						JOptionPane.showMessageDialog(null, "Incorrect Answer!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnForgottenCredentialsAnswer.setBounds(58, 204, 131, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsAnswer);
		
		lblForgottenCredentialsInstructions = new JLabel("Enter your username and click on the Generate Secret Question Button");
		lblForgottenCredentialsInstructions.setBounds(37, 83, 353, 14);
		panelForgottenCredentials.add(lblForgottenCredentialsInstructions);
		
		textField_ForgottenCredentialsUsername = new JTextField();
		textField_ForgottenCredentialsUsername.setBounds(179, 108, 134, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsUsername);
		textField_ForgottenCredentialsUsername.setColumns(10);
		
		lblForgottenCredentialsUsername = new JLabel("Username: ");
		lblForgottenCredentialsUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForgottenCredentialsUsername.setBounds(73, 108, 103, 14);
		panelForgottenCredentials.add(lblForgottenCredentialsUsername);
		
		btnForgottenCredentialsPreviousScreen = new JButton("Previous Screen");
		btnForgottenCredentialsPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clear textFields
				textField_ForgottenCredentialsUsername.setText("");
				textField_ForgottenCredentialsSecretAnswer.setText("");
				textField_ForgottenCredentialsSecretQuestion.setText("");
				//change the visibility of the panels to show the navigation between them
				panelForgottenCredentials.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnForgottenCredentialsPreviousScreen.setBounds(204, 204, 134, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsPreviousScreen);
		
		textField_ForgottenCredentialsSecretQuestion = new JTextField();
		textField_ForgottenCredentialsSecretQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		textField_ForgottenCredentialsSecretQuestion.setEditable(false);
		textField_ForgottenCredentialsSecretQuestion.setBounds(58, 173, 156, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsSecretQuestion);
		textField_ForgottenCredentialsSecretQuestion.setColumns(10);
		//----------------------------------------------------------------------------------------------------
		
		//MainMenu Panel *************************************************************************************
		panelMainMenu = new JPanel();
		frmEsasSystem.getContentPane().add(panelMainMenu, "name_136916106062093");
		panelMainMenu.setLayout(null);
		
		JLabel lblMainMenuWelcome = new JLabel("Welcome ");
		lblMainMenuWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMainMenuWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuWelcome.setBounds(129, 31, 179, 22);
		panelMainMenu.add(lblMainMenuWelcome);
		//The View History Button checks if the user has surveys or not before going to 
		//the View History Panel
		btnMainMenuViewHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenu.setVisible(false);  //hide Main Menu panel
				panelViewHistory.setVisible(true); //show the View History panel
				if (currentPatient.surveyIsEmpty()==true){ //display a message if there are no surveys to view for the current patient
					JOptionPane.showMessageDialog(null, "There are no surveys to view!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else { //create a JList object that will hold the list of surveys for the current patient
					survey_list = new JList<String>(currentPatient.getList());
					survey_list.setBorder(new LineBorder(new Color(0, 0, 0)));
					//add the JList inside of a JScrollpane so it will be able to scroll 
					scroll_survey_list = new JScrollPane(survey_list);
					scroll_survey_list.setBounds(27, 77, 155, 94);
					panelViewHistory.add(scroll_survey_list);
					//add the TextArea that will display the contents of each individual survey
					textArea_ViewHistory_Surveys = new JTextArea();
					textArea_ViewHistory_Surveys.setBorder(new LineBorder(new Color(0, 0, 0)));
					textArea_ViewHistory_Surveys.setEditable(false);
					textArea_ViewHistory_Surveys.setBounds(211, 73, 155, 130);
					panelViewHistory.add(textArea_ViewHistory_Surveys);
					survey_list.addMouseListener(new MouseAdapter() { //mouse Listener for clicking on List
						@Override
						public void mouseClicked(MouseEvent e) { 
							int index = survey_list.locationToIndex(e.getPoint()); //get index where user clicked on
							Survey selectedSurvey = new Survey();	//make a new Survey to hold the information
							selectedSurvey = currentPatient.getSurvey(index); //retrieve the corresponding Survey
							String info = selectedSurvey.getValuesOnString(); //Obtain String with values
							textArea_ViewHistory_Surveys.setText(info); //display info on textArea
						}
					});
				}
			}
		});
		btnMainMenuViewHistory.setBounds(108, 93, 200, 23);
		panelMainMenu.add(btnMainMenuViewHistory);
		//JButton for accessing the Care Provider Information
		JButton btnMainMenuAccessCPInformation = new JButton("Access Care Provider Info");
		btnMainMenuAccessCPInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenu.setVisible(false);
				panelViewCareProviderInfo.setVisible(true);
				//add the text area that will display the information about the care provider
				JTextArea textArea = new JTextArea();
				textArea.setEditable(false);
				textArea.setBounds(75, 85, 250, 90);
				textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
				panelViewCareProviderInfo.add(textArea);
				//check to see if the current patient has an assigned care provider
				if (currentPatient.getCareProvider().equals("")){//displayed a message in case the patient doesn't have one assigned
					textArea.setText("You currently do not have a Care Provider assigned to you at this moment");
				}
				else{//if the patient has one assigned, retrieved the doctor and set the text area to display the corresponding information
					int index = findDoctorByName(currentPatient.getCareProvider());
					if (index >= 0){ //if the doctor was found used the index to retrieve its information
						textArea.setText("Position: "+index);
						Doctor tempDoctor = DoctorList.get(index); //retrieve the doctor and get the information into a string
						String info = String.format(" Name: \n %s \n Phone Number: %s \n Email: \n %s", tempDoctor.getName(), 
								tempDoctor.getPhone(), tempDoctor.getEmail());
						textArea.setText(info); //set the text area to display the string with the information
					}
					else { //if the care provider was not found in the DoctorList display a message
						textArea.setText("\n         Care Provider was not found in \n         the directory of Care Providers");
					}
					
				}
			}
		});
		btnMainMenuAccessCPInformation.setBounds(108, 127, 200, 23);
		panelMainMenu.add(btnMainMenuAccessCPInformation);
		
		//Button to Complete Survey from the Main Menu
		JButton btnMainMenuCompleteSurvey = new JButton("Complete Survey");
		btnMainMenuCompleteSurvey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //action listener for the Complete Survey Button
				//add the JSpinner object that represents the date for the Survey 
				spinnerCompleteSurveyDate.setModel(new SpinnerDateModel());
				spinnerCompleteSurveyDate.setEditor(new JSpinner.DateEditor(spinnerCompleteSurveyDate, "dd/MM/yyyy hh:mm a"));
				spinnerCompleteSurveyDate.setBounds(254, 123, 159, 20);
				//add to the panel
				panelCompleteSurvey.add(spinnerCompleteSurveyDate);
				//change the visibility of the panels
				panelMainMenu.setVisible(false);
				panelCompleteSurvey.setVisible(true);
			}
		});
		btnMainMenuCompleteSurvey.setBounds(108, 160, 200, 23);
		panelMainMenu.add(btnMainMenuCompleteSurvey);
		
		JLabel lblMainMenuSelectOption = new JLabel("Select of one the options below:");
		lblMainMenuSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuSelectOption.setBounds(108, 64, 200, 14);
		panelMainMenu.add(lblMainMenuSelectOption);
		
		JButton btnMainMenuPreviousScreen = new JButton("Previous Screen");
		btnMainMenuPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenu.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnMainMenuPreviousScreen.setBounds(108, 194, 200, 23);
		panelMainMenu.add(btnMainMenuPreviousScreen);
		//----------------------------------------------------------------------------------------------------
		
		//Complete Survey Panel*******************************************************************************
		panelCompleteSurvey = new JPanel();
		frmEsasSystem.getContentPane().add(panelCompleteSurvey, "name_136918142667310");
		panelCompleteSurvey.setLayout(null);
		
		JLabel lblCompleteSurvey = new JLabel("Complete Survey");
		lblCompleteSurvey.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCompleteSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompleteSurvey.setBounds(111, 11, 216, 30);
		panelCompleteSurvey.add(lblCompleteSurvey);
		
		JLabel lblCompleteSurveyPain = new JLabel("Pain");
		lblCompleteSurveyPain.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyPain.setBounds(79, 44, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyPain);
		
		JLabel lblCompleteSurveyBreath = new JLabel("Shortness \r\nof breath");
		lblCompleteSurveyBreath.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyBreath.setBounds(12, 147, 113, 14);
		panelCompleteSurvey.add(lblCompleteSurveyBreath);
		
		JLabel lblCompleteSurveyNausea = new JLabel("Nausea");
		lblCompleteSurveyNausea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyNausea.setBounds(79, 96, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyNausea);
		
		JLabel lblCompleteSurveyDepression = new JLabel("Depression");
		lblCompleteSurveyDepression.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyDepression.setBounds(58, 175, 67, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDepression);
		
		JLabel lblCompleteSurveyAnxiety = new JLabel("Anxiety");
		lblCompleteSurveyAnxiety.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyAnxiety.setBounds(79, 201, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyAnxiety);
		
		JLabel lblCompleteSurveyDrowsiness = new JLabel("Drowsiness");
		lblCompleteSurveyDrowsiness.setBounds(58, 71, 79, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDrowsiness);
		
		JLabel lblCompleteSurveyAppetite = new JLabel("Appetite");
		lblCompleteSurveyAppetite.setBounds(79, 122, 58, 14);
		panelCompleteSurvey.add(lblCompleteSurveyAppetite);
		
		JLabel lblCompleteSurveyWellbeing = new JLabel("Wellbeing");
		lblCompleteSurveyWellbeing.setBounds(70, 226, 67, 14);
		panelCompleteSurvey.add(lblCompleteSurveyWellbeing);
		
		JLabel lblCompleteSurveyDate = new JLabel("Date:");
		lblCompleteSurveyDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCompleteSurveyDate.setBounds(201, 126, 46, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDate);
		
		spinnerCompleteSurveyDate = new JSpinner();
		
		JSpinner spinnerCompleteSurveyBreath = new JSpinner();
		spinnerCompleteSurveyBreath.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyBreath.setBounds(140, 144, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyBreath);
		
		JSpinner spinnerCompleteSurveyNausea = new JSpinner();
		spinnerCompleteSurveyNausea.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyNausea.setBounds(140, 93, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyNausea);
		
		JSpinner spinnerCompleteSurveyDepression = new JSpinner();
		spinnerCompleteSurveyDepression.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyDepression.setBounds(140, 171, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyDepression);
		
		JSpinner spinnerCompleteSurveyAnxiety = new JSpinner();
		spinnerCompleteSurveyAnxiety.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyAnxiety.setBounds(140, 197, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyAnxiety);
		
		JSpinner spinnerCompleteSurveyPain = new JSpinner();
		spinnerCompleteSurveyPain.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyPain.setBounds(140, 41, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyPain);
		
		JSpinner spinnerCompleteSurveyDrowsiness = new JSpinner();
		spinnerCompleteSurveyDrowsiness.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyDrowsiness.setBounds(140, 67, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyDrowsiness);
		
		JSpinner spinnerCompleteSurveyAppetite = new JSpinner();
		spinnerCompleteSurveyAppetite.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyAppetite.setBounds(140, 119, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyAppetite);
		
		JSpinner spinnerCompleteSurveyWellbeing = new JSpinner();
		spinnerCompleteSurveyWellbeing.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerCompleteSurveyWellbeing.setBounds(140, 223, 40, 20);
		panelCompleteSurvey.add(spinnerCompleteSurveyWellbeing);
		
		JFormattedTextField tfpain = ((JSpinner.DefaultEditor)spinnerCompleteSurveyPain.getEditor()).getTextField();
		tfpain.setEditable(false);
		tfpain.setBackground(Color.white);
		
		JFormattedTextField tfdrowsiness = ((JSpinner.DefaultEditor)spinnerCompleteSurveyDrowsiness.getEditor()).getTextField();
		tfdrowsiness.setEditable(false);
		tfdrowsiness.setBackground(Color.white);

		JFormattedTextField tfnausea = ((JSpinner.DefaultEditor)spinnerCompleteSurveyNausea.getEditor()).getTextField();
		tfnausea.setEditable(false);
		tfnausea.setBackground(Color.white);
		
		JFormattedTextField tfappetite = ((JSpinner.DefaultEditor)spinnerCompleteSurveyAppetite.getEditor()).getTextField();
		tfappetite.setEditable(false);
		tfappetite.setBackground(Color.white);
		
		JFormattedTextField tfbreath = ((JSpinner.DefaultEditor)spinnerCompleteSurveyBreath.getEditor()).getTextField();
		tfbreath.setEditable(false);
		tfbreath.setBackground(Color.white);

		JFormattedTextField tfdepression = ((JSpinner.DefaultEditor)spinnerCompleteSurveyDepression.getEditor()).getTextField();
		tfdepression.setEditable(false);
		tfdepression.setBackground(Color.white);

		JFormattedTextField tfanxiety = ((JSpinner.DefaultEditor)spinnerCompleteSurveyAnxiety.getEditor()).getTextField();
		tfanxiety.setEditable(false);
		tfanxiety.setBackground(Color.white);

		JFormattedTextField tfwellbeing = ((JSpinner.DefaultEditor)spinnerCompleteSurveyWellbeing.getEditor()).getTextField();
		tfwellbeing.setEditable(false);
		tfwellbeing.setBackground(Color.white);
		
		JButton btnCompleteSurveySaveSurvey = new JButton("Save Survey");
		btnCompleteSurveySaveSurvey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//collect current values from JSpinner
				int pain = (int)spinnerCompleteSurveyPain.getValue();
				int drowsiness = (int)spinnerCompleteSurveyDrowsiness.getValue();
				int nausea = (int)spinnerCompleteSurveyNausea.getValue();
				int appetite = (int)spinnerCompleteSurveyAppetite.getValue();
				int shortnessOfBreath = (int)spinnerCompleteSurveyBreath.getValue();
				int depression = (int)spinnerCompleteSurveyDepression.getValue();
				int anxiety = (int)spinnerCompleteSurveyAnxiety.getValue();
				int wellbeing = (int)spinnerCompleteSurveyWellbeing.getValue();
				String date = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(spinnerCompleteSurveyDate.getValue());
				//add the survey to the Correct Patient
				Survey completedSurvey = new Survey(pain, drowsiness, nausea, appetite, shortnessOfBreath, depression, anxiety, wellbeing, date);
				//--------------completedSurvey.printAll();
				currentPatient.addSurvey(completedSurvey);
				//reset JSpinners to default values
				spinnerCompleteSurveyPain.setValue(1);
				spinnerCompleteSurveyDrowsiness.setValue(1);
				spinnerCompleteSurveyNausea.setValue(1);
				spinnerCompleteSurveyAppetite.setValue(1);
				spinnerCompleteSurveyBreath.setValue(1);
				spinnerCompleteSurveyDepression.setValue(1);
				spinnerCompleteSurveyAnxiety.setValue(1);
				spinnerCompleteSurveyWellbeing.setValue(1);
				JOptionPane.showMessageDialog(null, "Survey was saved!");
			}
		});
		btnCompleteSurveySaveSurvey.setBounds(254, 166, 134, 23);
		panelCompleteSurvey.add(btnCompleteSurveySaveSurvey);
		
		JButton btnCompleSurveyPreviousScreen = new JButton("Previous Screen");
		btnCompleSurveyPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCompleteSurvey.setVisible(false);
				panelMainMenu.setVisible(true);
			}
		});
		btnCompleSurveyPreviousScreen.setBounds(254, 205, 134, 23);
		panelCompleteSurvey.add(btnCompleSurveyPreviousScreen);
		//JLabel describing the usage of the JSpinner
		JLabel lblCompleteSurveyDescription1 = new JLabel("1 is lowest level of symptom possible");
		lblCompleteSurveyDescription1.setBounds(201, 63, 242, 30);
		panelCompleteSurvey.add(lblCompleteSurveyDescription1);
		//JLabel describing the usage of the JSpinner
		JLabel lblCompleteSurveyDescription2 = new JLabel("10 is highest level of symptom possible");
		lblCompleteSurveyDescription2.setHorizontalAlignment(SwingConstants.LEFT);
		lblCompleteSurveyDescription2.setBounds(201, 96, 242, 14);
		panelCompleteSurvey.add(lblCompleteSurveyDescription2);

		//----------------------------------------------------------------------------------------------------
		
		//View CareProvider Info Panel************************************************************************
		panelViewCareProviderInfo = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewCareProviderInfo, "name_136919613899853");
		panelViewCareProviderInfo.setLayout(null);
		
		JLabel lblViewCPInfoTitle = new JLabel("Care Provider Information");
		lblViewCPInfoTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewCPInfoTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewCPInfoTitle.setBounds(85, 39, 252, 22);
		panelViewCareProviderInfo.add(lblViewCPInfoTitle);
		
		JButton btnCareProviderInfoPreviousScreen = new JButton("Previous Screen");
		btnCareProviderInfoPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//change the visibility of the panels
				panelViewCareProviderInfo.setVisible(false);
				panelMainMenu.setVisible(true);
			}
		});
		btnCareProviderInfoPreviousScreen.setBounds(128, 189, 158, 23);
		panelViewCareProviderInfo.add(btnCareProviderInfoPreviousScreen);
	
		//----------------------------------------------------------------------------------------------------
		
		//View History Panel**********************************************************************************
		panelViewHistory = new JPanel();
		panelViewHistory.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmEsasSystem.getContentPane().add(panelViewHistory, "name_136921281711804");
		panelViewHistory.setLayout(null);
		
		JLabel lblViewHistoryTitle = new JLabel("View History");
		lblViewHistoryTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewHistoryTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewHistoryTitle.setBounds(125, 41, 175, 22);
		panelViewHistory.add(lblViewHistoryTitle);
		
		//Previous Screen Button for Main Menu 
		JButton btnViewHistoryPreviousScreen = new JButton("Previous Screen");
		btnViewHistoryPreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//change the visibility of the panels
				panelViewHistory.setVisible(false);
				panelMainMenu.setVisible(true);
				//if there are no surveys for the current patient then the text area and the JScroll pane are removed
				if (currentPatient.surveyIsEmpty()==false){
					panelViewHistory.remove(textArea_ViewHistory_Surveys);
					panelViewHistory.remove(scroll_survey_list);
				}
			}
		});

		btnViewHistoryPreviousScreen.setBounds(136, 215, 164, 23);
		btnViewHistoryPreviousScreen.setBounds(136, 213, 164, 23);
		panelViewHistory.add(btnViewHistoryPreviousScreen);
		
		JLabel lbl_View_History_Survey = new JLabel("Surveys:");
		lbl_View_History_Survey.setBounds(28, 63, 66, 14);
		panelViewHistory.add(lbl_View_History_Survey);
//--------------------------------------------------------------------------------------------------------------		
		
//Doctor Sign Up Panel *****************************************************************************************		
		panelDoctorSignUp = new JPanel();
		frmEsasSystem.getContentPane().add(panelDoctorSignUp, "name_27245650935221");
		panelDoctorSignUp.setLayout(null);
		
		JLabel lblCreateANew = new JLabel("Create A New User");
		lblCreateANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateANew.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCreateANew.setBounds(112, 26, 211, 14);
		panelDoctorSignUp.add(lblCreateANew);
		
		textField_DoctorSignUp_FirstName = new JTextField();
		textField_DoctorSignUp_FirstName.setBounds(81, 51, 95, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_FirstName);
		textField_DoctorSignUp_FirstName.setColumns(10);
		
		textField_DoctorSignUp_LastName = new JTextField();
		textField_DoctorSignUp_LastName.setBounds(81, 82, 95, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_LastName);
		textField_DoctorSignUp_LastName.setColumns(10);
		
		textField_DoctorSignUp_Username = new JTextField();
		textField_DoctorSignUp_Username.setBounds(317, 51, 95, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_Username);
		textField_DoctorSignUp_Username.setColumns(10);
		
		textField_DoctorSignUp_SecurityQuestion = new JTextField();
		textField_DoctorSignUp_SecurityQuestion.setBounds(127, 177, 128, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_SecurityQuestion);
		textField_DoctorSignUp_SecurityQuestion.setColumns(10);
		
		textField_DoctorSignUp_SecurityAnswer = new JTextField();
		textField_DoctorSignUp_SecurityAnswer.setBounds(127, 208, 128, 20);
		panelDoctorSignUp.add(textField_DoctorSignUp_SecurityAnswer);
		textField_DoctorSignUp_SecurityAnswer.setColumns(10);
		
		JLabel lbl_DoctorSignUp_FirstName = new JLabel("First Name:");
		lbl_DoctorSignUp_FirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_FirstName.setBounds(-15, 54, 86, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_FirstName);
		
		JLabel lbl_DoctorSignUp_LastName = new JLabel("Last Name:");
		lbl_DoctorSignUp_LastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_LastName.setBounds(-5, 85, 76, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_LastName);
		
		JLabel lbl_DoctorSignUp_Username = new JLabel("Username:");
		lbl_DoctorSignUp_Username.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_Username.setBounds(241, 54, 66, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_Username);
		
		passwordField_DoctorSignUp_Password = new JPasswordField();
		passwordField_DoctorSignUp_Password.setBounds(317, 82, 95, 20);
		panelDoctorSignUp.add(passwordField_DoctorSignUp_Password);
		
		passwordField_DoctorSignUp_ConfirmPassword = new JPasswordField();
		passwordField_DoctorSignUp_ConfirmPassword.setBounds(316, 113, 96, 20);
		panelDoctorSignUp.add(passwordField_DoctorSignUp_ConfirmPassword);
		
		JLabel lbl_DoctorSignUp_Password = new JLabel("Password:");
		lbl_DoctorSignUp_Password.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_Password.setBounds(244, 85, 63, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_Password);
		
		JLabel lbl_DoctorSignUp_ConfirmPassword = new JLabel("Confirm Password:");
		lbl_DoctorSignUp_ConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_ConfirmPassword.setBounds(179, 113, 128, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_ConfirmPassword);
		
		JLabel lbl_DoctorSignUp_SecurityQuestion = new JLabel("Security Question:");
		lbl_DoctorSignUp_SecurityQuestion.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_SecurityQuestion.setBounds(10, 180, 112, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_SecurityQuestion);
		
		JLabel lbl_DoctorSignUp_SecurityAnswer = new JLabel("Security Answer:");
		lbl_DoctorSignUp_SecurityAnswer.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_SecurityAnswer.setBounds(23, 211, 99, 14);
		panelDoctorSignUp.add(lbl_DoctorSignUp_SecurityAnswer);
		
		JButton btn_DoctorSignUp_Register = new JButton("Register");
		btn_DoctorSignUp_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = textField_DoctorSignUp_FirstName.getText();
				String lastName = textField_DoctorSignUp_LastName.getText();
				String name = firstName + " " + lastName;
				String userName = textField_DoctorSignUp_Username.getText();
				String phone = textField_DoctorPhone.getText();
				String email = textField_DoctorEmail.getText();
				String secretQuestion = textField_DoctorSignUp_SecurityQuestion.getText();
				String secretAnswer = textField_DoctorSignUp_SecurityAnswer.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField_DoctorSignUp_Password.getText();
				@SuppressWarnings("deprecation")
				String passwordConfirmation = passwordField_DoctorSignUp_ConfirmPassword.getText();
				//check if passwords match
				if (!password.equals(passwordConfirmation)){
					JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if password is at least 6 characters
				else if(password.length()<=5){
					JOptionPane.showMessageDialog(null, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
				} //check if name is at least 3 characters long
				else if(name.length() <= 3){
					JOptionPane.showMessageDialog(null, "Invalid name", "Error", JOptionPane.ERROR_MESSAGE);
				} //check that user name is at least 6 characters long
				else if(userName.length()<= 5){
					JOptionPane.showMessageDialog(null, "Username too short", "Error", JOptionPane.ERROR_MESSAGE);
				}//check that the user name is not taken already
				else if(checkUsername(userName)==true){
					JOptionPane.showMessageDialog(null, "Username already exists in the system!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{//create a new user and add it to the ArrayList
					Doctor newUser = new Doctor(name, userName, password, secretQuestion, secretAnswer, phone, email);
					//check to see if there any Patients without an assigned Doctor and add them to the new Doctor user
					for(int i=0;i<PatientList.size();i++){
						if (PatientList.get(i).getCareProvider().equals("")){ //check to see if there's no Care Provider assigned to the Patient
							PatientList.get(i).setCareProvider(newUser.getName());//add the Care Provider's name to the Patient
							newUser.addPatientName(PatientList.get(i).getName());//add the Patient Name to the Care Provider's list of patients
						}
					}
					DoctorList.add(newUser); //add the new Doctor User to the DoctorList
					JOptionPane.showMessageDialog(null, "User: " + userName +" was created!");
					System.out.println("New Doctor user: " + name + " was added!");
					//clear textFields
					textField_DoctorSignUp_FirstName.setText("");
					textField_DoctorSignUp_LastName.setText("");
					textField_DoctorSignUp_Username.setText("");
					textField_DoctorSignUp_SecurityQuestion.setText("");
					textField_DoctorSignUp_SecurityAnswer.setText("");
					textField_DoctorEmail.setText("");
					textField_DoctorPhone.setText("");
					passwordField_DoctorSignUp_Password.setText("");
					passwordField_DoctorSignUp_ConfirmPassword.setText("");
				}
			}
		});
		btn_DoctorSignUp_Register.setBounds(277, 176, 135, 23);
		panelDoctorSignUp.add(btn_DoctorSignUp_Register);
		
		JButton btn_DoctorSignUp_PreviousScreen = new JButton("Previous Screen");//Previous Screen Button
		btn_DoctorSignUp_PreviousScreen.addActionListener(new ActionListener() { //Action Listener for the Previous Screen button
			public void actionPerformed(ActionEvent e) {
				//clear all the text fields
				textField_DoctorSignUp_FirstName.setText("");
				textField_DoctorSignUp_LastName.setText("");
				textField_DoctorSignUp_Username.setText("");
				textField_DoctorSignUp_SecurityQuestion.setText("");
				textField_DoctorSignUp_SecurityAnswer.setText("");
				passwordField_DoctorSignUp_Password.setText("");
				passwordField_DoctorSignUp_ConfirmPassword.setText("");
				//change the visibility of the panels
				panelDoctorSignUp.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btn_DoctorSignUp_PreviousScreen.setBounds(277, 208, 135, 25);
		panelDoctorSignUp.add(btn_DoctorSignUp_PreviousScreen);
		
		textField_DoctorPhone = new JTextField();
		textField_DoctorPhone.setBounds(81, 146, 95, 20);
		panelDoctorSignUp.add(textField_DoctorPhone);
		textField_DoctorPhone.setColumns(10);
		
		textField_DoctorEmail = new JTextField();
		textField_DoctorEmail.setBounds(81, 113, 95, 20);
		panelDoctorSignUp.add(textField_DoctorEmail);
		textField_DoctorEmail.setColumns(10);
		
		JLabel lbl_DoctorSignUp_Phone = new JLabel("Phone:");
		lbl_DoctorSignUp_Phone.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_Phone.setBounds(10, 143, 61, 23);
		panelDoctorSignUp.add(lbl_DoctorSignUp_Phone);
		
		JLabel lbl_DoctorSignUp_Email = new JLabel("Email:");
		lbl_DoctorSignUp_Email.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DoctorSignUp_Email.setBounds(5, 113, 65, 20);
		panelDoctorSignUp.add(lbl_DoctorSignUp_Email);
		
		JLabel lblExxxxXxxxxxx = new JLabel("Ex. (xxx)xxx-xxxx");
		lblExxxxXxxxxxx.setBounds(189, 143, 128, 23);
		panelDoctorSignUp.add(lblExxxxXxxxxxx);
		
		//-----------------------------------------------------------------------------------------------------
		
		// Main Menu Doctor panel *****************************************************************************
		panelMainMenuDoctor = new JPanel();
		frmEsasSystem.getContentPane().add(panelMainMenuDoctor, "name_41199396829377");
		panelMainMenuDoctor.setLayout(null);
		
		JLabel lbl_MainMenuDoctor_Welcome = new JLabel("Welcome");
		lbl_MainMenuDoctor_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_MainMenuDoctor_Welcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_MainMenuDoctor_Welcome.setBounds(108, 25, 200, 33);
		panelMainMenuDoctor.add(lbl_MainMenuDoctor_Welcome);
		
		JButton btn_MainMenuDoctor_ViewPatientHistory = new JButton("View Patient History");
		btn_MainMenuDoctor_ViewPatientHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenuDoctor.setVisible(false);
				panelViewHistoryDoctor.setVisible(true);
				if (currentDoctor.isPatientListEmpty()==true){
					JOptionPane.showMessageDialog(null, "There are no patients to view!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//make and add the JList containing the patient's name associated with the doctor
					patient_list = new JList<String>(currentDoctor.getList()); //make the JList containing the list of patients
					patient_list.setBorder(new LineBorder(new Color(0, 0, 0))); //add a border to the JList object
					scroll_patient_list = new JScrollPane(patient_list); //make a scroll pane for the JList object
					scroll_patient_list.setBounds(10, 67, 125, 135); //set the bounds of the of scroll pane object
					panelViewHistoryDoctor.add(scroll_patient_list); //add the patient_list to the scroll pane
					
					//Add the second JList object to this panel
					survey_list_doctor = new JList<String>();
					survey_list_doctor.setBorder(new LineBorder(new Color(0, 0, 0)));
					scroll_survey_list_doctor = new JScrollPane(survey_list_doctor);
					scroll_survey_list_doctor.setBounds(142, 67, 125, 135);
					panelViewHistoryDoctor.add(scroll_survey_list_doctor);
					
					//add the TextArea
					textArea_ViewPatient_Names = new JTextArea();
					textArea_ViewPatient_Names.setBorder(new LineBorder(new Color(0, 0, 0)));
					textArea_ViewPatient_Names.setEditable(false);
					textArea_ViewPatient_Names.setBounds(275, 67, 150, 135);
					panelViewHistoryDoctor.add(textArea_ViewPatient_Names);
					
					patient_list.addMouseListener(new MouseAdapter() { //mouse Listener	
					@Override
					public void mouseClicked(MouseEvent e){
						//use the click action to select and retrieve the surveys from a single patient user
						int index2 = patient_list.locationToIndex(e.getPoint()); //get index where user clicked on
						String patientName = currentDoctor.getPatientName(index2); //put the corresponding patient name in a String
						int position = findPatientByName(patientName); //find the position of the corresponding Patient in the PatientList
						Patient tempPatient = PatientList.get(position); //retrieve the corresponding Patient into tempPatient
						survey_list_doctor.setModel(tempPatient.getList());
						
						//add the action listener that will retrieve the surveys for a specific user
						survey_list_doctor.addMouseListener(new MouseAdapter() { //mouse Listener for clicking on List
							@Override
							public void mouseClicked(MouseEvent e) { 
								int index = survey_list_doctor.locationToIndex(e.getPoint()); //get index where user clicked on
								Survey selectedSurvey = new Survey();	//make a new Survey to hold the information
								selectedSurvey = tempPatient.getSurvey(index); //retrieve the corresponding Survey
								String info = selectedSurvey.getValuesOnString(); //Obtain String with values
								textArea_ViewPatient_Names.setText(info); //display info on textArea
							}
						});
					}
					});//end of patient_list MouseListener
				}//end of the else
				
			}
		});
		
		btn_MainMenuDoctor_ViewPatientHistory.setBounds(108, 79, 200, 33);
		panelMainMenuDoctor.add(btn_MainMenuDoctor_ViewPatientHistory);
		//JButton for viewing the patient contact information 
		JButton btn_MainMenuDoctor_ViewPatientInfo = new JButton("View Patient Contact Info");
		btn_MainMenuDoctor_ViewPatientInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenuDoctor.setVisible(false);
				panelViewPatientInfo.setVisible(true);
				if (currentDoctor.isPatientListEmpty()==true){
					JOptionPane.showMessageDialog(null, "There are no patients to view!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//make and add the JList containing the patient's name associated with the doctor
					patient_list_view_patient_info = new JList<String>(currentDoctor.getList()); //make the JList containing the list of patients
					patient_list_view_patient_info.setBorder(new LineBorder(new Color(0, 0, 0))); //add a border to the JList object
					scroll_patient_list_view_patient_info = new JScrollPane(patient_list_view_patient_info); //make a scroll pane for the JList object
					scroll_patient_list_view_patient_info.setBounds(60, 67, 125, 125); //set the bounds of the of scroll pane object
					panelViewPatientInfo.add(scroll_patient_list_view_patient_info); //add the patient_list to the scroll pane
					
					//add the TextArea
					textArea_ViewPatient_Info = new JTextArea();
					textArea_ViewPatient_Info.setBorder(new LineBorder(new Color(0, 0, 0)));
					textArea_ViewPatient_Info.setEditable(false);
					textArea_ViewPatient_Info.setBounds(200, 67, 150, 125);
					panelViewPatientInfo.add(textArea_ViewPatient_Info);
					
					patient_list_view_patient_info.addMouseListener(new MouseAdapter() { //mouse Listener	
					@Override
					public void mouseClicked(MouseEvent e){
						//use the click action to select and retrieve the surveys from a single patient user
						int index = patient_list_view_patient_info.locationToIndex(e.getPoint()); //get index where user clicked on
						String patientName = currentDoctor.getPatientName(index); //put the corresponding patient name in a String
						int position = findPatientByName(patientName); //find the position of the corresponding Patient in the PatientList
						Patient tempPatient = PatientList.get(position); //retrieve the corresponding Patient into tempPatient
						String info = String.format("Patient Name: \n%s \nPatient Phone Number: \n%s", tempPatient.getName(), tempPatient.getPhoneNumber());
						textArea_ViewPatient_Info.setText(info);
					}
					});//end of patient_list MouseListener
				}//end of the else
			}
		});
		btn_MainMenuDoctor_ViewPatientInfo.setBounds(108, 123, 200, 33);
		panelMainMenuDoctor.add(btn_MainMenuDoctor_ViewPatientInfo);
		JButton btn_View = new JButton("Previous Screen");
		btn_View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMainMenuDoctor.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btn_View.setBounds(108, 167, 200, 33);
		panelMainMenuDoctor.add(btn_View);
		//-------------------------------------------------------------------------------------------------------------------
		
		//View History Doctor Panel************************************************************************************************
		panelViewHistoryDoctor = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewHistoryDoctor, "name_44443382672325");
		panelViewHistoryDoctor.setLayout(null);
		
		JLabel lbl_ViewPatientHistory_title = new JLabel("View Patient History");
		lbl_ViewPatientHistory_title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_ViewPatientHistory_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ViewPatientHistory_title.setBounds(104, 11, 207, 28);
		panelViewHistoryDoctor.add(lbl_ViewPatientHistory_title);
		

		
		JLabel lbl_ViewHistoryDoctor_Patients = new JLabel("Patients:");
		lbl_ViewHistoryDoctor_Patients.setBounds(10, 45, 68, 14);
		panelViewHistoryDoctor.add(lbl_ViewHistoryDoctor_Patients);
		
		JLabel lbl_ViewHistoryDoctor_Surveys = new JLabel("Surveys:");
		lbl_ViewHistoryDoctor_Surveys.setBounds(143, 45, 68, 14);
		panelViewHistoryDoctor.add(lbl_ViewHistoryDoctor_Surveys);
		
		JButton btn_ViewHistoryDoctor_PreviousScreen = new JButton("Previous Screen");
		btn_ViewHistoryDoctor_PreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewHistoryDoctor.setVisible(false);
				panelMainMenuDoctor.setVisible(true);
			}
		});
		btn_ViewHistoryDoctor_PreviousScreen.setBounds(132, 223, 145, 28);
		panelViewHistoryDoctor.add(btn_ViewHistoryDoctor_PreviousScreen);
		
		JLabel lbl_ViewHistoryDoctor_Contents = new JLabel("Contents:");
		lbl_ViewHistoryDoctor_Contents.setBounds(278, 45, 90, 14);
		panelViewHistoryDoctor.add(lbl_ViewHistoryDoctor_Contents);
		//--------------------------------------------------------------------------------------------------------------------
		
		//View Patient Info as a Doctor panel************************************************************************************
		panelViewPatientInfo = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewPatientInfo, "name_44471001791997");
		panelViewPatientInfo.setLayout(null);
		
		JLabel lblViewPatientInfo = new JLabel("View Patient Info");
		lblViewPatientInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewPatientInfo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblViewPatientInfo.setBounds(111, 11, 201, 30);
		panelViewPatientInfo.add(lblViewPatientInfo);
		
		JButton btn_ViewPatientInfo_PreviousScreen = new JButton("Previous Screen");
		btn_ViewPatientInfo_PreviousScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewPatientInfo.setVisible(false);
				panelMainMenuDoctor.setVisible(true);
			}
		});
		btn_ViewPatientInfo_PreviousScreen.setBounds(149, 210, 136, 30);
		panelViewPatientInfo.add(btn_ViewPatientInfo_PreviousScreen);
		
		JLabel lbl_ViewPatientInfo_Patients = new JLabel("Patients:");
		lbl_ViewPatientInfo_Patients.setBounds(60, 45, 68, 14);
		panelViewPatientInfo.add(lbl_ViewPatientInfo_Patients);
		
		//----------------------------------------------------------------------------------------------------
	}
	
	//method to find user in either the PatientList or DoctorList 
	public int findPatient(String pUsername){
		//check if user name exists
		boolean found = false;
		int length = PatientList.size(); //get the size of ArrayList
		Patient tempPatient = new Patient();   //make a temporary Patient
		int position = -1;
		int i = 0;                     //counter
		while ((found==false)&&(i<length)){ //go through ArrayList looking for match on user name
			tempPatient = PatientList.get(i);

			if (pUsername.equalsIgnoreCase(tempPatient.getUsername())){ //check if user name matches
				found = true;  // if it is found change condition and save the index
				position = i; //save the current position 
			}
			i++;  //increment counter
		}		
		if (found==true)
			return position;
		else
			return -1;
	}

//method that finds the index of a doctor in the DoctorList based on the user name
	public int findDoctor(String pUsername){
		//check if user name exists
		boolean found = false;
		int length = DoctorList.size(); //get the size of ArrayList
		Doctor tempDoctor = new Doctor();   //make a temporary Patient
		int position = -1;
		int i = 0;                     //counter
		while ((found==false)&&(i<length)){ //go through ArrayList looking for match on user name
			tempDoctor = DoctorList.get(i);
			if (pUsername.equalsIgnoreCase(tempDoctor.getUsername())){ //check if user name matches
				found = true;  // if it is found change condition and save the index
				position = i; //save the current position 
			}
			i++;  //increment counter
		}		
		if (found == true)
			return position;
		else
			return -1;
	}

	
//method that finds the index of a doctor in the DoctorList based on the Name
	public int findDoctorByName(String pName){
		//check if user name exists
		boolean found = false;
		int length = DoctorList.size(); //get the size of ArrayList
		Doctor tempDoctor = new Doctor();   //make a temporary Patient
		int position = -1;
		int i = 0;                     //counter
		while ((found==false)&&(i<length)){ //go through ArrayList looking for match on user name
			tempDoctor = DoctorList.get(i);
			if (pName.equalsIgnoreCase(tempDoctor.getName())){ //check if user name matches
				found = true;  // if it is found change condition and save the index
				position = i; //save the current position 
			}
			i++;  //increment counter
		}		
		if (found == true)
			return position;
		else
			return -1;
	}
	
	public int findPatientByName(String pName){
		//check if the name exists in the PatientList
		boolean found = false;
		int length = PatientList.size();
		Patient temp = new Patient();
		int position = -1; 
		int i = 0;
		while ((found==false)&&(i<length)){
			temp = PatientList.get(i);
			if (pName.equalsIgnoreCase(temp.getName())){
				found = true;
				position = i;
			}
			i++;
		}
		if (found == true)
			return position;
		else 
			return -1;
	}
	//method that looks through the DoctorList for a given Name
	public boolean doctorExists(String pName){
		for (int i=0; i < DoctorList.size(); i++){
			if (pName.equals(DoctorList.get(i).getName())){
				return true;
			}
		}
		return false;
	}
	//method to check if a username already exists in either the PatientList or the DoctorList
	public boolean checkUsername(String pUsername){
		boolean found = false;
		for (int i=0;i<PatientList.size();i++){
			if (pUsername.equalsIgnoreCase(PatientList.get(i).getUsername()))
				return true;
		}
		for (int i=0;i<DoctorList.size();i++){
			if (pUsername.equalsIgnoreCase(DoctorList.get(i).getUsername()))
				return true;
		}
		return found;
	}
	
	private void savePatientToFile() throws FileNotFoundException{
		PrintWriter pw = new PrintWriter (new FileOutputStream("patients.txt"));
		pw.println(PatientList.size());
		for (int i=0;i<PatientList.size();i++){
			pw.println(PatientList.get(i).getName());
			pw.println(PatientList.get(i).getUsername());
			pw.println(PatientList.get(i).getPassword());
			pw.println(PatientList.get(i).getSecretQuestion());
			pw.println(PatientList.get(i).getSecretAnswer());
			pw.println(PatientList.get(i).getCareProvider());
			pw.println(PatientList.get(i).getPhoneNumber());
			Patient tempPatient = PatientList.get(i);
			pw.println(tempPatient.surveyCount());
			for (int j=0; j<tempPatient.surveyCount();j++){
				Survey temp = tempPatient.getSurvey(j);
				pw.print(temp.getPain()+" ");
				pw.print(temp.getDrowsiness()+" ");
				pw.print(temp.getNausea()+" ");
				pw.print(temp.getAppetite()+" ");
				pw.print(temp.getShortnessOfBreath()+" ");
				pw.print(temp.getDepression()+" ");
				pw.print(temp.getAnxiety()+" ");
				pw.print(temp.getWellbeing()+" ");
				pw.println(temp.getDate()+" ");
			}
		}
		pw.close();
	}
	
	private void saveDoctorToFile() throws FileNotFoundException{
		PrintWriter pw = new PrintWriter (new FileOutputStream("doctors.txt"));
		pw.println(DoctorList.size());
		for (int i=0;i<DoctorList.size();i++){
			pw.println(DoctorList.get(i).getName());
			pw.println(DoctorList.get(i).getUsername());
			pw.println(DoctorList.get(i).getPassword());
			pw.println(DoctorList.get(i).getSecurityQ());
			pw.println(DoctorList.get(i).getSecurityA());
			pw.println(DoctorList.get(i).getPhone());
			pw.println(DoctorList.get(i).getEmail());
			Doctor tempDoctor = DoctorList.get(i);
			pw.println(tempDoctor.getPatientCount());
			for (int j=0; j<tempDoctor.getPatientCount();j++){
				pw.println(tempDoctor.getPatientName(j));
			}
		}
		pw.close();
	}
	
	public void loadPatients() throws FileNotFoundException{
		File file = new File("patients.txt");
		if(file.exists()){
			Scanner sc = new Scanner(file);
			int patientCount = sc.nextInt();
			String Dummy = sc.nextLine();
			for (int i=0;i<patientCount;i++){
				String Name = sc.nextLine();
				String Username = sc.nextLine();
				String Password = sc.nextLine();
				String SecretQuestion = sc.nextLine();
				String SecretAnswer = sc.nextLine();
				String CareProvider = sc.nextLine();
				String PhoneNumber = sc.nextLine();
				Patient newPatient = new Patient(Name, Username, Password, SecretQuestion, SecretAnswer, CareProvider, PhoneNumber);
				PatientList.add(newPatient);
				int surveyCount = sc.nextInt();
				Dummy = sc.nextLine();
				for (int j=0; j<surveyCount; j++){
					int Pain = sc.nextInt();
					int Drowsiness = sc.nextInt();
					int Nausea = sc.nextInt();
					int Appetite = sc.nextInt();
					int Shortness = sc.nextInt();
					int Depression = sc.nextInt();
					int Anxiety = sc.nextInt();
					int WellBeing = sc.nextInt();
					String Date = sc.nextLine();
					Survey newSurvey = new Survey(Pain, Drowsiness, Nausea, Appetite, Shortness, Depression, Anxiety, WellBeing, Date);
					newPatient.addSurvey(newSurvey);
				}
			}
		
		sc.close();
		} else {
			System.out.println("File was not found");
		}
	}
	
	public void loadDoctors() throws FileNotFoundException{
		File file = new File("doctors.txt");
		if(file.exists()){
			Scanner sc = new Scanner(file);
			int doctorCount = sc.nextInt();
			String Dummy = sc.nextLine();
			for (int i=0;i<doctorCount;i++){
				String Name = sc.nextLine();
				String Username = sc.nextLine();
				String Password = sc.nextLine();
				String SecretQuestion = sc.nextLine();
				String SecretAnswer = sc.nextLine();
				String PhoneNumber = sc.nextLine();
				String Email = sc.nextLine();
				Doctor newDoctor = new Doctor(Name, Username, Password, SecretQuestion, SecretAnswer, PhoneNumber, Email);
				DoctorList.add(newDoctor);
				int patientCount = sc.nextInt();
				Dummy = sc.nextLine();
				for (int j=0; j<patientCount; j++){
					String patientName = sc.nextLine();
					newDoctor.addPatientName(patientName);
				}
			}
		
		sc.close();
		} else {
			System.out.println("File was not found");
		}
	}
	
	//method to generate a random number
	public static int randNumber(int min, int max){
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		return randomNumber;
	}

	//For Testing Purposes*********************************************************************************************
	/*
	public boolean addPatient(String pName, String pUsername, String pPassword, String pSecretQuestion, String pSecretAnswer,
			String pCareProvider) {
				try{
					Patient newPatient = new Patient(pName, pUsername, pPassword, pSecretQuestion, pSecretAnswer, pCareProvider);
					PatientList.add(newPatient);
					return true;
				}
				catch(Exception e) {
					System.out.println(e);
				}
				return false;
			}
	public boolean addDoctor(String name1, String username1, String password1, String securityQ1, String securityA1, String phone1, String email1) {
		try{
			Doctor newDoctor = new Doctor(name1, username1, password1, securityQ1, securityA1, phone1, email1);
			DoctorList.add(newDoctor);	
			return true;
			}
			catch(Exception e) {
				System.out.println(e);
			}
		return false;
	}
	/*
	public void addSurvey() {
		Survey newSurvey = new Survey(1, 2, 3, 4, 5, 6, 7, 8, "Today");
		currentPatient.addSurvey(newSurvey);
	}*/
}

