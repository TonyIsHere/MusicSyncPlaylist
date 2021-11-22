package com.chevrolet.MusicSyncPlaylist.web;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.chevrolet.MusicSyncPlaylist.domain.Album;
import com.chevrolet.MusicSyncPlaylist.domain.AlbumSpotify;
import com.chevrolet.MusicSyncPlaylist.domain.Artist;
import com.chevrolet.MusicSyncPlaylist.domain.ArtistSpotify;
import com.chevrolet.MusicSyncPlaylist.domain.Content;
import com.chevrolet.MusicSyncPlaylist.domain.ContentId;
import com.chevrolet.MusicSyncPlaylist.domain.Playlist;
import com.chevrolet.MusicSyncPlaylist.domain.Roles;
import com.chevrolet.MusicSyncPlaylist.domain.Track;
import com.chevrolet.MusicSyncPlaylist.domain.TrackSpotify;
import com.chevrolet.MusicSyncPlaylist.domain.User;
import com.chevrolet.MusicSyncPlaylist.domain.repository.AlbumRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.AlbumSpotifyRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.ArtistSpotifyRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.ContentRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.PlaylistRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.TrackRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.TrackSpotifyRepository;
import com.chevrolet.MusicSyncPlaylist.domain.repository.UserRepository;


@Controller
public class AppController {

	private static final Logger log = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private TrackSpotifyRepository tsrepository;

	@Autowired
	private ArtistSpotifyRepository asrepository;

	@Autowired
	private TrackRepository trepository;

	@Autowired
	private AlbumSpotifyRepository alsrepository;

	@Autowired
	private AlbumRepository alrepository;

	@Autowired
	private UserRepository ulrepository;

	@Autowired
	private PlaylistRepository plrepository;

	@Autowired
	private ContentRepository crepository;

	@Autowired
	private HttpSession session;

	//For hash psw//
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	//

	@PreAuthorize("hasAuthority('ADMIN')") //Importante before requestmapping
	@RequestMapping({ "/admin" })
	public String admin() {
		return "admin";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')") //Importante before requestmapping
	@RequestMapping({ "/admin/tracks" })
	public String admintracks( Model model) {
		model.addAttribute("tracks",trepository.findAll());
		return "admintrack";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')") //Importante before requestmapping
	@RequestMapping({ "/admin/albums" })
	public String adminalbum( Model model) {
		model.addAttribute("albums",alrepository.findAll());
		return "adminalbum";
	}
	
	
	@RequestMapping({ "/search" })
	public String search(@RequestParam(name = "q", required = false) String query, Model model) {

		if (query != null && query != "") {
			final String uri = "http://localhost:8080/api/search?q=" + query;

			RestTemplate restTemplate = new RestTemplate();
			List<Object> result = restTemplate.getForObject(uri, List.class);

			List<Track> track = new ArrayList<Track>();

			for (int i = 0; i < result.size(); i++) {
				Map data = (Map) result.get(i);
				String idSpotify = data.get("id").toString();
				List<TrackSpotify> find = tsrepository.findBySpotify(idSpotify);
				track.add(find.get(0));
			}
			model.addAttribute("search", track);
		}

		return "createPlaylist";
	}

	@RequestMapping({ "api/search" })
	public @ResponseBody Object search(@RequestParam(name = "q") String query) {
		RestTemplate restTemplate = new RestTemplate();
		String client_id = "3597042a166c45abb95b0ec082236f0a";
		String client_secret = "99f74c80b4cb48c587e7ddd5992a5eb6";
		String url = "https://accounts.spotify.com/api/token";

		// request body parameters
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "client_credentials");

		// Header
		String plainCreds = client_id + ":" + client_secret;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", " Basic " + base64Creds);

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body,
				headers);
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class); // first get
																										// access token
		Map account = response.getBody();

		String access_token = account.get("access_token").toString();

		HttpHeaders headerSearch = new HttpHeaders();
		headerSearch.add("Authorization", " Bearer " + access_token);

		String urlApi = "https://api.spotify.com";
		String type = "track";

		HttpEntity<String> entity = new HttpEntity<>("body", headerSearch);

		// do the search on api
		ResponseEntity<Map> responseSearch = restTemplate.exchange(
				urlApi + String.format("/v1/search?q=%s&type=%s", query, type), HttpMethod.GET, entity, Map.class);

		Map jsondata = (Map) responseSearch.getBody().get("tracks");

		// get result from spotify API
		List<Object> data = (List<Object>) jsondata.get("items");

		for (int i = 0; i < data.size(); i++) {
			checkItem((Map) data.get(i));
		}

