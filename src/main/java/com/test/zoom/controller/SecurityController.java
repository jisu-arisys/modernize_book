package com.test.zoom.controller;

import com.test.zoom.data.Db;
import com.test.zoom.entity.Auth;
import com.test.zoom.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/** security 의 formLogin 사용하지 않고 직접 api 에서 처리하기위한 컨트롤러 **/
@RestController
@RequestMapping() //rewite 로 "/api" path 제거
//@CrossOrigin(origins = "http://localhost:5173") // Vue 서버 주소를 허용
@CrossOrigin(origins = "http://localhost:5177") // Vue 서버 주소를 허용
public class SecurityController {

    public Db DB = Db.getInstance();
    private final AuthenticationManager authenticationManager;
    public SecurityController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private void setAuthentication(User loginUser, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUserName(),
                        loginUser.getAuthName().toString()
                )
        );

//		SecurityContextHolder.getContext().setAuthentication(authentication); // 새로운 요청이오면 인증 정보가 유지되지 않음
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    /** RequestBody (application/json) 사용 (JSON 방식)하는 loginProcessingUrl api 를 직접 구현
     * UsernamePasswordAuthenticationFilter 미사용 setAuthentication 로 직접구현
     * RequestBody 로 JSON 객체를 전달하여 처리. **/
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpSession session) { //로그인요청 처리
        if(isUser(loginUser)) {
            setAuthentication(loginUser, session);
            // JWT 발급 (JWT 사용 시) 응답값에 map 으로 감싸서 전달.
//			String token = jwtTokenProvider.createToken(loginRequest.getUsername());

            return ResponseEntity.ok(DB.me);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public boolean isUser(User loginUser){
        System.out.println(loginUser);
        Auth auth = loginUser.getAuthName();
        String username = loginUser.getUserName();
        if(Db.me.getUserName().equals(username)){
            DB.me.setAuthName(auth);
            return true;
        }
        return false;
    }
}
