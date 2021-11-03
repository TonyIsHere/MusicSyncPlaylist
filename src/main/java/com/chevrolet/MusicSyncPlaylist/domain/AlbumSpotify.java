package com.chevrolet.MusicSyncPlaylist.domain;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "alb_spo_id"))
public class AlbumSpotify extends Album {
	/*
	 * Properties
	 */
	@Column(name = "alb_spo_id",nullable = false,length = 22)
	private String spotify;
	
	/*
	 * Constructor
	 */
	public AlbumSpotify()
	{
		
	}

	public AlbumSpotify(String name, LocalDate release, String picture, String type,String spotify) {
		super(name, release, picture, type);
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
