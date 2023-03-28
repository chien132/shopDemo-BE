package chien.demo.shopdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/** CartDetail Entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartDetails")
public class CartDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cartId")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "itemId", nullable = false)
  //  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  //  @JsonIdentityReference(alwaysAsId = true)
  private Item item;

  private int quantity;

  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd")
  @Column(name = "dateAdded")
  private Date dateAdded;
}
