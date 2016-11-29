/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.greenhouse.bean;

import br.udesc.greenhouse.modelo.dao.core.ConfiguracaoDAO;
import br.udesc.greenhouse.modelo.dao.core.FactoryDAO;
import br.udesc.greenhouse.modelo.entidade.Configuracao;
import br.udesc.greenhouse.modelo.entidade.Noticia;
import br.udesc.greenhouse.modelo.entidade.Oficina;
import br.udesc.greenhouse.uc.FormularioMensagemUC;
import br.udesc.greenhouse.uc.GerenciarNoticiasUC;
import com.google.gson.Gson;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ignoi
 */
@ManagedBean
@RequestScoped
public class IndexBean {

    private List<Noticia> usuarios;
    private List<Oficina> oficinas;
    private GerenciarNoticiasUC gerenciador;
    private Gson g;
    private String assunto, corpo, nome, emailOrigem;
    private Configuracao config;
    private ConfiguracaoDAO cdao;

    private Oficina teste;

    @PostConstruct
    public void init() {
        cdao = FactoryDAO.getFactoryDAO().getConfiguracaoDAO();
        if (cdao.listar().isEmpty()) {
            cdao.inserir(new Configuracao("email@email.com", "Aqui deve constar o texto sobre nós", "Aqui deve constar o endereço do projeto"));
        }
        config = cdao.listar().get(0);
        g = new Gson();
        gerenciador = new GerenciarNoticiasUC();
        usuarios = gerenciador.listar();
        oficinas = FactoryDAO.getFactoryDAO().getOficinaDAO().listar();
        teste = oficinas.get(0);
        limpar();
    }

    public List<Noticia> getNoticias() {
        return usuarios;
    }

    public void setNoticias(List<Noticia> usuarios) {
        this.usuarios = usuarios;
    }

    public String getJsonList() {
        System.out.println(g.toJson(usuarios));
        return (usuarios == null) ? "" : g.toJson(usuarios);
    }

    public String getJsonOficinas() {
        System.out.println(g.toJson(oficinas) + "tagggggggggg");
        return (oficinas == null) ? "" : g.toJson(oficinas);
    }

    public String getJsonConfig() {
        System.out.println(g.toJson(config) + "tagggggggggg");
        return (config == null) ? "" : g.toJson(config);
    }

    public String sendEmail() {
        try {
            FormularioMensagemUC fm = new FormularioMensagemUC();
            fm.enviarEmail(assunto, corpo, nome, emailOrigem);
            saveMessage("Sucesso!", "E-mail enviado com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            saveMessage("Erro ao enviar e-mail", "Por favor, tente novamente mais tarde.");
            return null;
        }
        limpar();
        return "index.jsf#third-section";
    }

    public List<Noticia> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Noticia> usuarios) {
        this.usuarios = usuarios;
    }

    public GerenciarNoticiasUC getGerenciador() {
        return gerenciador;
    }

    public void setGerenciador(GerenciarNoticiasUC gerenciador) {
        this.gerenciador = gerenciador;
    }

    public Gson getG() {
        return g;
    }

    public void setG(Gson g) {
        this.g = g;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailOrigem() {
        return emailOrigem;
    }

    public void setEmailOrigem(String emailOrigem) {
        this.emailOrigem = emailOrigem;
    }

    public void limpar() {
        assunto = "";
        corpo = "";
        nome = "";
        emailOrigem = "";
    }

    public static void saveMessage(String title, String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, msg));
    }

    public List<Oficina> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<Oficina> oficinas) {
        this.oficinas = oficinas;
    }

    public Oficina getTeste() {
        return teste;
    }

    public void setTeste(Oficina teste) {
        this.teste = teste;
    }

}
