package com.aiken.pos.admin.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.aiken.pos.admin.model.SysUser;

/**
 * @author Nandana Basnayake
 * @version 1.1
 * @since 2021-11-05
 */
public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private SysUser user;
	
	public CustomUserDetails(SysUser user) {
        this.user = user;
    }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = user.getUserRole();
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		// ROLE_EMPLOYEE, ROLE_MANAGER
		GrantedAuthority authority = new SimpleGrantedAuthority(role);
		grantList.add(authority);
        return grantList;
	}

    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	public Integer getUserId() {
		return user.getUserId();
	}
	public String getFirstName() {
        return this.user.getFirstName();
    }
	public String getLastName() {
        return this.user.getLastName();
    }
	public boolean isNewUser() {
		return this.user.isNewUser();
	}
	public int getFailedAttempt() {
		return this.user.getFailedAttempt();
	}
	public String getUserRole() {
		return this.user.getUserRole();
	}
	public String getUserGroup() {
		return this.user.getUserGroup();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof CustomUserDetails)) return false;
		CustomUserDetails that =(CustomUserDetails) obj;

		return user.getUsername().equals(that.user.getUsername()) &&
				user.getPassword().equals(that.user.getPassword()) &&
				user.getUserRole().equals(that.user.getUserRole()) &&
				user.isActive() == that.user.isActive() &&
				user.isAccountNonLocked() == that.user.isAccountNonLocked();
	}
	@Override
	public int hashCode() {
		return Objects.hash(user.getUsername(),user.getPassword(),user.getUserRole(),user.isActive(),user.isAccountNonLocked());
	}
}
