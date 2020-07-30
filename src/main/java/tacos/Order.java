package tacos;

import lombok.Data;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Order {

    private Long id;
    private Date placedAt;

    @NotNull(message="Name is required")
    private String name;

    @NotNull(message="Street is required")
    private String street;

    @NotNull(message="City is required")
    private String city;

    @NotNull(message="State is required")
    private String state;

    @NotNull(message="Zip code is required")
    private String zip;

    @Pattern(regexp="[0-9]{16}", message="Enter CC # with no spaces")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Format must be MM/YY")
    private String ccExpiration;

    @Pattern(regexp="[0-9]{3}", message="Invalid CCV")
    private String ccCVV;

}
