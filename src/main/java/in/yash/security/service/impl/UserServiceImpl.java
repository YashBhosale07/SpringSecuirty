package in.yash.security.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import in.yash.security.dto.LoginRequestDTO;
import in.yash.security.dto.SignUpDTO;
import in.yash.security.dto.SignUpResponseDTO;
import in.yash.security.exceptionClasses.IncorrectUserDetails;
import in.yash.security.exceptionClasses.UserAlreadyPresentException;
import in.yash.security.jwtService.JwtService;
import in.yash.security.model.User;
import in.yash.security.repository.UserRepository;
import in.yash.security.service.UserService;
import in.yash.security.utils.Roles;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
	 @Autowired JwtService jwtService;
	 
	@Override
	public SignUpResponseDTO signUp(SignUpDTO signUpDTO) {
		if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw new UserAlreadyPresentException("User is already present with email: " + signUpDTO.getEmail());
        }
		User u=mapper.map(signUpDTO, User.class);
		String role="ROLE_"+signUpDTO.getRole().toUpperCase();
		u.setRole(Roles.valueOf(role));
		u.setPassword(encoder.encode(signUpDTO.getPassword()));
		User savedUser=userRepository.save(u);
		SignUpResponseDTO response=mapper.map(savedUser,SignUpResponseDTO.class);
		return response;
	}

	@Override
	public String login(LoginRequestDTO dto)  {
	    try {
	        UsernamePasswordAuthenticationToken authenticationToken = 
	                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
	        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(authenticationToken);
	        UserDetails details = (UserDetails) authentication.getPrincipal();
	        String token = jwtService.generateToken(details.getUsername());
	        return token;
	    } catch (Exception e) {
	        throw new IncorrectUserDetails("Incorrect username or password");
	    }
	}


}
