package com.wbd.cloud.model.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	 * .@JsonIgnore此注解用于属性或者方法上（最好是属性上），用来完全忽略被注解的字段和方法对应的属性，即便这个字段或方法可以被自动检测到或者还有其

他的注解，一般标记在属性或者方法上，返回的json数据即不包含该属性。

使用情景：需要把一个List<CustomerInfo >转换成json格式的数据传递给前台。但实体类中基本属性字段的值都存储在快照属性字段中。此时我可以在业务层中做处理，

把快照属性字段的值赋给实体类中对应的基本属性字段。最后，我希望返回的json数据中不包含这两个快照字段，那么在实体类中快照属性上加注解@JsonIgnore，

那么最后返回的json数据，将不会包含customerId和productId两个属性值。
	 * 
	 * 
	 */
	
	
	/**
	 * 把 角色与权限全部放入到GrantedAuthority集合中去
	 */
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new HashSet<>();

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
