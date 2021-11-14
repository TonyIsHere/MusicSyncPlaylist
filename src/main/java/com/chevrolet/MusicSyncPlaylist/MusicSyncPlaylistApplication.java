package com.chevrolet.MusicSyncPlaylist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.chevrolet.MusicSyncPlaylist.domain.repository.UserRepository;
import com.chevrolet.MusicSyncPlaylist.domain.Roles;
import com.chevrolet.MusicSyncPlaylist.domain.User;


@SpringBootApplication
public class MusicSyncPlaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicSyncPlaylistApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository urepository) {
		return (args) -> {	
			// Create users: admin/admin user/user
			
		/*	User user1 = new User("user","user@gmail.com",
			"$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", Roles.USER);
			User user2 = new User("admin","admin@gmail.com",
			"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", Roles.ADMIN);
			urepository.save(user1);
			urepository.save(user2);*/
		};
		}

}
