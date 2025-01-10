package com.aiken.pos.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aiken.pos.admin.model.SysUser;
import com.aiken.pos.admin.repository.SysUserRepository;
import com.aiken.pos.admin.util.CustomUserDetails;

/**
 * User Service
 * 
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-27
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SysUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		SysUser sysUser = userRepository.findByUsername(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException("User " //
					+ username + " was not found in the database");
		}

		return new CustomUserDetails(sysUser);
	}

}