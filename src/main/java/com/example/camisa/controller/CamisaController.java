package com.example.camisa.controller;

import com.example.camisa.domain.Camisa;
import com.example.camisa.service.CamisaService;
import com.example.camisa.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Controller
public class CamisaController {

    private final CamisaService service;
    private final FileStorageService fileStorageService;

    public CamisaController(CamisaService service, FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String getComputadorHome(Model model, HttpServletResponse response){

        List<Camisa> camisa = service.findAll();
        model.addAttribute("camisa", camisa);

        Cookie cookie = new Cookie("visita","cookie-value");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);

        return "index";
    }

    @GetMapping("/cadastrar")
    public String doCadastrar(Model model){
        Camisa c = new Camisa();
        model.addAttribute("camisa", c);

        return "cadastrar";

    }

    @GetMapping("editar/{id}")
    public String getEditarComputador(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){

        Camisa camisa = service.findById(id);
        model.addAttribute("camisa", camisa);

        redirectAttributes.addAttribute("msg2", "Cadastro atualizado com sucesso");
        return "cadastrar";
    }

    @GetMapping("deletar/{id}")
    public String getDeletarComputador(@ModelAttribute Camisa c, Model model, @PathVariable Long id){
        System.out.println(c.getDescricao());

        Camisa camisa = service.findById(c.getId());

        camisa.setDescricao(c.getDescricao());
        camisa.setImagem(c.getImagem());
        camisa.setMarca(c.getMarca());
        camisa.setModelo(c.getModelo());
        camisa.setPreco(c.getPreco());

        camisa.setDeletd(false);

        service.update(camisa);

        List<Camisa> camisas = service.findAll();

        model.addAttribute("camisa", camisas);

        return "index";
    }

    @PostMapping("salvar")
    public String doSalvaComputador(@ModelAttribute @Valid Camisa c, Errors errors,
                                  @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (errors.hasErrors()){
            System.out.println(errors.getAllErrors().stream().toArray());
            return "produto/cadastrar";
        }else{
            /*
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getContentType());
			System.out.println(file.getSize());
             */

            c.setImagem(file.getOriginalFilename());
            service.update(c);
            fileStorageService.save(file);

            redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
            return "redirect:/";
        }
    }



    @GetMapping("/adicionarCarrinho/{id}")
    public String getAddCarrinho(Model model, @PathVariable Long id){

        Camisa camisa = service.findById(id);
        model.addAttribute("camisa", camisa);

        return "cadastrar";
    }

    @GetMapping("/vercarrinho")
    public String getVerCarrinho(Model model){
        return "vercarrinho";
    }

    @GetMapping("/addItemCarrinho")
    public void doAdicionarItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = 2;
        var camisa = service.findById((long) id);
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
        carrinhoCompras.setMaxAge(60 * 60 * 24);
        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;
        if (requestCookies != null) {
            for (var c : requestCookies) {
                achouCarrinho = true;
                carrinhoCompras = c;
                break;
            }
        }
        Camisa camisas = null;
        if (camisa != null){
            camisas = camisa;
            if (achouCarrinho == true){
                String value = carrinhoCompras.getValue();
                carrinhoCompras.setValue(value + camisas.getId() + "|");
            }else{
                carrinhoCompras.setValue(camisas.getId() + "|");
            }
        }else {
            response.addCookie(carrinhoCompras);
        }
        response.addCookie(carrinhoCompras);
    }
    @GetMapping("/visualizarCarrinho")
    public String visualizarCarrinho(HttpServletRequest request, Model model) throws ServletException, IOException {
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;
        if (requestCookies != null) {
            for (var c : requestCookies) {
                achouCarrinho = true;
                carrinhoCompras = c;
                break;
            }
        }
        Camisa camisa = null;
        var i = 0;
        ArrayList<Camisa> lista_camisas = new ArrayList();
        if(achouCarrinho == true) {
            StringTokenizer tokenizer = new StringTokenizer(carrinhoCompras.getValue(), "|");
            while (tokenizer.hasMoreTokens()) {
                camisa = service.findById((long) Integer.parseInt(tokenizer.nextToken()));
                lista_camisas.add(camisa);
            }
            model.addAttribute("camisa", lista_camisas);
            return "relacao";

        } else {
            return "redirect:/index";
        }
    }
    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpServletRequest request, HttpServletResponse response){
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
        response.addCookie(carrinhoCompras);
        return "redirect:/index";
    }
}
