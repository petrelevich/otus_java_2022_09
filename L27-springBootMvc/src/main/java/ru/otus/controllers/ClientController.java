package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.domain.Client;
import ru.otus.services.ClientService;

import java.util.List;

@Controller
public class ClientController {

    private final String osData;
    private final String applicationYmlMessage;
    private final ClientService clientService;

    public ClientController(@Value("${app.client-list-page.msg:Тут может находиться ваша реклама}")
                                    String applicationYmlMessage,
                            @Value("OS: #{T(System).getProperty(\"os.name\")}, " +
                                    "JDK: #{T(System).getProperty(\"java.runtime.version\")}")
                                    String osData,
                            ClientService clientService) {
        this.applicationYmlMessage = applicationYmlMessage;
        this.osData = osData;
        this.clientService = clientService;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("osData", osData);
        model.addAttribute("applicationYmlMessage", applicationYmlMessage);
        return "clientsList";
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new Client());
        return "clientCreate";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(@ModelAttribute Client client) {
        clientService.save(client);
        return new RedirectView("/", true);
    }

}
