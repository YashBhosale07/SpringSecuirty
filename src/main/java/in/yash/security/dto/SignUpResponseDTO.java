package in.yash.security.dto;

import lombok.Data;

@Data
public class SignUpResponseDTO {
	
	private Long id;
	private String name;
	private String email;
	private String role;

}
