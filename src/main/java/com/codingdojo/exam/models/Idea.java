package com.codingdojo.exam.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="ideaz")
public class Idea {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotEmpty(message="Idea name is required!")
	 private String title;
	 
	 @Column(updatable=false)
	 private Date createdAt;
	 private Date updatedAt;
	
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="creator_id")
	 private User creator;
	 
	   @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "users_ideas", 
	        joinColumns = @JoinColumn(name = "idea_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    private List<User> usersWholiked;
	   
	 
	 public Idea() {
		 
	 }
	 
	 
	 
	   public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public User getCreator() {
		return creator;
	}



	public void setCreator(User creator) {
		this.creator = creator;
	}



	public List<User> getUsersWholiked() {
		return usersWholiked;
	}



	public void setUsersWholiked(List<User> usersWholiked) {
		this.usersWholiked = usersWholiked;
	}



	@PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    }
}
