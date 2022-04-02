package com.example.foodieapp.order;


import com.example.foodieapp.iteam.Item;
import com.example.foodieapp.iteam.ItemRepository;
import com.example.foodieapp.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

@Controller
public class OrderController {

    private ClientOrder clientOrder;
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;

    @Autowired

    public OrderController(ClientOrder clientOrder, ItemRepository itemRepository) {
        this.clientOrder = clientOrder;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/zamowienie/dodaj")
    public String addItemToOrder(@RequestParam Long itemId, Model model){
        Optional<Item> item = itemRepository.findById(itemId);
        item.ifPresent(clientOrder::add);
        if(item.isPresent()) {
            model.addAttribute("message", new Message("Dodano", "Do zamówienia dodano: " + item.get().getName()));
        } else {
            model.addAttribute("message", new Message("Nie dodano", "Porano błędne id z menu: " + itemId));
        }
        return "message";
    }

    @GetMapping("/zamowienie")
    public String getCurrentOrder(Model model) {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", clientOrder
                .getOrder()
                .getItems().stream()
                .mapToDouble(Item::getPrice)
                .sum());
        return "order";
    }

    @PostMapping("/zamowienie/realizuj")
    public String proceedOrder(@RequestParam String address, @RequestParam String telephone, Model model) {
        Order order = clientOrder.getOrder();
        order.setAddress(address);
        order.setTelephone(telephone);
        orderRepository.save(order);
        clientOrder.clear();
        model.addAttribute("message", new Message("Dziękujemy", "Zamówienie przekazane do realizacji"));
        return "message";
    }
}