		return (Object) data;
	}

	/**
	 * Check if a track exist on the database
	 * @param data
	 */
	private void checkItem(Map data) {

		// Track Spotify data
		String idTrack = data.get("id").toString();
		String previewUrl = data.get("preview_url") == null ? null : data.get("preview_url").toString();

		String logmessage = idTrack + " | ";
		// Track General data
		String nametrack = data.get("name").toString();
		Integer duration = (Integer) data.get("duration_ms");

		List<Map> dataArtist = (List) data.get("artists");

		// Album general data
		Map dataAlbum = (Map) data.get("album");
		String album_name = dataAlbum.get("name").toString();

		LocalDate release = null;
		String precision = dataAlbum.get("release_date_precision").toString();
		switch (precision) {
		case "day":
			release = LocalDate.parse(dataAlbum.get("release_date").toString());
			break;
		case "month":
			log.error("PRECISION OF MONTH --> " + idTrack);
			release = LocalDate.parse(dataAlbum.get("release_date").toString());
			break;
		case "year":
			String date = dataAlbum.get("release_date").toString() + "-01-01";
			release = LocalDate.parse(date);
			break;
		}

		List<Object> dataImg = (List<Object>) dataAlbum.get("images");
		Map dataImgDetail = (Map) dataImg.get(1);
		String urlPicture = dataImgDetail.get("url").toString();
		String type = dataAlbum.get("album_type").toString();

		// Album Spotity data
		String idAlbum = dataAlbum.get("id").toString();

		Map<String, Object> artistsOfAlbumId = new HashMap();
		List<Object> ArtistsOfAlbum = (List) dataAlbum.get("artists");
		for (Object item : ArtistsOfAlbum) {
			Map m = (Map) item;
			String id = (String) m.get("id");
			artistsOfAlbumId.put(id, item);
		}

		List<TrackSpotify> trackSpotify = tsrepository.findBySpotify(idTrack);

		if (trackSpotify.isEmpty()) // Not found in db Spotify
		{
			logmessage += "TrackSpotify exist No  | ";
			List<AlbumSpotify> albumSpotify = alsrepository.findBySpotify(idAlbum);
			if (albumSpotify.isEmpty()) // Album not found in spotify
			{
				logmessage += "AlbumSpotify exist No  | ";
				List<Artist> allArtistOfAlbumFromdB = new ArrayList<>();

				for (Map.Entry<String, Object> entry : artistsOfAlbumId.entrySet()) {
					Map dataOfArt = (Map) artistsOfAlbumId.get(entry.getKey());
					Artist a = checkArtistSpotify(dataOfArt);
					allArtistOfAlbumFromdB.add(a);
				}

				boolean existIndb = false;
				Album refAlbum = null;
				Track track = null;
				Set<Artist> artNotLinked = new HashSet<Artist>();

				for (Artist artist : allArtistOfAlbumFromdB) {

					Object[] albumByName = artist.getAlbums().stream().filter(x -> (x.getName() == album_name))
							.toArray();

					// Not working to use comparaison with contains --> Api have 2 album with same name (L'école du micro d'argent) and
					// (L'école Du Micro D'argent) but not same id
					/*
					 * if (albumByName.length == 0) { albumByName =
					 * artist.getAlbums().stream().filter(x -> (x.getName().contains(album_name)))
					 * .toArray(); }
					 */
					if (albumByName.length != 0) {
						existIndb = true;
						refAlbum = (Album) albumByName[0];
					} else {
						artNotLinked.add(artist);
					}
				}
				if (!existIndb) {
					logmessage += "Album exist No  | ";
					// Create Album and track
					refAlbum = new AlbumSpotify(album_name, release, urlPicture, type, idAlbum);

					for (Artist artist : artNotLinked) {
						// Create link between art and album
						artist.getAlbums().add(refAlbum);
					}

					alsrepository.save((AlbumSpotify) refAlbum);

					track = new TrackSpotify(nametrack, duration, idTrack, previewUrl);
					track.setAlbum(refAlbum);

					for (Map item : dataArtist) {
						Artist a = checkArtistSpotify(item);
						track.getArtists().add(a);
					}
					tsrepository.save((TrackSpotify) track);
					refAlbum.getTracks().add(track);
					alsrepository.save((AlbumSpotify) refAlbum);

				} else {
					// Check Track of album
					logmessage += "Album exist Yes | ";

					// Link to spotify
					alrepository.insertNewSpotify(refAlbum.getId(), idAlbum);

					Object[] trackByName = refAlbum.getTracks().stream().filter(x -> (x.getName() == nametrack))
							.toArray();
					if (trackByName.length == 0) {
						// create track linked to album
						logmessage += "Track exist No  | ";
						track = new TrackSpotify(nametrack, duration, idTrack, previewUrl);
						track.setAlbum(refAlbum);

						for (Map item : dataArtist) {
							Artist a = checkArtistSpotify(item);
							track.getArtists().add(a);
						}
						trepository.save(track);

					} else {
						// else exist create link spotify
						logmessage += "Track exist Yes | ";
						track = (Track) trackByName[0];
						trepository.insertDocumentByTaskId(track.getId(), idTrack, previewUrl);
					}

				}

			} else {
				logmessage += "AlbumSpotify exist Yes | ";
				Album a = albumSpotify.get(0);
				List<Track> tracksOfPlaylist = a.getTracks();
				Object[] trackofAlbum = tracksOfPlaylist.stream().filter(x -> (x.getName() == nametrack)).toArray();
				if (trackofAlbum.length == 0) {
					// Create Track linked to album
					logmessage += "Track exist No  | ";
					TrackSpotify track = new TrackSpotify(nametrack, duration, idTrack, previewUrl);
					track.setAlbum(a);

					for (Map item : dataArtist) {
						Artist art = checkArtistSpotify(item);
						track.getArtists().add(art);
					}

					tsrepository.save(track);

				} else {
					// create Spotify id
					logmessage += "Track exist Yes | ";
					Track t = (Track) trackofAlbum[0];
					trepository.insertDocumentByTaskId(t.getId(), idTrack, previewUrl);

				}

			}
		} else {
			logmessage += "TrackSpotify exist Yes | ";
		}
		log.warn(logmessage);
	}

	/**
	 * Check if artist exist in db
	 */
	private Artist checkArtistSpotify(Map data) {
		String nameart = data.get("name").toString();
		String idArt = data.get("id").toString();

		List<ArtistSpotify> artistSpotify = asrepository.findBySpotify(idArt);
		if (artistSpotify.isEmpty()) {
			ArtistSpotify newArt = new ArtistSpotify(nameart, null, null, idArt); // not exist
			asrepository.save(newArt);
			return newArt;
		}

		return artistSpotify.get(0);
	}

	@RequestMapping({ "/login" })
	public String login(Model model) {
		return "login";
	}

	@RequestMapping({ "/playlist/{id}" })
	public String login(@PathVariable("id") int playlistid, Model model) {
		Optional<Playlist> p = plrepository.findById(playlistid);
		if (p.isEmpty()) {
			return "redirect:/login";
		}

		model.addAttribute("playlist", p.get().getContents());
		model.addAttribute("name", p.get().getName());

		return "playlist";
	}

	@RequestMapping({ "/track/{id}" })
	public String track(@PathVariable("id") int trackid, Model model) {
		Optional<Track> t = trepository.findById(trackid);

		if (t.isEmpty()) {
			return "redirect:/login";
		}

		model.addAttribute("track", t.get());

		return "track";
	}
	
	@RequestMapping({ "/album/{id}" })
	public String album(@PathVariable("id") int albumid, Model model) {
		Optional<Album> a = alrepository.findById(albumid);

		if (a.isEmpty()) {
			return "redirect:/login";
		}

		model.addAttribute("album", a.get());
		model.addAttribute("contents", a.get().getTracks());

		return "album";
	}

	
	
	@RequestMapping({ "/profile" })
	public String profile(Model model) {
		Integer idUser = (Integer) session.getAttribute("id");
		Optional<User> u = ulrepository.findById(idUser);
		User user = u.get();

		if (u == null) {
			return "login";
		}

		model.addAttribute("playlist", user.getPlaylists());
		model.addAttribute("user", user);

		return "profile";
	}

	@RequestMapping(value = "api/playlist/{id}", method = { RequestMethod.DELETE })
	public String deletePlaylist(@PathVariable("id") Integer id) {

		List<Content> allRow = crepository.findByPrimaryKeyPlaylistId(id);
		for (Content content : allRow) { // delete first content
			crepository.delete(content);
		}

		plrepository.deleteById(id); // delete playlist
		return "redirect:/profile";
	}

	@RequestMapping(value = "api/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(@RequestParam int id, Integer[] allTrackId, String name) {
		Optional<User> u = ulrepository.findById(id); // get id
		Playlist p = new Playlist(name, u.get());

		p = plrepository.save(p); // save playlist before to get id

		for (int i = 0; i < allTrackId.length; i++) {
			Optional<Track> t = trepository.findById((allTrackId[i])); // get track by id

			ContentId idPk = new ContentId(p, t.get(), (i + 1)); // create the row item in the playlist
			Content row = new Content(idPk);

			crepository.save(row); // save content
		}

		// redirect to the new playlist
		return "redirect:/playlist/" + p.getId();
	}
	
	@RequestMapping({ "/new" })
	public String newAccount(@ModelAttribute User user) {
		return "new";
	}

	@PostMapping("/new")
	public String postAddAccount(@RequestParam String password, @RequestParam String confirm, @ModelAttribute User user,
			Model model) throws Exception {
		if (password.equals(confirm)) {
			user.setPasswordHash(passwordEncoder.encode(password));
			user.setRole(Roles.USER);
			ulrepository.save(user);
			return "redirect:/login";
			
		} else {
			model.addAttribute("error", "error");
			return "new";
		}

	}

}
