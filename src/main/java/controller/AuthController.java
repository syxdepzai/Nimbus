package controller;

@PostMapping("/register")public ResponseEntity<?>register(@Valid @RequestBody RegisterUserRequest request){Optional<User>existingUser=userRepository.findByEmail(request.getEmail());if(existingUser.isPresent()){return ResponseEntity.badRequest().body("Email already registered");}

User user=new User();user.setEmail(request.getEmail());user.setPassword(passwordEncoder.encode(request.getPassword()));user.setFullName(request.getFullName());user.setRole("USER");userRepository.save(user);

String otp=String.valueOf(new Random().nextInt(999999));emailService.sendOtp(request.getEmail(),"Activate your account",otp);

return ResponseEntity.ok("User registered successfully. Please check your email for OTP.");}@PostMapping("/verify-otp")public ResponseEntity<?>verifyOtp(@RequestParam String email,@RequestParam String otp){Optional<User>userOpt=userRepository.findByEmail(email);if(userOpt.isEmpty()){return ResponseEntity.badRequest().body("Invalid email");}

// Lưu OTP tạm trong bộ nhớ hoặc database, đây là giả lập:
String expectedOtp="123456";

if(!expectedOtp.equals(otp)){return ResponseEntity.badRequest().body("Invalid OTP");}

User user=userOpt.get();user.setIsActive(true);userRepository.save(user);

return ResponseEntity.ok("Account activated successfully");}@PostMapping("/login")public ResponseEntity<?>login(@Valid @RequestBody LoginRequest request){Optional<User>userOpt=userRepository.findByEmail(request.getEmail());if(userOpt.isEmpty()||!passwordEncoder.matches(request.getPassword(),userOpt.get().getPassword())){return ResponseEntity.status(401).body("Invalid email or password");}

String token=jwtUtil.generateToken(request.getEmail(),86400000);return ResponseEntity.ok(token);}