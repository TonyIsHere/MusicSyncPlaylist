package com.chevrolet.MusicSyncPlaylist.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chevrolet.MusicSyncPlaylist.domain.Track;


@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>{
	@Query(value = "SELECT * FROM Track left JOIN track_artist ON Track.tra_id = Track_Artist.tra_id left JOIN artist ON Artist.art_id = Track_Artist.art_id WHERE tra_name = ?1 AND art_name = ?2",nativeQuery = true)
	List<Track> FindByDescriptionQuery(String trackname,String artName);
	
	@Query(value ="INSERT INTO track_spotify(`tra_spo_preview`, `tra_spo_id`, `tra_id`) VALUES (?3,?2,?1)",nativeQuery = true)
	void insertDocumentByTaskId(int idTrack,String spotify,String preview);
}

