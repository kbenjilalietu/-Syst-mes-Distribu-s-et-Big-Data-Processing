package org.sid.customerservice.repo;

import org.sid.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
// Spring Data REST pour explorer le web service RESTFUL qui permet de gérer les customers
//pour : http://localhost:8081/customers
@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
