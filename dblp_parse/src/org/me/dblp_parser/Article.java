package org.me.dblp_parser;

import java.util.ArrayList;

public class Article {
     String mdate="";
     String key="";
     ArrayList<String> authors=new ArrayList<String>();
     String title;
     String pages="";
     int year;
     int volume=-1;
     String journal="";
     String number="";
     String ee="";
     String url="";
	
	public Article(String mdate, String key, ArrayList<String> authors,
			String title, String pages, int year, int volume, String journal,
			String number, String ee, String url) {
		super();
		this.mdate = mdate;
		this.key = key;
		this.authors = authors;
		this.title = title;
		this.pages = pages;
		this.year = year;
		this.volume = volume;
		this.journal = journal;
		this.number = number;
		this.ee = ee;
		this.url = url;
	}
	public Article() {
		super();
		this.mdate = mdate;
		this.key = key;
		this.authors = authors;
		this.title = title;
		this.pages = pages;
		this.year = year;
		this.volume = volume;
		this.journal = journal;
		this.number = number;
		this.ee = ee;
		this.url = url;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public ArrayList<String> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEe() {
		return ee;
	}
	public void setEe(String ee) {
		this.ee = ee;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Article [mdate=" + mdate + ", key=" + key + ", authors="
				+ authors + ", title=" + title + ", pages=" + pages + ", year="
				+ year + ", volume=" + volume + ", journal=" + journal
				+ ", number=" + number + ", ee=" + ee + ", url=" + url + "]";
	}
	
     
}
