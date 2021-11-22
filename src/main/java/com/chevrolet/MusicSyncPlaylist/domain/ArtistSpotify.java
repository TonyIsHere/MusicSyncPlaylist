package com.chevrolet.MusicSyncPlaylist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "art_spo_id"))
public class ArtistSpotify extends Artist {
	/*
	 * Properties
	 */
	@Column(name = "art_spo_id",nullable = false,length = 22)
	private String spotify;

	/*
	 * Constructor
	 */
	public ArtistSpotify() {

	}

	public ArtistSpotify(String name, String picture, String desc, String spotify) {
		super(name, picture, desc);
		this.spotify = spotify;
	}

	/*
	 * Get/Set
	 */
	public String getSpotify() {
		return spotify;
	}

	public void setSpotify(String spotify) {
		this.spotify = spotify;
	}

}
