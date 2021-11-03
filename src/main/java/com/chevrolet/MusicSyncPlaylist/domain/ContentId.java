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


	

}
