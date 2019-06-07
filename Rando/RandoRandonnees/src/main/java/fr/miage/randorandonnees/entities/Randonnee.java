/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randorandonnees.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Maha
 */
@Document(collection = "randonnees")
public class Randonnee implements Serializable{
    @Id
    @GeneratedValue
    private String idR;
    
    private String titreR;
    private Integer niveauCible;
    private Date dateRando;
    private Long idTeamLeader ;
    private String lieuR;
    private String distanceR;
    private Long coutFixeR;
    private Long coutVariableR;
    private Boolean sondageCloture;
    private Boolean inscriCloture;
    private List<Long> listInscris;
    private  Integer nbPlaces = 2;
    
    private String date1;
    private String date2;
    private String date3;

    public Integer getNbPlaces() {
        return nbPlaces;
    }

    //pour chaque date (key), on aura la list des id Membres ayant voté pour cette date
    private HashMap<String,ArrayList<Long>> votesR;

    public Randonnee() {
    }

    public Randonnee(String titreR, Integer niveauCible, Long idTeamLeader, String lieuR, String distanceR, Long coutFixeR, Long coutVariableR, String date1, String date2, String date3) {
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
        
        //params par défaut
        this.inscriCloture = false;
        this.sondageCloture = false;
        this.listInscris = new ArrayList<Long>();
       // this.votesR = new HashMap<String,ArrayList<Long>>();
        this.votesR = new HashMap<String,ArrayList<Long>>() {{
        put(date1,new ArrayList<Long>());
        put(date2,new ArrayList<Long>());
        put(date3,new ArrayList<Long>());
    }};
        
    }
    public String getId() {
        return this.idR;
    }

    public void setIdR(String idR) {
        this.idR = idR;
    }

    public void setTitreR(String titreR) {
        this.titreR = titreR;
    }

    public void setNiveauCible(Integer niveauCible) {
        this.niveauCible = niveauCible;
    }

    public void setDateRando(Date dateRando) {
        this.dateRando = dateRando;
    }

    public void setIdTeamLeader(Long idTeamLeader) {
        this.idTeamLeader = idTeamLeader;
    }

    public void setLieuR(String lieuR) {
        this.lieuR = lieuR;
    }

    public void setDistanceR(String distanceR) {
        this.distanceR = distanceR;
    }

    public void setCoutFixeR(Long coutFixeR) {
        this.coutFixeR = coutFixeR;
    }

    public void setCoutVariableR(Long coutVariableR) {
        this.coutVariableR = coutVariableR;
    }

    public void setSondageCloture(Boolean sondageCloture) {
        this.sondageCloture = sondageCloture;
    }

    public void setInscriCloture(Boolean inscriCloture) {
        this.inscriCloture = inscriCloture;
    }

    public void setListInscris(List<Long> listInscris) {
        this.listInscris = listInscris;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public void setVotesR(HashMap<String, ArrayList<Long>> votesR) {
        this.votesR = votesR;
    }

    public String getIdR() {
        return idR;
    }

    public String getTitreR() {
        return titreR;
    }

    public Integer getNiveauCible() {
        return niveauCible;
    }

    public Date getDateRando() {
        return dateRando;
    }

    public Long getIdTeamLeader() {
        return idTeamLeader;
    }

    public String getLieuR() {
        return lieuR;
    }

    public String getDistanceR() {
        return distanceR;
    }

    public Long getCoutFixeR() {
        return coutFixeR;
    }

    public Long getCoutVariableR() {
        return coutVariableR;
    }

    public Boolean getSondageCloture() {
        return sondageCloture;
    }

    public Boolean getInscriCloture() {
        return inscriCloture;
    }

    public List<Long> getListInscris() {
        return listInscris;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getDate3() {
        return date3;
    }

    public HashMap<String, ArrayList<Long>> getVotesR() {
        return votesR;
    }
    
    public void ajouterMembreInscri(Long idMembre){
        this.listInscris.add(idMembre);
    }
    
    public Boolean isOverBooked(){
        return this.listInscris.size() == this.nbPlaces ;
    }
    
    public String toString(){                
        
        return "{" + "\"titre\" :\"" + this.titreR + "\", "
                + "\"niveauCible\" :\"" + this.niveauCible + "\", " 
                + "\"id\" :\"" + this.idR + "\", " 
                + "\"idTL\" :\"" + this.idTeamLeader + "\", "
                + "\"lieu\" :\"" + this.lieuR + "\", "
                + "\"distance\" :\"" + this.distanceR + "\", "
                + "\"coutFixe\" :\"" + this.coutFixeR + "\", "
                + "\"coutVariable\" :\"" + this.coutVariableR + "\", "
                + "\"sondageCloture\" :\"" + this.sondageCloture + "\", "
                + "\"inscriCloture\" :\"" + this.inscriCloture + "\", "
                + "\"date1\" :\"" + this.date1 + "\", " 
                + "\"date2\" :\"" + this.date2 + "\", " 
                + "\"date3\" :\"" + this.date3 + "\", " 
                //+ "\"listInscris\" :\"" + this.listInscris.toString() + "\", "
                + "\"nbPlaces\" :\"" + this.nbPlaces + "\" "
                //+ "\"votes\" :\"" + this.votesR.toString() + "\" "
                + "}"
                ;
    }
}
