package com.chevrolet.MusicSyncPlaylist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ArtistSpotifyRepository extends CrudRepository<ArtistSpotify, Integer> {
	List<ArtistSpotify> findBySpotify(String spo);
}
