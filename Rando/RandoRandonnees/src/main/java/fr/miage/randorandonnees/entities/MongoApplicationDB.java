/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.entities;

/**
 *
 * @author Maha
 */


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;



@SpringBootApplication
public class MongoApplicationDB implements CommandLineRunner {
	
	@Autowired
	MongoOperations mongoOperations;
	
	public static void main(String[] args) {
		SpringApplication.run(MongoApplicationDB.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Randonnee r = new Randonnee();
		// save
		mongoOperations.save(r);
		
		// build mongo query
		Query query = new Query(Criteria.where("name").is("document_1"));
		
		// search operation
		Randonnee object = (Randonnee) mongoOperations.findOne(query, Randonnee.class);
		System.out.println("##################  After: save document 1");
		System.out.println(object.toString());
		
		//update operation
		mongoOperations.updateFirst(query, Update.update("description", "update description"), Randonnee.class);
		
		// search a updated Simple Document
		Randonnee updatedObject = (Randonnee) mongoOperations.findOne(query, Randonnee.class);
		System.out.println("##################  After: update document 1");
		System.out.println(updatedObject.toString());
		
		// save other
		Randonnee other = new Randonnee();
		mongoOperations.save(other);
				
		// find all simple document in DB
		List<Randonnee> objLst =  mongoOperations.findAll(Randonnee.class);
		System.out.println("##################  After: save other");
		System.out.println(objLst.size());
		
		// delete a simple document in Db
		mongoOperations.remove(query, Randonnee.class);
		
		// find all simple document in DB
		objLst =  mongoOperations.findAll(Randonnee.class);
		System.out.println("##################  After: delete a simple document 1");
		System.out.println(objLst.size());
	}
}
