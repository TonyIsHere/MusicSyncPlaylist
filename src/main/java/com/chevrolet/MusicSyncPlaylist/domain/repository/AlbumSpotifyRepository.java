package com.chevrolet.MusicSyncPlaylist.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chevrolet.MusicSyncPlaylist.domain.AlbumSpotify;

public interface AlbumSpotifyRepository extends CrudRepository<AlbumSpotify, Integer> {
	List<AlbumSpotify> findBySpotify(String spo);
}
