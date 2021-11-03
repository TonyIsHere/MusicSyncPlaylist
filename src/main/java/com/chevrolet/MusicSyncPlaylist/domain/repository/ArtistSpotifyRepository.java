package com.chevrolet.MusicSyncPlaylist.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chevrolet.MusicSyncPlaylist.domain.ArtistSpotify;

public interface ArtistSpotifyRepository extends CrudRepository<ArtistSpotify, Integer> {
	List<ArtistSpotify> findBySpotify(String spo);
}
