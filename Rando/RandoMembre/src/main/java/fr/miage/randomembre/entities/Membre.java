 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Maha 
 */
@Entity
@Table(name="membres")
public class Membre implements Serializable{
    @Id
    @GeneratedValue
    private Long idM;

    private String nomM;
    private String prenomM;
    private String mailM;
    private String loginM;
    private String mdpM;
    private String adressM;
    private Integer licenceM;
    private Date anneeCertificat;
    private Date anneeCotisation;
    private Boolean isApte;
    private Boolean isTL;
    private Boolean isPresident;
    private Boolean isSecretaire;
    private Long cotisationM;
    private String ibanM;
    private Integer niveauM;
    
    @Column(name="association")
    private Long assotId;
        
    public Membre() {
        //params par défaut
        this.isApte = false;
        this.isPresident = false;
        this.isSecretaire = false;
        this.isTL = false;
    }

    public Membre(String nomM, String prenomM, String mailM, String loginM, String mdpM, String adressM, Integer licenceM, Date anneeCertificat, Date anneeCotisation, Long cotisationM, String ibanM, Integer niveauM) {
        this.nomM = nomM;
        this.prenomM = prenomM;
        this.mailM = mailM;
        this.loginM = loginM;
        this.mdpM = mdpM;
        this.adressM = adressM;
        this.licenceM = licenceM;
        this.anneeCertificat = anneeCertificat;
        this.anneeCotisation = anneeCotisation;
        this.cotisationM = cotisationM;
        this.ibanM = ibanM;
        this.niveauM = niveauM;
        
        //params par défaut
        this.isApte = false;
        this.isPresident = false;
        this.isSecretaire = false;
        this.isTL = false;
    }

    public Long getIdM() {
        return idM;
    }

    public String getNomM() {
        return nomM;
    }

    public String getPrenomM() {
        return prenomM;
    }

    public String getMailM() {
        return mailM;
    }

    public String getLoginM() {
        return loginM;
    }

    public String getMdpM() {
        return mdpM;
    }

    public String getAdressM() {
        return adressM;
    }

    public Integer getLicenceM() {
        return licenceM;
    }

    public Date getAnneeCertificat() {
        return anneeCertificat;
    }

    public Date getAnneeCotisation() {
        return anneeCotisation;
    }

    public Boolean getIsApte() {
        return isApte;
    }

    public Boolean getIsTL() {
        return isTL;
    }

    public Boolean getIsPresident() {
        return isPresident;
    }

    public Boolean getIsSecretaire() {
        return isSecretaire;
    }

    public Long getCotisationM() {
        return cotisationM;
    }

    public String getIbanM() {
        return ibanM;
    }

    public Integer getNiveauM() {
        return niveauM;
    }

    public void setIdM(Long idM) {
        this.idM = idM;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public void setPrenomM(String prenomM) {
        this.prenomM = prenomM;
    }

    public void setMailM(String mailM) {
        this.mailM = mailM;
    }

    public void setLoginM(String loginM) {
        this.loginM = loginM;
    }

    public void setMdpM(String mdpM) {
        this.mdpM = mdpM;
    }

    public void setAdressM(String adressM) {
        this.adressM = adressM;
    }

    public void setLicenceM(Integer licenceM) {
        this.licenceM = licenceM;
    }

    public void setAnneeCertificat(Date anneeCertificat) {
        this.anneeCertificat = anneeCertificat;
    }

    public void setAnneeCotisation(Date anneeCotisation) {
        this.anneeCotisation = anneeCotisation;
    }

    public void setIsApte(Boolean isApte) {
        this.isApte = isApte;
    }

    public void setIsTL(Boolean isTL) {
        this.isTL = isTL;
    }

    public void setIsPresident(Boolean isPresident) {
        this.isPresident = isPresident;
    }

    public void setIsSecretaire(Boolean isSecretaire) {
        this.isSecretaire = isSecretaire;
    }

    public void setCotisationM(Long cotisationM) {
        this.cotisationM = cotisationM;
    }

    public void setIbanM(String ibanM) {
        this.ibanM = ibanM;
    }

    public void setNiveauM(Integer niveauM) {
        this.niveauM = niveauM;
    }
    
    
}
