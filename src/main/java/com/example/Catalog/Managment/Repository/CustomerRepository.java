package com.example.Catalog.Managment.Repository;

import com.example.Catalog.Managment.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer>
{

}
