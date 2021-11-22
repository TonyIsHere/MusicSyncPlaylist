package com.chevrolet.MusicSyncPlaylist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "tra_spo_id"))
public class TrackSpotify extends Track {

	/*
	 * Properties
	 */
	@Column(name = "tra_spo_id",nullable = false,length = 22)
	private String spotify;
	
	@Column(name = "tra_spo_preview")
	private String preview;

	/*
	 * Constructor
	 */
	public TrackSpotify() {
	};
	
	public TrackSpotify(String name, int duration,String spotify,String preview) {
		super(name, duration);
		this.spotify = spotify;
		this.preview = preview;
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

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}
}
