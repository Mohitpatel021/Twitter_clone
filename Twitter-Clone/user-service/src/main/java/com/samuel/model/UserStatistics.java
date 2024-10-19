package com.samuel.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Entity
@Table(name = "user_statistics")
public class UserStatistics {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "followers_count")
	private Long followersCount = 0L;

	@Column(name = "following_count")
	private Long followingCount = 0L;

	@Column(name = "tweet_count")
	private Long tweetCount = 0L;

	@Column(name = "likes_count")
	private Long likesCount = 0L;

	@Column(name = "favorite_count")
	private Long favoriteCount = 0L;

	@Column(name = "retweets_count")
	private Long retweetsCount = 0L;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}

	public Long getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(Long followingCount) {
		this.followingCount = followingCount;
	}

	public Long getTweetCount() {
		return tweetCount;
	}

	public void setTweetCount(Long tweetCount) {
		this.tweetCount = tweetCount;
	}

	public Long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Long likesCount) {
		this.likesCount = likesCount;
	}

	public Long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Long getRetweetsCount() {
		return retweetsCount;
	}

	public void setRetweetsCount(Long retweetsCount) {
		this.retweetsCount = retweetsCount;
	}

}
