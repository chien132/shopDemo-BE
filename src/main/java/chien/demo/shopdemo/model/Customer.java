package chien.demo.shopdemo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "customers")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    private Set<Order> orders;

    private String username;
    private String password;
    private boolean type;
}
