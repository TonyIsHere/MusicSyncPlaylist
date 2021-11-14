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
	public User(String nickname,String email, String psw,Roles role) {
		this.nickname = nickname;
		this.email = email;
		this.passwordHash = psw;
		this.role = role;
	}

	/*
	 * Get/Set
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
}
