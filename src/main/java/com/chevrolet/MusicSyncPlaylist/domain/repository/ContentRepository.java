package com.chevrolet.MusicSyncPlaylist.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chevrolet.MusicSyncPlaylist.domain.ArtistSpotify;
import com.chevrolet.MusicSyncPlaylist.domain.Content;

public interface ContentRepository extends CrudRepository<Content, Integer> {
	List<Content> findByPrimaryKeyPlaylistId(int id);
}
