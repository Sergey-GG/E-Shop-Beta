package vntu.fcsa.gonchar.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vntu.fcsa.gonchar.dao.DAOChanger;
import vntu.fcsa.gonchar.dao.MilkProductDAO;
import vntu.fcsa.gonchar.model.MilkProduct;

@Controller
@RequestMapping("/milkProducts")
public class MilkProductsController {
    private final DAOChanger milkProductDAO;

    @Autowired
    private MilkProductsController(MilkProductDAO milkProductDAO) {
        this.milkProductDAO = milkProductDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("milkProducts", milkProductDAO.index());
        return "milkProducts/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("milkProduct", milkProductDAO.show(id));
        return "milkProducts/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("milkProduct") MilkProduct milkProduct) {
        return "milkProducts/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("milkProduct") @Valid MilkProduct milkProduct,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "milkProducts/new";

        milkProductDAO.save(milkProduct);
        return "redirect:/milkProducts";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("milkProduct", milkProductDAO.show(id));
        return "milkProducts/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("milkProduct") @Valid MilkProduct milkProduct, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "milkProducts/edit";

        milkProductDAO.update(id, milkProduct);
        return "redirect:/milkProducts";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        milkProductDAO.delete(id);
        return "redirect:/milkProducts";
    }

    @GetMapping("/{id}/buy")
    public String buy(Model model, @PathVariable("id") int id) {
        model.addAttribute("milkProduct", milkProductDAO.show(id));
        return "milkProducts/buy";
    }

    @PutMapping("/{id}")
    public String updateBuy(@ModelAttribute("milkProduct") @Valid MilkProduct cellMilkProduct, BindingResult bindingResult,
                            @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "milkProducts/buy";

        milkProductDAO.buy(id, cellMilkProduct);
        return check(cellMilkProduct, id);
    }

    @GetMapping("/{id}/check")
    public String check(@Valid MilkProduct model, @PathVariable("id") int id) {
        return "milkProducts/check";
    }
}
