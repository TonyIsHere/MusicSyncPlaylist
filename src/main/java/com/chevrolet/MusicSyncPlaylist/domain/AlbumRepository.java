package com.chevrolet.MusicSyncPlaylist.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
	
	@Query(value ="INSERT INTO album_spotify(`alb_spo_id`, `alb_id`) VALUES (?2,?1)",nativeQuery = true)
	void insertNewSpotify(int idTrack,String spotify);

}
