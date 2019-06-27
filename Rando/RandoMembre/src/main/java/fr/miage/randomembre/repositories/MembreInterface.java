/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.randomembre.repositories;

import fr.miage.randomembre.entities.Membre;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembreInterface extends CrudRepository<Membre,Long>{
    
    /**
     * cherche l'existance dans membre dans le repo ayant pour id mdp loginM et mdpM
     * @param loginM
     * @param mdpM
     * @return 
     */
    @Query("SELECT m FROM Membre m WHERE m.loginM = ?1 and m.mdpM = ?2")
    public Optional<Membre> findMembreByLoginMAndMdpM(String loginM, String mdpM);

    /**
     * cherche le membre ayant pour loginM le logingM en parametre dans le repo
     * @param loginM
     * @return 
     */
    public Optional<Membre> findByLoginM(String loginM);
    
}
