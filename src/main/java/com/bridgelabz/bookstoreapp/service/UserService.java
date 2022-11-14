package com.bridgelabz.bookstoreapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.exception.BookException;
import com.bridgelabz.bookstoreapp.exception.UserException;
import com.bridgelabz.bookstoreapp.model.UserModel;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.util.EmailSenderService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@Service
public class UserService implements IUserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TokenUtil tokenUtil;
	
	@Autowired
	EmailSenderService emailSender;
	    
	//Generated token after saving data and sent email
    public String addUser(UserDTO userDTO) throws UserException {
        Optional<UserModel> model = Optional.ofNullable(userRepo.findByEmailAddress(userDTO.getEmailAddress()));
        if(model.isPresent()) {
        	return "Email already exists";
        }
        else {
        UserModel userModel = new UserModel(userDTO);
        userRepo.save(userModel);
        String token = tokenUtil.createToken(userModel.getUserId());
        //email body
        String data = "Hi "+userModel.getFirstName()+" \uD83D\uDE00,\n\nThank you for choosing BookStore App \uD83E\uDD1D\n\n"
        		+"Your account is successfully created. Please find the details below\n\n"+
        "USER DETAILS: \n"+"\uD83D\uDC71 First Name: "+userModel.getFirstName()+",\t"+
        		"Last Name: "+userModel.getLastName()+"\n\uD83C\uDFE0 Address: "+userModel.getAddress()+"\n"+
        		"\uD83D\uDCE7 Email Address: "+userModel.getEmailAddress()+"\n\uD83D\uDCC6 DOB: "+userModel.getDOB()+
        		"\n\uD83D\uDD11 Password: "+userModel.getPassword()+"\n\uD83E\uDE99 Token :"+token
        		+"\n\nRegards \uD83D\uDE4F,\nBookStore Team";
        //sending email
        emailSender.sendEmail(userModel.getEmailAddress(), "Registered Successfully", data);
        return token;}
    }
    
    //Get all User Details list
    public List<UserModel> getAllUsersData() {
        List<UserModel> userList = userRepo.findAll();
        if (userList.isEmpty()) {
            throw new UserException("No User Registered yet!!!!");
        } else
            return userList;
    }
    
    //Get the user data by id
    public UserModel getUserDataById(int id) {
    	UserModel userModel = userRepo.findById(id).orElse(null);
        if (userModel != null) {
            return userModel;
        } else
            throw new UserException("UserID: " + id + ", does not exist");
    }
    
    //Get User Details by Token
    public UserModel getUserDataByToken(String token) {
    	try{
    		int id = tokenUtil.decodeToken(token);
    		return userRepo.findById(id).stream()
                .filter(data -> data.getUserId() == id)
                .findFirst()
                .orElseThrow(() -> new UserException("Invalid User/Token"));}
    	catch(Exception e){throw new UserException("Invalid User/Token");}
    }
    
    //Get the User Details by Email Address
    public UserModel getUserDataByEmail(String email) {
    	UserModel userModel = userRepo.findByEmailAddress(email);
        if (userModel != null) {
            return userModel;
        } else
            throw new UserException("Email Address: " + email + ", does not exist");
    }
    
    //Send email link for the forgot password
    public String forgotPassword(String email, String newPassword) {
        UserModel userModel = userRepo.findByEmailAddress(email);
        if(userModel!=null){
           	userModel.setPassword(newPassword);//changing password
           	userRepo.save(userModel);
            return "Hi "+userModel.getFirstName()+", your password has been successfully reset.";
        }else
            throw new UserException("Invalid Email Address");
    }
    
    //reset password
    public String changePassword(LoginDTO loginDTO, String newPassword) {
        UserModel userModel = userRepo.findByEmailAddress(loginDTO.getEmailAddress());
        if(userModel!=null){
        	if(userModel.getPassword().equals(loginDTO.getPassword())) {
        		userModel.setPassword(newPassword); //changing password
            }else {
            	return "Incorrect old password!!";
            }
            //Sending Email
            //emailSender.sendEmail(userModel.get().getEmailAddress(), "Password Change!", "Password Changed Successfully!");
            userRepo.save(userModel);
            return "Password Changed Successfully!";
        }else
            return "Invalid Email Address";
    }
    
    //Update data by email address
    public UserModel updateUserDataByEmail(UserDTO userDTO, String email) {
    	UserModel userModel = userRepo.findByEmailAddress(email);
        if (userModel != null) {
            userModel.setFirstName(userDTO.getFirstName());
            userModel.setLastName(userDTO.getLastName());
            userModel.setAddress(userDTO.getAddress());
            userModel.setEmailAddress(userDTO.getEmailAddress());
            userModel.setDOB(userDTO.getDOB());
            userModel.setPassword(userDTO.getPassword());
          //email body
            String data = "Hi "+userModel.getFirstName()+" \uD83D\uDE07"+",\n\nYour details are successfully updated \uD83D\uDC4D\n\n"+
            "UPDATED DETAILS: \n"+"\uD83D\uDC71 First Name: "+userModel.getFirstName()+",\t"+
            		"Last Name: "+userModel.getLastName()+"\n\uD83C\uDFE0 Address: "+userModel.getAddress()+"\n"+
            		"\uD83D\uDCE7 Email Address: "+userModel.getEmailAddress()+"\n\uD83D\uDCC6 DOB: "+userModel.getDOB()+
            		"\n\uD83D\uDD11 Password: "+userModel.getPassword()+"\n\nRegards \uD83D\uDE4F,\nBookStore Team";
            //sending email
            //emailSender.sendEmail(userModel.getEmailAddress(), "Data Updated!!!",data );
            return userRepo.save(userModel);
        } else
            throw new UserException("Invalid Email Address: " + email);
    }
    
    //Login check
    public String loginUser(LoginDTO loginDTO) {
        UserModel userModel = userRepo.findByEmailAddress(loginDTO.getEmailAddress());
        if(userModel!=null){
        	if(userModel.getPassword().equals(loginDTO.getPassword())) {
                return "Login Successful!!";
            } else
                throw new UserException("Login Failed, Wrong Password!!!");
        }else
            throw new UserException("Login Failed, Invalid emailId or password!!!");
    }
        
    //Delete data by id
    public int deleteUser(int id) {
    	UserModel userModel = userRepo.findById(id).orElse(null);
        if(userModel != null){
        	String msg = "Hey "+userModel.getFirstName()+" \uD83D\uDE07,\n\n Your account has been successfully removed"
           			+ " from BookStore App..!!\n"
           			+ "Hope to see you again soon \uD83D\uDE1E!\n\n"
           			+ "Thank you,\n"
           			+ "BookStore Team";
        	//sending email
            //emailSender.sendEmail(userModel.getEmailAddress(), "User Removed!!!", msg);
            userRepo.deleteById(id);
        }else
            throw new UserException("Error: Cannot find User ID " + id);
        return id;
    }
    
    public String generateToken(int id) {
        UserModel model = userRepo.findById(id).get();
        if(model != null) {
        String token = tokenUtil.createToken(model.getUserId());
        return token;}
        else throw new UserException("Invalid ID");
    }
    
    public boolean validateUser(String token) {
    	try{
       		int id = tokenUtil.decodeToken(token);
       		do {
       			return true;
       		}while(userRepo.findById(id).isPresent());}
    	catch(Exception e){throw new BookException("Invalid User/ Token");}
       		
    }
}
