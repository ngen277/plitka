package plitkashop.web.rest;

import plitkashop.domain.Admins;
import plitkashop.repository.AdminsRepository;
import plitkashop.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link plitkashop.domain.Admins}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdminsResource {

    private final Logger log = LoggerFactory.getLogger(AdminsResource.class);

    private static final String ENTITY_NAME = "admins";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminsRepository adminsRepository;

    public AdminsResource(AdminsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
    }

    /**
     * {@code POST  /admins} : Create a new admins.
     *
     * @param admins the admins to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new admins, or with status {@code 400 (Bad Request)} if the admins has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admins")
    public ResponseEntity<Admins> createAdmins(@RequestBody Admins admins) throws URISyntaxException {
        log.debug("REST request to save Admins : {}", admins);
        if (admins.getId() != null) {
            throw new BadRequestAlertException("A new admins cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Admins result = adminsRepository.save(admins);
        return ResponseEntity.created(new URI("/api/admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admins} : Updates an existing admins.
     *
     * @param admins the admins to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated admins,
     * or with status {@code 400 (Bad Request)} if the admins is not valid,
     * or with status {@code 500 (Internal Server Error)} if the admins couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admins")
    public ResponseEntity<Admins> updateAdmins(@RequestBody Admins admins) throws URISyntaxException {
        log.debug("REST request to update Admins : {}", admins);
        if (admins.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Admins result = adminsRepository.save(admins);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, admins.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /admins} : get all the admins.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of admins in body.
     */
    @GetMapping("/admins")
    public List<Admins> getAllAdmins() {
        log.debug("REST request to get all Admins");
        return adminsRepository.findAll();
    }

    /**
     * {@code GET  /admins/:id} : get the "id" admins.
     *
     * @param id the id of the admins to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the admins, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admins/{id}")
    public ResponseEntity<Admins> getAdmins(@PathVariable Long id) {
        log.debug("REST request to get Admins : {}", id);
        Optional<Admins> admins = adminsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(admins);
    }

    /**
     * {@code DELETE  /admins/:id} : delete the "id" admins.
     *
     * @param id the id of the admins to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmins(@PathVariable Long id) {
        log.debug("REST request to delete Admins : {}", id);
        adminsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
