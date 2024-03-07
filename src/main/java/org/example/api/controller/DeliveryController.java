package org.example.api.controller;

import org.example.api.dto.DeliveryRangeDTO;
import org.example.api.dto.StreetsDTO;
import org.example.api.dto.mapper.DeliveryMapper;
import org.example.business.DeliveryRangeService;
import org.example.business.OwnerService;
import org.example.business.RestaurantService;
import org.example.business.teryt.LoadDataService;
import org.example.domain.Owner;
import org.example.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class DeliveryController {

    @Autowired
    private LoadDataService loadDataService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DeliveryRangeService deliveryRangeService;
    @Autowired
    private DeliveryMapper deliveryMapper;

    @GetMapping("/owner/delivery")
    public String showMyPage(@RequestParam(required = false) String voi,
                             @RequestParam(required = false) String cou,
                             @RequestParam(required = false) String cit,
                             Model model,
                             Principal principal) {
        Map<String, Set<String>> voivodeships = loadDataService.loadWojPowFromCSV();
        Map<String, List<String>> countiesMap = loadDataService.loadPowGmiFromCSV();
        Map<String, List<String>> symbols = loadDataService.loadGmiSymFromCSV();

        if (voi != null && voivodeships.containsKey(voi)) {
            Set<String> counties = voivodeships.get(voi);
            model.addAttribute("counties", counties);
        }
        if (cou != null && countiesMap.containsKey(cou)) {
            List<String> cities = countiesMap.get(cou);
            model.addAttribute("cities", cities);
        }
        if (cit != null && symbols.containsKey(cit)) {
            String symbol = symbols.get(cit).get(0);
            List<StreetsDTO> streets = loadDataService.getStreetsForGmina(symbol);
            model.addAttribute("streets", streets);
        }
        String email = principal.getName();
        Owner owner = ownerService.findOwnerByUser(email);
        List<Restaurant> restaurants = restaurantService.findRestaurantsByOwnerId(owner.getOwnerId());
        model.addAttribute("cit", cit);
        model.addAttribute("restaurantsList", restaurants);
        model.addAttribute("voivodeships", voivodeships);
        return "delivery_range";
    }


    @PostMapping("/owner/delivery/addStreets")
    public String addStreets(@RequestParam("cit") String cit,
                             @RequestParam("restaurant") String restaurant,
                             @RequestParam("approvedNames") List<String> approvedNames,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        try {
            DeliveryRangeDTO deliveryRangeDTO = deliveryMapper.map(cit, restaurant, approvedNames);
            deliveryRangeService.addDeliveryPlaces(deliveryRangeDTO);
           return "success_register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Something gone wrong when saving delivery address");
        }
        return "error";
    }
}
