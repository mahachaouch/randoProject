/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Maha
 */
@Document(collection = "randonnees")
public class Randonnee implements Serializable{
    @Id
    private Integer idR;
    
    private String titreR;
    private Integer niveauCible;
    private Date dateRando;
    private Integer idTeamLeader ;
    private String lieuR;
    private String distanceR;
    private Long coutFixeR;
    private Long coutVariableR;
    private Boolean sondageCloture;
    private Boolean inscriCloture;
    private List<Integer> listInscris;
    
    private Date date1;
    private Date date2;
    private Date date3;
    
    //pour chaque date (key), on aura la list des id Membres ayant voté pour cette date
    private HashMap<Date,List<Integer>> votesR;

    public Randonnee() {
    }

    public Randonnee(String titreR, Integer niveauCible, Integer idTeamLeader, String lieuR, String distanceR, Long coutFixeR, Long coutVariableR, Date date1, Date date2, Date date3, HashMap<Date, List<Integer>> votesR) {
        this.titreR = titreR;
        this.niveauCible = niveauCible;
        this.idTeamLeader = idTeamLeader;
        this.lieuR = lieuR;
        this.distanceR = distanceR;
        this.coutFixeR = coutFixeR;
        this.coutVariableR = coutVariableR;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.votesR = votesR;
        
        //params par défaut
        this.inscriCloture = false;
        this.sondageCloture = false;
       // this.votesR = new HashMap<this.date1,new List(),>
    }
    
    
    
}
