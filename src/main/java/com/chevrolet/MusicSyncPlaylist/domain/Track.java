package com.chevrolet.MusicSyncPlaylist.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Track {
	/*
	 * Properties
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tra_id")
	private int id;

	@Column(name = "tra_name",nullable = false)
	private String name;

	@Column(name = "tra_duration")
	private int duration;

	@ManyToOne
	@JoinColumn(name = "tra_alb_id",nullable = false)
	private Album album;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Track_Artist", joinColumns = { @JoinColumn(name = "tra_id") }, inverseJoinColumns = {
			@JoinColumn(name = "art_id") })
	Set<Artist> artists = new HashSet<>();
	
	/*
	 * Constructor
	 */
	public Track() {
	};

	public Track(String name, int duration) {
		this.name = name;
		this.duration = duration;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Set<Artist> getArtists() {
		return artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}
	
	public String getArtistsString() {
		String art = ""; // from your method
		for(Artist x : artists) {
			art += x.getName()+", ";
		}
		
		art = art.substring(0,art.length()-2);
		return art;
	}
}
