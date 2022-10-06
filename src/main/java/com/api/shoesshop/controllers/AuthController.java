package com.api.shoesshop.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.shoesshop.dtos.ChangePasswordDTO;
import com.api.shoesshop.dtos.ChangeProfileDTO;
import com.api.shoesshop.dtos.CreateAccountDTO;
import com.api.shoesshop.dtos.LoginDTO;
import com.api.shoesshop.dtos.RefreshTokenDTO;
import com.api.shoesshop.entities.Account;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.AccountService;
import com.api.shoesshop.services.AuthService;
import com.api.shoesshop.types.Auth;
import com.api.shoesshop.utils.Helper;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.gson.io.GsonDeserializer;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    @Autowired
    private AccountService accountService;

    private AuthService authService = new AuthService();

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody CreateAccountDTO body) {
        try {
            Account account = accountService.save(body);
            String accessToken = authService.createAccessToken(account);
            String refreshToken = authService.createRefreshToken(account);
            ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).secure(false)
                    .maxAge(authService.getRefreshTokenExpired());
            return Helper.responseCreated(new Auth(account, accessToken, refreshToken));
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO body) {
        try {
            Account account = accountService.login(body);
            String accessToken = authService.createAccessToken(account);
            String refreshToken = authService.createRefreshToken(account);
            ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).secure(false)
                    .maxAge(authService.getRefreshTokenExpired());
            return Helper.responseSuccess(new Auth(account, accessToken, refreshToken));
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
    }

    @GetMapping("/auth/my-profile")
    public ResponseEntity<String> getProfile(HttpServletRequest req) {
        if (AuthInterceptor.isLoggedin(req) == true) {
            try {
                if (req.getAttribute("account_id") != null) {
                    Account account = accountService.findById(Long.parseLong(req.getAttribute("account_id").toString()))
                            .get();
                    return Helper.responseSuccess(account);
                }
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }

        return Helper.responseUnauthorized();
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout() {
        try {
            ResponseCookie.from("refresh_token", null).httpOnly(true).secure(false)
                    .maxAge(0);
            return Helper.responseSussessNoData();
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<String> refreshToken(@CookieValue(name = "refresh_token") String refreshToken,
            @RequestBody(required = false) RefreshTokenDTO dto) {
        try {
            if (refreshToken != null) {
                Claims claims = Jwts.parserBuilder()
                        .deserializeJsonWith(new GsonDeserializer<>(new Gson()))
                        .setSigningKey(new AuthService().getRefreshTokenSecret())
                        .build()
                        .parseClaimsJws(refreshToken)
                        .getBody();
                Account account = new Account();
                account.setId(Long.parseLong(claims.get("id", String.class)));
                account.setAccountRole(claims.get("role", String.class));
                String accessToken = authService.createAccessToken(account);
                String _refreshToken = authService.createRefreshToken(account);
                return Helper.responseSuccess(new Auth(accessToken, _refreshToken));
            } else if (dto != null) {
                Claims claims = Jwts.parserBuilder()
                        .deserializeJsonWith(new GsonDeserializer<>(new Gson()))
                        .setSigningKey(new AuthService().getRefreshTokenSecret())
                        .build()
                        .parseClaimsJws(dto.getRefreshToken())
                        .getBody();
                Account account = new Account();
                account.setId(Long.parseLong(claims.get("id", String.class)));
                account.setAccountRole(claims.get("role", String.class));
                String accessToken = authService.createAccessToken(account);
                String _refreshToken = authService.createRefreshToken(account);
                ResponseCookie.from("refresh_token", refreshToken).httpOnly(true).secure(false)
                        .maxAge(authService.getRefreshTokenExpired());
                return Helper.responseSuccess(new Auth(accessToken, _refreshToken));
            }

        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();
    }

    @PatchMapping("/auth/change-profile")
    public ResponseEntity<String> changeProfile(@RequestBody ChangeProfileDTO body, HttpServletRequest req) {
        if (AuthInterceptor.isLoggedin(req) == true) {
            try {
                long id = Long.parseLong(req.getAttribute("account_id").toString());
                Account oldAccount = accountService.findById(id).get();
                if (oldAccount != null) {
                    oldAccount.setFullName(body.getFullName());
                    Account newAccount = accountService.update(oldAccount, id);
                    if (newAccount != null) {
                        return Helper.responseSuccess(newAccount);
                    }
                }
                return Helper.responseError();
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }

        return Helper.responseUnauthorized();
    }

    @PatchMapping("/auth/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO body, HttpServletRequest req) {
        if (AuthInterceptor.isLoggedin(req) == true) {
            try {
                long id = Long.parseLong(req.getAttribute("account_id").toString());
                Account oldAccount = accountService.findById(id).get();
                if (oldAccount != null) {
                    if (Helper.verifyPassword(body.getOldPassword(), oldAccount.getHashedPassword()) == true) {
                        Account account = accountService.changePassword(body.getNewPassword(), id);
                        if (account != null) {
                            return Helper.responseSussessNoData();
                        }
                    }
                }
                return Helper.responseError();
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }

        return Helper.responseUnauthorized();
    }
}
