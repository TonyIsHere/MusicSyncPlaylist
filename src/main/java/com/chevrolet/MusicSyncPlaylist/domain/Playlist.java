package com.chevrolet.MusicSyncPlaylist.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Playlist {	
	/*
	 * Properties
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pla_id")
	private int id;
	
	@Column(name = "pla_name", nullable = false,length = 50)
	private String name;
	
	
	@Column(name = "pla_picture")
	private String picture;
	
	@ManyToOne
	@JoinColumn(name = "pla_use_id",nullable = false)
	private User user;
	


	/*
	 * Constructor
	 */
    public Playlist() {

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
	
}
