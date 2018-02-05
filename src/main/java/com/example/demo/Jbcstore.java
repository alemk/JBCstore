package com.example.demo;

import com.example.demo.repository.CleaningitemRepository;
import com.example.demo.repository.CosmeticRepository;
import com.example.demo.repository.Snackrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller

public class Jbcstore {
 @Autowired
    CleaningitemRepository cleaningitemrepo;
 @Autowired
    CosmeticRepository cosmeticrepo;
 @Autowired
    Snackrepository snackrepo;

    @RequestMapping("/")
 public String navigator(){
     return "navigation";
 }

 @RequestMapping("/addcosmetics")
    public String addcosmetics( Model model){
     model.addAttribute("cosmo", new Cosmetics());
     return "addcosmetics";
 }

 @PostMapping("/addcosmetics")
    public String addcosmetics(@Valid Cosmetics cosmo, BindingResult result){
     if(result.hasErrors()){
         return "addcosmetics";
     }
     cosmeticrepo.save(cosmo);
     return "redirect:/";
 }
 @RequestMapping("/addsnacks")
public String addsnacks(Model model){
     model.addAttribute("snack", new Snack());
     return "addsnacks";

     }
     @PostMapping("addsnacks")
    public String addsnacks(@Valid Snack snack, BindingResult result){
     if(result.hasErrors()){
         return "addsnacks";
     }
     snackrepo.save(snack);
     return "redirect:/";
     }
     @RequestMapping("addcleaningitem")
    public String addcleaningitem(Model model){
     model.addAttribute("cleaningitem", new CleaningItem());
     return "addcleaningitem";
     }
     @PostMapping("addcleaningitem")
    public String addcleaningitem(@Valid CleaningItem cleaningitem, BindingResult result){
     if(result.hasErrors()){
         return "addcleaningitem";
     }
     cleaningitemrepo.save(cleaningitem);
     return "redirect:/";
     }

   /* @RequestMapping("detail/{id}")
    public String showreddit(@PathVariable("id") long id, Model model){
        model.addAttribute("reddit", redditRepository.findOne(id));
        return "showreddit";*/


     @RequestMapping("inventory")
     public String inventory(Model model){
     long numberofsnacks = 0;
     long numberofcosmetics=0;
     long numberofcleaningitem=0;

     numberofsnacks = snackrepo.count();
     numberofcosmetics = cosmeticrepo.count();
     numberofcleaningitem = cleaningitemrepo.count();

     model.addAttribute("numofcosmetics",numberofcosmetics);
     model.addAttribute("numofcleaningitem", numberofcleaningitem);
     model.addAttribute("numofsnack", numberofsnacks);

        return "displayinventory";
     }

    @RequestMapping("revenue")
    public String revenue(Model model){


        double snacktotalrevenue = 0;
        float cosmeticsrevenue = 0;
        float cleaningitemrevenue = 0;

        Iterable<Snack> snacks = snackrepo.findAll();
        for(Snack sn: snacks){
            snacktotalrevenue += sn.getPrice();
        }

        model.addAttribute("snackrevenue", snacktotalrevenue);

        Iterable<CleaningItem> cleanitem = cleaningitemrepo.findAll();
        for (CleaningItem ci: cleanitem){
            cleaningitemrevenue += ci.getPrice();
        }
        model.addAttribute("cleaningrevenue",cleaningitemrevenue);

        Iterable<Cosmetics> cosmetics = cosmeticrepo.findAll();
        for (Cosmetics cos : cosmetics){
            cosmeticsrevenue += cos.getPrice();
        }
        model.addAttribute("cosmeticsrevenue", cosmeticsrevenue);

        /*Iterable<String> findpriceById(@Param("id") Long id);
        for(int i=0; i<=snackrepo.count(); i++ )
        {

            snacktotalrevenue = snacks.forEach(long price, @id =i );
        }*/





        return "revenue";
    }

}

