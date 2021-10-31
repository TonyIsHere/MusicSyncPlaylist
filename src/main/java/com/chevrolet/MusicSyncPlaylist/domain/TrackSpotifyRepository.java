package com.chevrolet.MusicSyncPlaylist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TrackSpotifyRepository extends CrudRepository<TrackSpotify, Integer>{
	//Name based on name of the PROPERTY (id keyword is not allowed)
	List<TrackSpotify> findBySpotify(String spo);
}