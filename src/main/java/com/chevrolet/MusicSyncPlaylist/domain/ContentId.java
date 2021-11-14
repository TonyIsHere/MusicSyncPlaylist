package com.chevrolet.MusicSyncPlaylist.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

//https://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example
@Embeddable
public class ContentId implements Serializable {
	/*
	 * Properties
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private Playlist playlist;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Track track;
	
	@Column(name = "con_position",nullable = false)
	private int position;
	/*
	 * Get/Set
	 */
	
	public ContentId(Playlist p, Track t, int pos)
	{
		this.playlist = p;
		this.track = t;
		this.position = pos;
	}
	
	public ContentId()
	{
		
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	
	

}
