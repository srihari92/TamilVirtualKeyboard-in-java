package com.srihari92.tamilkeyboard.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
	final int INDEPENDENTCHARCODE=-1;
	final int CONSONENTCHARCODE=0;
	final int SPECIALCHARCODE=-2;
	Connection conn=null;
	public DBUtil(){
		conn=DBConnection.getConnection();
	}
	public void close(){
		DBConnection.closeConnection();
	}
	public void updateHit(int id){
		try {
			PreparedStatement ps=conn.prepareStatement("update tamil_words set hit=(hit+1) where id=?");
			ps.setInt(1, id);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<String> getIndependentCharacters(){
		List<String> list=new ArrayList<String>();
		Statement s = null;
		ResultSet r=null;
		try {
			s = conn.createStatement();
			r=s.executeQuery("select * from tamil_chars where ref_id="+INDEPENDENTCHARCODE);
			while(r.next()){
				list.add(r.getString("code"));
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public List<String> getConsonentCharacters(){
		List<String> list=new ArrayList<String>();
		Statement s = null;
		ResultSet r=null;
		try {
			s = conn.createStatement();
			r=s.executeQuery("select * from tamil_chars where ref_id="+CONSONENTCHARCODE);
			while(r.next()){
				list.add(r.getString("code")+","+r.getString("id"));
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public List<String> getSpecialCharacters(){
		List<String> list=new ArrayList<String>();
		Statement s = null;
		ResultSet r=null;
		try {
			s = conn.createStatement();
			r=s.executeQuery("select * from tamil_chars where ref_id="+SPECIALCHARCODE);
			while(r.next()){
				list.add(r.getString("code"));
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		return list;
	}
	public List<String> getDynamicCharacters(int id){
		List<String> list=new ArrayList<String>();
		Statement s = null;
		ResultSet r=null;
		try {
			s = conn.createStatement();
			r=s.executeQuery("select * from tamil_chars where id = "+id+" or ref_id="+id);
			while(r.next()){
			
				list.add(r.getString("code"));
			}
			list.add(0,list.get(list.size()-1));
			list.remove(list.size()-1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	public List<String> getPops(String word){
		List<String> list=new ArrayList<String>();
		Statement s = null;
		ResultSet r=null;
		try {
			s = conn.createStatement();
			r=s.executeQuery("select id,word from tamil_words where word like '"+word+"%' order by hit DESC,length(word) LIMIT 6");
			while(r.next()){
				list.add(r.getString("word")+","+r.getInt("id"));
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		if(list.size()>0)
			return list;
		else
			return null;
	}
}
