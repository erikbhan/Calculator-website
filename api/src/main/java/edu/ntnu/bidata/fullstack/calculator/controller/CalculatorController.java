package edu.ntnu.bidata.fullstack.calculator.controller;

import java.util.HashSet;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.bidata.fullstack.calculator.model.Calculation;
import edu.ntnu.bidata.fullstack.calculator.model.User;
import edu.ntnu.bidata.fullstack.calculator.payload.request.CalculationRequest;
import edu.ntnu.bidata.fullstack.calculator.payload.response.MessageResponse;
import edu.ntnu.bidata.fullstack.calculator.repository.CalculationRepository;
import edu.ntnu.bidata.fullstack.calculator.repository.UserRepository;
import edu.ntnu.bidata.fullstack.calculator.security.jwt.JwtUtils;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class CalculatorController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CalculationRepository calculationRepository;

	@Autowired
	JwtUtils jwtUtils;
    
    @PostMapping("/calculation")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> calculationExpression(@Valid @RequestBody CalculationRequest calculateRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findByUsername(auth.getName());
        if (optUser.isPresent() && !(optUser == null)) {
            Calculation calculation = new Calculation(calculateRequest.getFirstNumber(), calculateRequest.getSecondNumber(), calculateRequest.getOperator());
            calculationRepository.save(calculation);
            User user = optUser.get();
            HashSet<Calculation> calculations = new HashSet<>(user.getCalculations());
            calculations.add(calculation);
            user.setCalculations(calculations);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse(String.valueOf(calculation.getAnswer())));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found"));
    }

    @GetMapping("/history")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optUser = userRepository.findByUsername(auth.getName());
        if (optUser.isPresent() && !(optUser == null)) {
            User user = optUser.get();
            HashSet<Calculation> calculations = new HashSet<>(user.getCalculations());
            return ResponseEntity.ok(calculations);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found"));
    }
}
