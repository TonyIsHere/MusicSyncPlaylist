package com.chevrolet.MusicSyncPlaylist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AlbumSpotifyRepository extends CrudRepository<AlbumSpotify, Integer> {
	List<AlbumSpotify> findBySpotify(String spo);
}
