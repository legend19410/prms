package com.police_resource_manager.prms.leave;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.police_resource_manager.prms.officers.Officer;
import com.police_resource_manager.prms.security.DecodedToken;

@RestController
@RequestMapping("/api")
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;
	
	@GetMapping("/leaves")
	public List<Leave> getLeaves(){
		return leaveService.getAllLeave();
	}
	
	@GetMapping("/leave")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<List<Leave>> getLeaves(@RequestHeader(name="Authorization") String encodedtoken, @RequestHeader(name="Formations") String formations) throws UnsupportedEncodingException{
		
		DecodedToken decodedToken = DecodedToken.getDecoded(encodedtoken);
		String username = decodedToken.sub;

		List<String> roles = new ArrayList<String>(Arrays.asList(formations.split(" ")));
		
		List<Leave> allLeaves = leaveService.getLeavesByFormation(roles, username);
		return ResponseEntity.ok(allLeaves);
	}
	
}
