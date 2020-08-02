package tacos.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;

@Controller
//Requests mapping for design.html
@RequestMapping("/design")
//Specifies that any model objects like the order attribute should be kept in session and
//available across multiple requests
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private final TacoRepository designRepo;

    //Grab the autowired instances of IngredientRepo and TacoRepo
    @Autowired
    public DesignTacoController( IngredientRepository ingredientRepo,
                                 TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    // Handles GET requests for "design"
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        return "design";
    }

    private List<Ingredient> filterByType (List<Ingredient> ingredients, Type type){
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    /** Order is annotated with @ModelAttribute to indicate that it should come from the model and that Spring
    MVC shouldn't try to bind request parameters to it */
    /** @Valid is used to check to make sure submitted taco doesn't have errors */
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if(errors.hasErrors()) {
            return "design";
        }

        //Save taco to order and in designRepo
        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        // Save the taco design...  
        // We'll do this in chapter 3  
        return "redirect:/orders/current";
    }

    //Ensures that the model object will be created in the model
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    //Ensures that the taco object will be created in the model
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    }
