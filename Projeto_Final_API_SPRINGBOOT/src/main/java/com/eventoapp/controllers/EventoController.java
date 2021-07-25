package com.eventoapp.controllers;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;


@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String  form(){
        return "evento/FormEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String  form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique os campos!!");
            return "redirect:/cadastrarEvento";
        }
        er.save(evento);
        attributes.addFlashAttribute("mensagem","Cadastrado com sucesso!!");
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/evento")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("Index");
        Iterable<Evento> evento =  er.findAll();
        mv.addObject("evento",evento);
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method =RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento",evento);
        Iterable<Convidado> convidados = cr.findByEvento(evento);
        mv.addObject("convidados",convidados);
        return mv;
    }


    @RequestMapping(value = "/{codigo}", method =RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique os campos!!");
            return "redirect:/{codigo}";
        }
        Evento evento = er.findByCodigo(codigo);
        convidado.setEvento(evento);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem","Convidado adicionado com sucesso!!");
        return "redirect:/{codigo}";
    }

    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg){
        Convidado convidado = cr.findByRg(rg);
        cr.delete(convidado);
        Evento evento = convidado.getEvento();
        long codLong = evento.getCodigo();
        String codigo = "" + codLong;
        return "redirect:/"+codigo;
    }

    @RequestMapping(value = "/editarEvento/{codigo}", method = RequestMethod.GET)
    public ModelAndView editEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("/evento/FormeditarEvento");
        mv.addObject("evento",evento);
        return mv;
    }


    @RequestMapping(value = "/editarEvento/{codigo}", method = RequestMethod.POST)
    public String  form1(@PathVariable("codigo") long codigo,@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique os campos!!");
            return "redirect:/editarEvento/{codigo}";
        }
        evento.setCodigo(codigo);
        er.save(evento);
        attributes.addFlashAttribute("mensagem","Atualizado com sucesso!!");
        return "redirect:/editarEvento/{codigo}";
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = er.findByCodigo(codigo);
        Iterable<Convidado> convidado = cr.findByEvento(evento);
        cr.deleteAll(convidado);
        er.delete(evento);

        return "redirect:/evento";
    }

}