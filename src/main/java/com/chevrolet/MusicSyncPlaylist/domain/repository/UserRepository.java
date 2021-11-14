package com.chevrolet.MusicSyncPlaylist.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.chevrolet.MusicSyncPlaylist.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	 User findByNickname(String nickname);
	}