package com.chevrolet.MusicSyncPlaylist.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	/*
	 * Properties
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "use_id")	
	private int id;

	@Column(name = "use_username", nullable = false, unique = true,length = 50)
	private String nickname;

	@Column(name = "use_email", nullable = false, unique = true,length = 100)
	private String email;

	@Column(name = "use_password", nullable = false,length = 60)
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	@Column(name = "use_role", nullable = false,length = 10)
	private Roles role;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	List<Playlist> playlists;

	/*
	 * Constructor
	 */
	public User() {

	}

	/*
	 * Get/Set
	 */
}
