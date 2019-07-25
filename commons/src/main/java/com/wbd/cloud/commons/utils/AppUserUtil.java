package com.wbd.cloud.commons.utils;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.alibaba.fastjson.JSONObject;
import com.wbd.cloud.model.user.LoginAppUser;

/**
 * 通过SecurityContextHolder获取登录用户的相关信息
 * 
 * @author zgh
 *
 */
public class AppUserUtil {

	 /**
	    * 该方法其实对这个请求的http://localhost:8889/user-me?access_token=x
	    * 返回的一大串json进行解析，然后把对应的值通过json转对象 赋给LoginAppUser
	    * 然后返回该对象
	    * @return
	    */
	/**
	 * 
	 * 
	 * 获取登录的LoginAppUser
	 */

	public static LoginAppUser getLoginAppUser() {
		// 通过SecurityContextHolder获取Authentication接口
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof OAuth2Authentication) {
			// 把Authentication转换为OAuth2Authentication
			// OAuth2Authentication实现了Authentication接口
			OAuth2Authentication oauth2Auth = (OAuth2Authentication) authentication;

			authentication = oauth2Auth.getUserAuthentication();

			if (authentication instanceof UsernamePasswordAuthenticationToken) {

				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
				Object principal = authentication.getPrincipal();

				if (principal instanceof LoginAppUser) {
					return (LoginAppUser) principal;
				}

				Map map = (Map) authenticationToken.getDetails();

				map = (Map) map.get("principal");
                //将json转换为LoginAppUser对象
				return JSONObject.parseObject(JSONObject.toJSONString(map), LoginAppUser.class);
			}

		}
		return null;
	}
	
	/**
	 * http://localhost:8889/user-me?access_token=x  该请求返回的json
	 * 然后通过key=principal获取到我们想要的json数据，再转换为LoginAppUser对象
	 * 
	 * 
	 * {
    "authorities": [
        {
            "authority": "back:menu:set2role"
        },
        {
            "authority": "mail:update"
        },
        {
            "authority": "back:permission:delete"
        },
        {
            "authority": "role:permission:byroleid"
        },
        {
            "authority": "back:menu:save"
        },
        {
            "authority": "back:menu:query"
        },
        {
            "authority": "ip:black:query"
        },
        {
            "authority": "client:query"
        },
        {
            "authority": "ip:black:save"
        },
        {
            "authority": "file:del"
        },
        {
            "authority": "mail:query"
        },
        {
            "authority": "ip:black:delete"
        },
        {
            "authority": "back:user:query"
        },
        {
            "authority": "back:role:permission:set"
        },
        {
            "authority": "sms:query"
        },
        {
            "authority": "back:role:query"
        },
        {
            "authority": "client:save"
        },
        {
            "authority": "back:role:save"
        },
        {
            "authority": "back:permission:query"
        },
        {
            "authority": "back:user:role:set"
        },
        {
            "authority": "log:query"
        },
        {
            "authority": "file:query"
        },
        {
            "authority": "client:update"
        },
        {
            "authority": "back:menu:update"
        },
        {
            "authority": "back:role:delete"
        },
        {
            "authority": "back:role:update"
        },
        {
            "authority": "back:user:password"
        },
        {
            "authority": "ROLE_SUPER_ADMIN"
        },
        {
            "authority": "back:menu:delete"
        },
        {
            "authority": "back:user:update"
        },
        {
            "authority": "menu:byroleid"
        },
        {
            "authority": "client:del"
        },
        {
            "authority": "mail:save"
        },
        {
            "authority": "user:role:byuid"
        },
        {
            "authority": "back:permission:save"
        },
        {
            "authority": "back:permission:update"
        }
    ],
    "details": {
        "remoteAddress": "0:0:0:0:0:0:0:1",
        "sessionId": "724644ae-10db-479b-b6c1-1e9874b83bef",
        "tokenValue": "45620192-b360-48b8-b3d0-cfca5ee537d5",
        "tokenType": "Bearer",
        "decodedDetails": null
    },
    "authenticated": true,
    "userAuthentication": {
        "authorities": [
            {
                "authority": "back:menu:set2role"
            },
            {
                "authority": "mail:update"
            },
            {
                "authority": "back:permission:delete"
            },
            {
                "authority": "role:permission:byroleid"
            },
            {
                "authority": "back:menu:save"
            },
            {
                "authority": "back:menu:query"
            },
            {
                "authority": "ip:black:query"
            },
            {
                "authority": "client:query"
            },
            {
                "authority": "ip:black:save"
            },
            {
                "authority": "file:del"
            },
            {
                "authority": "mail:query"
            },
            {
                "authority": "ip:black:delete"
            },
            {
                "authority": "back:user:query"
            },
            {
                "authority": "back:role:permission:set"
            },
            {
                "authority": "sms:query"
            },
            {
                "authority": "back:role:query"
            },
            {
                "authority": "client:save"
            },
            {
                "authority": "back:role:save"
            },
            {
                "authority": "back:permission:query"
            },
            {
                "authority": "back:user:role:set"
            },
            {
                "authority": "log:query"
            },
            {
                "authority": "file:query"
            },
            {
                "authority": "client:update"
            },
            {
                "authority": "back:menu:update"
            },
            {
                "authority": "back:role:delete"
            },
            {
                "authority": "back:role:update"
            },
            {
                "authority": "back:user:password"
            },
            {
                "authority": "ROLE_SUPER_ADMIN"
            },
            {
                "authority": "back:menu:delete"
            },
            {
                "authority": "back:user:update"
            },
            {
                "authority": "menu:byroleid"
            },
            {
                "authority": "client:del"
            },
            {
                "authority": "mail:save"
            },
            {
                "authority": "user:role:byuid"
            },
            {
                "authority": "back:permission:save"
            },
            {
                "authority": "back:permission:update"
            }
        ],
        "details": {
            "grant_type": "password",
            "scope": "app",
            "client_secret": "admin",
            "client_id": "system",
            "username": "admin"
        },
        "authenticated": true,
        "principal": {
            "id": 1,
            "username": "admin",
            "password": "$2a$10$rWeDtS0GjH.dEuwc20zs5eh0.CEQoJxxe91wMSoFHHAj8gRg3Xq0G",
            "nickname": "ddd",
            "headImgUrl": "ddd",
            "phone": "18107212317",
            "sex": 0,
            "enabled": true,
            "type": "APP",
            "createTime": "2018-01-17T08:57:01.000+0000",
            "updateTime": "2019-07-11T14:35:43.000+0000",
            "sysRoles": [
                {
                    "id": 1,
                    "code": "SUPER_ADMIN",
                    "name": "超级管理员",
                    "createTime": "2018-01-19T12:32:16.000+0000",
                    "updateTime": "2018-01-19T12:32:18.000+0000"
                }
            ],
            "permissions": [
                "back:menu:set2role",
                "mail:update",
                "back:permission:delete",
                "role:permission:byroleid",
                "back:menu:save",
                "back:menu:query",
                "ip:black:query",
                "client:query",
                "ip:black:save",
                "file:del",
                "mail:query",
                "ip:black:delete",
                "back:user:query",
                "back:role:permission:set",
                "sms:query",
                "back:role:query",
                "client:save",
                "back:role:save",
                "back:permission:query",
                "back:user:role:set",
                "log:query",
                "file:query",
                "client:update",
                "back:menu:update",
                "back:role:delete",
                "back:role:update",
                "back:user:password",
                "back:menu:delete",
                "back:user:update",
                "menu:byroleid",
                "client:del",
                "mail:save",
                "user:role:byuid",
                "back:permission:save",
                "back:permission:update"
            ],
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true
        },
        "credentials": null,
        "name": "admin"
    },
    "credentials": "",
    "principal": {
        "id": 1,
        "username": "admin",
        "password": "$2a$10$rWeDtS0GjH.dEuwc20zs5eh0.CEQoJxxe91wMSoFHHAj8gRg3Xq0G",
        "nickname": "ddd",
        "headImgUrl": "ddd",
        "phone": "18107212317",
        "sex": 0,
        "enabled": true,
        "type": "APP",
        "createTime": "2018-01-17T08:57:01.000+0000",
        "updateTime": "2019-07-11T14:35:43.000+0000",
        "sysRoles": [
            {
                "id": 1,
                "code": "SUPER_ADMIN",
                "name": "超级管理员",
                "createTime": "2018-01-19T12:32:16.000+0000",
                "updateTime": "2018-01-19T12:32:18.000+0000"
            }
        ],
        "permissions": [
            "back:menu:set2role",
            "mail:update",
            "back:permission:delete",
            "role:permission:byroleid",
            "back:menu:save",
            "back:menu:query",
            "ip:black:query",
            "client:query",
            "ip:black:save",
            "file:del",
            "mail:query",
            "ip:black:delete",
            "back:user:query",
            "back:role:permission:set",
            "sms:query",
            "back:role:query",
            "client:save",
            "back:role:save",
            "back:permission:query",
            "back:user:role:set",
            "log:query",
            "file:query",
            "client:update",
            "back:menu:update",
            "back:role:delete",
            "back:role:update",
            "back:user:password",
            "back:menu:delete",
            "back:user:update",
            "menu:byroleid",
            "client:del",
            "mail:save",
            "user:role:byuid",
            "back:permission:save",
            "back:permission:update"
        ],
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true
    },
    "oauth2Request": {
        "clientId": "system",
        "scope": [
            "app"
        ],
        "requestParameters": {
            "grant_type": "password",
            "scope": "app",
            "client_id": "system",
            "username": "admin"
        },
        "resourceIds": [],
        "authorities": [],
        "approved": true,
        "refresh": false,
        "redirectUri": null,
        "responseTypes": [],
        "extensions": {},
        "grantType": "password",
        "refreshTokenRequest": null
    },
    "clientOnly": false,
    "name": "admin"
}
	 * 
	 * 
	 */
}
