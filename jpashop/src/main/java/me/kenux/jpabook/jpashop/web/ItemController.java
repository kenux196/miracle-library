package me.kenux.jpabook.jpashop.web;

import lombok.RequiredArgsConstructor;
import me.kenux.jpabook.jpashop.domain.Book;
import me.kenux.jpabook.jpashop.domain.Item;
import me.kenux.jpabook.jpashop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

//    @PostMapping("/items/{itemId}/edit")
    public String updateItemV1(@ModelAttribute("form") BookForm form) {
        // 준영속 상태의 엔티티를 가지고 병합을 하고 있다.
        // 이 경우에는 엔티티의 변경된 부부만 아니라 전체가 변경된다.
        // 필드 중에 null로 채워지면  null 자체가 저장이 된다.
        // 실무에서는 변경 감지를 사용하는 것이 좋다.
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItemV2(@ModelAttribute("form") BookForm form) {
        // 변경 감지를 사용한 버전
        itemService.updateItem(form);
        return "redirect:/items";
    }
}
