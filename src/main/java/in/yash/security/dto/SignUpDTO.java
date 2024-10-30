package in.yash.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDTO {
	
	private String name;
	private String email;
	private String password;
	private String role;

}
