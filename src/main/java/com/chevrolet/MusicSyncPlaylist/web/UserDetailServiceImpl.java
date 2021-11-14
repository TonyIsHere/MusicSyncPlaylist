package com.chevrolet.MusicSyncPlaylist.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chevrolet.MusicSyncPlaylist.domain.User;
import com.chevrolet.MusicSyncPlaylist.domain.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserRepository repository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Autowired
	private HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User curruser = repository.findByNickname(username);
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(),
				AuthorityUtils.createAuthorityList(curruser.getRole().toString()));

		session.setAttribute("id", curruser.getId());
		return user;
	}
}