package chien.demo.shopdemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Customer. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "customers",
    uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull @NotBlank private String username;

  @NotNull @NotBlank private String password;

  @NotNull private boolean type;
}
