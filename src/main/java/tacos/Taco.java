package tacos;

import lombok.Data;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class Taco {

    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min=5, message="Name must be 5 characters long")
    private String name;

    @Size(min=1, message="You must have at least 1 ingredient selected")
    private List<String> ingredients;
}
