package chien.demo.shopdemo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** The type Cart dto. */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {
  private int id;

  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private CustomerDto customer;

  private List<CartDetailDto> cartDetails;
}
