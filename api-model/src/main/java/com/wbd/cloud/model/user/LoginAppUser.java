package com.wbd.cloud.model.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * spring security 当前登录对象
 * 
 * @author jwh
 *
 */
public class LoginAppUser extends AppUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	// 当前登录用户所有的角色
	private Set<SysRole> sysRoles;

	// 当前用户所有的权限

	private Set<String> permissions;

	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 把 角色与权限全部放入到GrantedAuthority集合中去
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new HashSet<GrantedAuthority>();

		// 添加角色
		if (!CollectionUtils.isEmpty(sysRoles)) {
			sysRoles.forEach(role -> {
				if (role.getCode().startsWith("ROLE_")) {
					collection.add(new SimpleGrantedAuthority(role.getCode()));
				} else {
					collection.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
				}
			});

		}

		// 添加权限
		if (!CollectionUtils.isEmpty(permissions)) {

			permissions.forEach(permission -> {

				collection.add(new SimpleGrantedAuthority(permission));

			});
		}

		return collection;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		return getEnabled();
	}

}
