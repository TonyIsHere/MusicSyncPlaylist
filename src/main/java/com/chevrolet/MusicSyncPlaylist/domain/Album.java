package com.chevrolet.MusicSyncPlaylist.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

//https://thorben-janssen.com/complete-guide-inheritance-strategies-jpa-hibernate/
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Album {
	/*
	 * Properties
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alb_id")
	private int id;
	
	@Column(name = "alb_name",nullable = false)
	private String name;
	
	@Column(name = "alb_release_date",nullable = false)
	private LocalDate release;
	
	@Column(name = "alb_picture")
	private String picture;
	
	@Column(name = "alb_type",nullable = false,length = 20)
	private String type;

	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "album")
	List<Track> tracks;
	
	@ManyToMany(mappedBy = "albums")
	private Set<Artist> artists = new HashSet<>();

	/*
	 * Constructor
	 */
	public Album() {
		
	}
	
	
	public Album(String name, LocalDate release, String picture, String type) {
		super();
		this.name = name;
		this.release = release;
		this.picture = picture;
		this.type = type;
		this.tracks = new ArrayList<Track>();
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

	public LocalDate getRelease() {
		return release;
	}

	public void setRelease(LocalDate release) {
		this.release = release;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public Set<Artist> getArtists() {
		return artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}
	
	
}
