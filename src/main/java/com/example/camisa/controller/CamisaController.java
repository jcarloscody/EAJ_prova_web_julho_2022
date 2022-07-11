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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Controller
public class CamisaController {

    private final CamisaService service;
    private final FileStorageService fileStorageService;

    private static int contador = 0;

    private int contCarrinho = 0;


    public CamisaController(CamisaService service, FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String getCamisaHome(Model model, HttpServletResponse response){

       List<Camisa> camisa = service.findAll();
       List<Camisa> camisasUtil = new ArrayList<>();


       camisa.forEach(camisa1 -> {

           if (camisa1.getDeletd()){
               System.out.println("-----" + camisa1.getDeletd());
              camisasUtil.add(camisa1);
           }
       });
        model.addAttribute("camisa", camisasUtil);

        Cookie cookie = new Cookie("visita","visitou");
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
    public String getEditarCamisa(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){

        Camisa camisa = service.findById(id);
        model.addAttribute("camisa", camisa);

        redirectAttributes.addAttribute("msg2", "Cadastro atualizado com sucesso");
        return "cadastrar";
    }

    @GetMapping("deletar/{id}")
    public String getDeletarCamisa(
            Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){

        Camisa camisa = service.findById(id);
        camisa.setDeletd(false);
        service.update(camisa);

        List<Camisa> camisas = service.findAll();

        model.addAttribute("camisa", camisas);
        redirectAttributes.addAttribute("msg", "Deleted com sucesso");
        return "redirect:/admin";

        //return "index";
    }

    @PostMapping("salvar")
    public String doSalvaComputador(@ModelAttribute @Valid Camisa c, Errors errors,
                                  @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (errors.hasErrors()){
            redirectAttributes.addAttribute("msg", "Cadastro FRACASSO!");
            return "redirect:/admin";
        }else{
           try {
               CamisaController.contador++;
               c.setDeletd(true);
               c.setImagem(file.getOriginalFilename()+CamisaController.contador);
               service.update(c);
               fileStorageService.save(file);

               redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
               return "redirect:/admin";
           } catch (Exception e){
               redirectAttributes.addAttribute("msg", "Cadastro FRACASSO!");
               return "redirect:/admin";
           }
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

    @GetMapping("/adicionarcarrinho/{id}")
    public String doAdicionarItem(@PathVariable (name = "id") Long id,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Camisa> carrinho = (List<Camisa>) session.getAttribute("carrinho");
        Camisa almoco = service.findById(id);
        if(carrinho == null){
            carrinho = new ArrayList<>();
        }

        carrinho.add(almoco);
        contCarrinho = carrinho.size();
        session.setAttribute("carrinho", carrinho);

        return "/";
    }
    @GetMapping("/admin")
    public String getCamisaAdmin(Model model, HttpServletResponse response){

        List<Camisa> camisa = service.findAll();
        model.addAttribute("camisa", camisa);
        List<Camisa> camisasUtil = new ArrayList<>();


        camisa.forEach(camisa1 -> {

            if (camisa1.getDeletd()){
                System.out.println("-----" + camisa1.getDeletd());
                camisasUtil.add(camisa1);
            }
        });
        model.addAttribute("camisa", camisasUtil);
       /* if (camisasUtil.isEmpty()){
            camisasUtil.add(new Camisa());
        }*/
        model.addAttribute("camisa", camisasUtil);
        Cookie cookie = new Cookie("visita","visitou");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);

        return "index";
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
            return "index";
        }
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpServletRequest request, HttpServletResponse response){
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
        response.addCookie(carrinhoCompras);
        return "redirect:/index";
    }
}
