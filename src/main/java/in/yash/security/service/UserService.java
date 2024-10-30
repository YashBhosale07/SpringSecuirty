package in.yash.security.service;

import in.yash.security.dto.LoginRequestDTO;
import in.yash.security.dto.SignUpDTO;
import in.yash.security.dto.SignUpResponseDTO;

public interface UserService {
	
	SignUpResponseDTO signUp(SignUpDTO signUpDTO);
	String login(LoginRequestDTO dto);

}
