package com.chevrolet.MusicSyncPlaylist.domain;

import java.util.HashSet;
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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Artist {
	/*
	 * Properties
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "art_id")
	private int id;

	@Column(name = "art_name",nullable = false)
	private String name;

	@Column(name = "art_pic")
	private String picture;

	@Column(name = "art_desc")
	private String desc;

	@ManyToMany(mappedBy = "artists")
	private Set<Track> tracks = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Artist_Album", joinColumns = { @JoinColumn(name = "art_id") }, inverseJoinColumns = {
			@JoinColumn(name = "alb_id") })
	Set<Album> albums = new HashSet<>();

	/*
	 * Constructor
	 */
	public Artist() {

	};
	
	public Artist(String name,String picture,String desc)
	{
		this.name = name;
		this.picture = picture;
		this.desc = desc;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<Track> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Track> tracks) {
		this.tracks = tracks;
	}

	public Set<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
}
