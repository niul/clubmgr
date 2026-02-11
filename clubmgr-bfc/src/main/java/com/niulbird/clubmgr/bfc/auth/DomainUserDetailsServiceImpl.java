package com.niulbird.clubmgr.bfc.auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Role;
import com.niulbird.clubmgr.db.model.User;
import com.niulbird.clubmgr.db.repository.UserRepository;

public class DomainUserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(DomainUserDetailsServiceImpl.class);
	
	@Autowired
    private UserRepository userRepository;

	@Autowired
	private HttpServletRequest request;
	
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	log.debug("Authenticating user [" + username + "] in Club [" + request.getServerName() + "]");
        User user = userRepository.findByUsernameAndClubDomain(username, request.getServerName());
        if (user == null) {
        	log.debug("Failed to find user[" + username + "] in club[" + request.getServerName() + "]");
        	return null;
        }
        log.debug("Found user[" + username + "] in club [" + request.getServerName() + "] ID: " + user.getUserId());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleKey()));
            log.debug("Adding Role: " + role.getRoleKey());
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
