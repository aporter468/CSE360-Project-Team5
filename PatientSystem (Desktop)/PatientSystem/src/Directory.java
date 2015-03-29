import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Directory {
    //declaration of private variables
	private JFrame frmEsasSystem;
	private JPanel panelLogin; 
	private JPanel panelSignUp;
	private JPanel panelForgottenCredentials;
	private JPanel panelMainMenu;
	private JPanel panelCompleteSurvey;
	private JPanel panelViewCareProviderInfo;
	private JPanel panelViewHistoryPanel;
	
	//textField and passwordField from Login Panel
	private JTextField textField_LoginUsername;
	private JPasswordField passwordField_Login;
	
	//textFields and PasswordField from Sign Up Panel
	private JTextField textField_SignUpFirstName;
	private JTextField textField_SignUpLastName;
	private JTextField textField_SignUpUsername;
	private JTextField textField_SignUpSecretQuestion;
	private JTextField textField_SignUpCareProvider;
	private JPasswordField passwordField_SignUpPassword;
	private JPasswordField passwordField_SignUpConfirmPassword;
	private JTextField textField_SecretAnswer;

	//textFields from panelForgottenCredentials
	private JTextField textField_ForgottenCredentialsUsername;
	private JTextField textField_ForgottenCredentialsSecretAnswer;
	
	//main method
	public static void main(String[] args) {
		ArrayList<Patient> PatientList = new ArrayList<Patient>();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Directory window = new Directory();
					window.frmEsasSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//constructor method
	public Directory() {
		initialize();
	}

	//initialize contents of frame
	private void initialize() {
		frmEsasSystem = new JFrame();
		frmEsasSystem.setTitle("ESAS System");
		frmEsasSystem.setBounds(100, 100, 450, 300);
		frmEsasSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Set Exit_On_close behavior when window is closed
		frmEsasSystem.getContentPane().setLayout(new CardLayout(0, 0)); //set CardLayout
		
		//Login Panel ************************************************************************************
		panelLogin = new JPanel();
		frmEsasSystem.getContentPane().add(panelLogin, "name_136526590665903");
		panelLogin.setLayout(null);
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.setBounds(207, 168, 89, 23);
		panelLogin.add(btnLogin);
		
		JButton btnLoginSignUp = new JButton("Sign Up"); //SignUp Button
		btnLoginSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_LoginUsername.setText("");
				passwordField_Login.setText("");
				panelLogin.setVisible(false);
				panelSignUp.setVisible(true);
				
			}
		});
		btnLoginSignUp.setBounds(108, 168, 89, 23);
		panelLogin.add(btnLoginSignUp);
		
		textField_LoginUsername = new JTextField();
		textField_LoginUsername.setBounds(181, 85, 112, 20);
		panelLogin.add(textField_LoginUsername);
		textField_LoginUsername.setColumns(10);
		
		JButton btnLoginForgottenCredentials = new JButton("Forgot you password?");
		btnLoginForgottenCredentials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_LoginUsername.setText("");
				passwordField_Login.setText("");
				panelLogin.setVisible(false);
				panelForgottenCredentials.setVisible(true);
			}
		});
		btnLoginForgottenCredentials.setBounds(108, 202, 188, 23);
		panelLogin.add(btnLoginForgottenCredentials);
		
		JLabel lblLoginTitle = new JLabel("Welcome to the ESAS System");
		lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLoginTitle.setBounds(73, 23, 272, 23);
		panelLogin.add(lblLoginTitle);
		
		JLabel lblLoginUsername = new JLabel("Username:");
		lblLoginUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginUsername.setBounds(108, 88, 65, 14);
		panelLogin.add(lblLoginUsername);
		
		JLabel lblLoginPassword = new JLabel("Password:");
		lblLoginPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginPassword.setBounds(108, 119, 65, 14);
		panelLogin.add(lblLoginPassword);
		
		passwordField_Login = new JPasswordField();
		passwordField_Login.setBounds(181, 116, 112, 20);
		panelLogin.add(passwordField_Login);
		
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
		
		JButton btnSignUpCreateNewUser = new JButton("Register");
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
				textField_SignUpCareProvider.setText("");
				passwordField_SignUpPassword.setText("");
				passwordField_SignUpConfirmPassword.setText("");
				textField_SecretAnswer.setText("");
				panelSignUp.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnSignUpGoBack.setBounds(259, 214, 146, 23);
		panelSignUp.add(btnSignUpGoBack);
		
		textField_SignUpCareProvider = new JTextField();
		textField_SignUpCareProvider.setBounds(103, 119, 86, 20);
		panelSignUp.add(textField_SignUpCareProvider);
		textField_SignUpCareProvider.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Care Provider:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 122, 84, 14);
		panelSignUp.add(lblNewLabel);
		
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
		
		textField_SecretAnswer = new JTextField();
		textField_SecretAnswer.setBounds(125, 215, 124, 20);
		panelSignUp.add(textField_SecretAnswer);
		textField_SecretAnswer.setColumns(10);
		
		JLabel lblSignUpSecretAnswer = new JLabel("Secret Answer:");
		lblSignUpSecretAnswer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSignUpSecretAnswer.setBounds(10, 218, 105, 14);
		panelSignUp.add(lblSignUpSecretAnswer);
		//----------------------------------------------------------------------------------------------------
		
		//Forgotten Credentials Panel*************************************************************************
		panelForgottenCredentials = new JPanel();
		frmEsasSystem.getContentPane().add(panelForgottenCredentials, "name_136911632557425");
		panelForgottenCredentials.setLayout(null);
		JButton btnForgottenCredentialsGenerateQuestion;
		JButton btnForgottenCredentialsAnswer;
		JLabel lblRecoverPassword;
		JLabel lblForgottenCredentialsSecretQuestion;
		JLabel lblForgottenCredentialsInstructions;
		JLabel lblForgottenCredentialsUsername;
		JButton btnForgottenCredentialsPreviousScreen;
		
		lblRecoverPassword = new JLabel("Recover Your Password");
		lblRecoverPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRecoverPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecoverPassword.setBounds(98, 23, 215, 35);
		panelForgottenCredentials.add(lblRecoverPassword);
		
		btnForgottenCredentialsGenerateQuestion = new JButton("Generate Secret Question");
		btnForgottenCredentialsGenerateQuestion.setBounds(58, 139, 280, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsGenerateQuestion);
		
		textField_ForgottenCredentialsSecretAnswer = new JTextField();
		textField_ForgottenCredentialsSecretAnswer.setBounds(236, 173, 102, 20);
		panelForgottenCredentials.add(textField_ForgottenCredentialsSecretAnswer);
		textField_ForgottenCredentialsSecretAnswer.setColumns(10);
		
		btnForgottenCredentialsAnswer = new JButton("Check Answer");
		btnForgottenCredentialsAnswer.setBounds(58, 204, 131, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsAnswer);
		
		lblForgottenCredentialsSecretQuestion = new JLabel("");
		lblForgottenCredentialsSecretQuestion.setBounds(37, 179, 176, 14);
		panelForgottenCredentials.add(lblForgottenCredentialsSecretQuestion);
		
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
				lblForgottenCredentialsSecretQuestion.setText("");
				panelForgottenCredentials.setVisible(false);
				panelLogin.setVisible(true);
			}
		});
		btnForgottenCredentialsPreviousScreen.setBounds(204, 204, 134, 23);
		panelForgottenCredentials.add(btnForgottenCredentialsPreviousScreen);
		//----------------------------------------------------------------------------------------------------
		
		//MainMenu Panel *************************************************************************************
		panelMainMenu = new JPanel();
		frmEsasSystem.getContentPane().add(panelMainMenu, "name_136916106062093");
		panelMainMenu.setLayout(null);
		
		JLabel lblMainMenuWelcome = new JLabel("Welcome ");
		lblMainMenuWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMainMenuWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuWelcome.setBounds(108, 31, 179, 22);
		panelMainMenu.add(lblMainMenuWelcome);
		
		JButton btnMainMenuViewHistory = new JButton("View History");
		btnMainMenuViewHistory.setBounds(128, 93, 159, 23);
		panelMainMenu.add(btnMainMenuViewHistory);
		
		JButton btnMainMenuAccessCPInformation = new JButton("Access Care Provider Info");
		btnMainMenuAccessCPInformation.setBounds(128, 127, 159, 23);
		panelMainMenu.add(btnMainMenuAccessCPInformation);
		
		JButton btnMainMenuCompleteSurvey = new JButton("Complete Survey");
		btnMainMenuCompleteSurvey.setBounds(128, 160, 159, 23);
		panelMainMenu.add(btnMainMenuCompleteSurvey);
		
		JLabel lblMainMenuSelectOption = new JLabel("Select of one the options below:");
		lblMainMenuSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenuSelectOption.setBounds(118, 64, 169, 14);
		panelMainMenu.add(lblMainMenuSelectOption);
		
		JButton btnMainMenuPreviousScreen = new JButton("Previous Screen");
		btnMainMenuPreviousScreen.setBounds(128, 194, 159, 23);
		panelMainMenu.add(btnMainMenuPreviousScreen);
		//----------------------------------------------------------------------------------------------------
		
		//Complete Survey Panel*******************************************************************************
		panelCompleteSurvey = new JPanel();
		frmEsasSystem.getContentPane().add(panelCompleteSurvey, "name_136918142667310");
		panelCompleteSurvey.setLayout(null);
		//----------------------------------------------------------------------------------------------------
		
		//View CareProvider Info Panel************************************************************************
		panelViewCareProviderInfo = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewCareProviderInfo, "name_136919613899853");
		panelViewCareProviderInfo.setLayout(null);
		//----------------------------------------------------------------------------------------------------
		
		//View History Panel**********************************************************************************
		panelViewHistoryPanel = new JPanel();
		frmEsasSystem.getContentPane().add(panelViewHistoryPanel, "name_136921281711804");
		panelViewHistoryPanel.setLayout(null);
		//----------------------------------------------------------------------------------------------------
	}
}
