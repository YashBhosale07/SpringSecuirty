package in.yash.security.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.yash.security.dto.LoginRequestDTO;
import in.yash.security.dto.SignUpDTO;
import in.yash.security.dto.SignUpResponseDTO;
import in.yash.security.service.UserService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/demo")
	public String demo() {
		return "working";
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?>registerUser(@RequestBody SignUpDTO signUpDTO){
		SignUpResponseDTO dto=userService.signUp(signUpDTO);
		return new ResponseEntity<SignUpResponseDTO>(dto,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?>loginUser(@RequestBody LoginRequestDTO dto){
		 return null;
	}

}
