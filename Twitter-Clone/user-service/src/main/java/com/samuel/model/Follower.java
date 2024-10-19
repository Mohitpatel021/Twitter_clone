package com.samuel.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Entity
@Table(name = "follower")
public class Follower {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "follower_id")
	private Long follower;

	@Column(name = "following_id")
	private Long following;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFollower() {
		return follower;
	}

	public void setFollower(Long follower) {
		this.follower = follower;
	}

	public Long getFollowing() {
		return following;
	}

	public void setFollowing(Long following) {
		this.following = following;
	}

}
