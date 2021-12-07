package com.videoco.eecs3311.project;

import java.util.ArrayList;

public class MovieInfo {
	private ArrayList<String> actors;
	private  ArrayList<String> directors;
	private  String description;
	private  String releaseYear;
	private Genre genre;
	private ArrayList<Integer> ratings;
	public MovieInfo(ArrayList<String> actors, ArrayList<String> directors, String description, String releaseYear,
			Genre genre, ArrayList<Integer> ratings) {
		super();
		this.actors = actors;
		this.directors = directors;
		this.description = description;
		this.releaseYear = releaseYear;
		this.genre = genre;
		this.ratings = ratings;
	}
	
	public MovieInfo() {
		
	}
	
	public boolean addActor(String s) {
		if(actors.contains(s)) {
			return false;
		}
		actors.add(s);
		return true;
	}
	
	public boolean removeActor(int index) {
		if(index<0 || index>=actors.size()) {
			return false;
		}
		actors.remove(index);
		return true;
	}
	public boolean addDirector(String s) {
		if(directors.contains(s)) {
			return false;
		}
		directors.add(s);
		return true;
	}
	
	public boolean removeDirector(int index) {
		if(index<0 || index>=directors.size()) {
			return false;
		}
		directors.remove(index);
		return true;
	}
	
	
	public ArrayList<String> getActors() {
		return actors;
	}
	public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}
	public ArrayList<String> getDirectors() {
		return directors;
	}
	public void setDirectors(ArrayList<String> directors) {
		this.directors = directors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public ArrayList<Integer> getRatings() {
		return ratings;
	}

	public void setRatings(ArrayList<Integer> ratings) {
		this.ratings = ratings;
	}
	
	public void addRating(int rating) {
		ratings.add(rating);
	}
	
	public int averageRating() {
		int sum=0;
		
		for(Integer rating : ratings) {
			sum=sum+rating;
		}
		if(sum>0) {
			return sum/ratings.size();
		}else {
			return 0;
		}
	}

	@Override
	public String toString() {
		String actorsS="";
		String directorsS="";
		String ratingsS="";
		for(String actor: actors) {
			actorsS=actorsS+  actor+",";
		}
		actorsS=actorsS.substring(0,actorsS.length()-1);
		for(String director: directors) {
			directorsS=directorsS+ " " + director;
		}
		directorsS=directorsS.substring(0,directorsS.length()-1);
		for(String rating: directors) {
			ratingsS=ratingsS+ " " + rating;
		}
		ratingsS=ratingsS.substring(0,ratingsS.length()-1);


		return "MovieInfo [actors=" + actors + ", directors=" + directors + ", description=" + description
				+ ", ReleaseYear=" + releaseYear + ", genre=" + genre + ", ratings=" + ratings + "]";
	}
	
	
	@Override 
	public MovieInfo clone() {
		ArrayList<String> newActors= new ArrayList<String>();
		ArrayList<String> newDirectors= new ArrayList<String>();
		ArrayList<Integer> newRatings= new ArrayList<Integer>();
		for(String actor: actors) {
			newActors.add(actor);
		}
		for(String director: directors) {
			newDirectors.add(director);
		}
		for(Integer rating: ratings) {
			newRatings.add(rating);
		}
		
		MovieInfo newInfo= new MovieInfo(newActors,newDirectors, description,releaseYear,genre,newRatings);
		return newInfo;



	}
	
	
	
	
	

}
