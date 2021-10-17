package vntu.fcsa.gonchar.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.dao.DAOChanger;
import vntu.fcsa.gonchar.dao.MeatProductDAO;
import vntu.fcsa.gonchar.model.MeatProduct;

@Controller
@RequestMapping("/meatProducts")
public class MeatProductsController {
    private final DAOChanger meatProductDAO;

    @Autowired
    private MeatProductsController(MeatProductDAO meatProductDAO) {
        this.meatProductDAO = meatProductDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("meatProducts", meatProductDAO.index());
        return "meatProducts/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("meatProduct", meatProductDAO.show(id));
        return "meatProducts/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("meatProduct") MeatProduct meatProduct) {
        return "meatProducts/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("meatProduct") @Valid MeatProduct meatProduct,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "meatProducts/new";

        meatProductDAO.save(meatProduct);
        return "redirect:/meatProducts";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("meatProduct", meatProductDAO.show(id));
        return "meatProducts/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("meatProduct") @Valid MeatProduct meatProduct, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "meatProducts/edit";

        meatProductDAO.update(id, meatProduct);
        return "redirect:/meatProducts";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        meatProductDAO.delete(id);
        return "redirect:/meatProducts";
    }

    @GetMapping("/{id}/buy")
    public String buy(Model model, @PathVariable("id") int id) {
        model.addAttribute("meatProduct", meatProductDAO.show(id));
        return "meatProducts/buy";
    }

    @PutMapping("/{id}")
    public String updateBuy(@ModelAttribute("meatProduct") @Valid MeatProduct cellMeatProduct, BindingResult bindingResult,
                            @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "meatProducts/buy";

        meatProductDAO.buy(id, cellMeatProduct);
        return check(cellMeatProduct, id);
    }

    @GetMapping("/{id}/check")
    public String check(@Valid MeatProduct model, @PathVariable("id") int id) {
        return "meatProducts/check";
    }
}
