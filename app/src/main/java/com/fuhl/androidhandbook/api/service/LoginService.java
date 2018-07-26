package com.fuhl.androidhandbook.api.service;

import com.fuhl.androidhandbook.api.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author tony
 * @date 2018/7/17
 */
public interface LoginService {
    /**
     * 请求接口获取Token
     * @param requestBody 请求内容
     * @return 返回BaseEntity<LoginReponseDataModel>
     */
    @POST("auth")
    Observable<BaseEntity<LoginReponseDataModel>> auth(@Body LoginRequest requestBody);

    class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    class LoginReponseDataModel {

        private String token;
        private String role;

        public void setRole(String role) {
            this.role = role;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRole() {
            return role;
        }

        public String getToken() {
            return token;
        }
    }
}
