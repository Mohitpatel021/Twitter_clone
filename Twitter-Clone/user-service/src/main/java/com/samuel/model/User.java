package com.samuel.model;

import com.samuel.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Builder
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "email", columnNames = "email"),
		@UniqueConstraint(name = "phone", columnNames = "phone") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 100, allocationSize = 1)
	@Column(name = "userId", nullable = false)
	private Long userId;

	@Column(name = "fullname", nullable = false)
	private String fullname;

	@Column(name = "bio")
	private String bio;

	@Column(name = "username")
	private String username;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email", unique = true)
	@Email(message = "INVALID EMAIL FORMAT")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "gender")
	private String gender;

	@Column(name = "website")
	private String website;

	@Column(name = "location")
	private String location;

	@Column(name = "profile_image")
	private String profile_image;

	@Column(name = "banner_image")
	private String banner_image;

	@Column(name = "country")
	private String country;

	@Column(name = "country_code")
	private String countryCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role = Role.USER;

	@Column(name = "language")
	private String language;

	@Column(name = "verified")
	private boolean verified = false;

	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	@Column(name = "updated_at")
	private LocalDateTime UpdatedAt;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserActivity userActivities;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserStatistics userStatistics;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserConfirmation userConfirmation;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserMetadata userMetadata;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ConfirmationToken confirmationToken;

	public User(long userId, String email, String fullname, LocalDateTime registrationDate, String password,
			Role role) {
		this.userId = userId;
		this.email = email;
		this.fullname = fullname;
		this.registrationDate = registrationDate;
		this.password = password;
		this.role = role;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		User user = (User) o;
		return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getBanner_image() {
		return banner_image;
	}

	public void setBanner_image(String banner_image) {
		this.banner_image = banner_image;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDateTime getUpdatedAt() {
		return UpdatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		UpdatedAt = updatedAt;
	}

	public UserActivity getUserActivities() {
		return userActivities;
	}

	public void setUserActivities(UserActivity userActivities) {
		this.userActivities = userActivities;
	}

	public UserStatistics getUserStatistics() {
		return userStatistics;
	}

	public void setUserStatistics(UserStatistics userStatistics) {
		this.userStatistics = userStatistics;
	}

	public UserConfirmation getUserConfirmation() {
		return userConfirmation;
	}

	public void setUserConfirmation(UserConfirmation userConfirmation) {
		this.userConfirmation = userConfirmation;
	}

	public UserMetadata getUserMetadata() {
		return userMetadata;
	}

	public void setUserMetadata(UserMetadata userMetadata) {
		this.userMetadata = userMetadata;
	}

	public ConfirmationToken getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(ConfirmationToken confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

}
