package com.chevrolet.MusicSyncPlaylist.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.chevrolet.MusicSyncPlaylist.domain.Playlist;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

}
