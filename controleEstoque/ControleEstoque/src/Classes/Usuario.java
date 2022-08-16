/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Date;

/**
 *
 * @author Aluno
 */
public class Usuario {
    
    private Integer id_usuarios;
    private String nome;
    private Date datanascimento;
    private String situacao;
    private String login;
    private String senha;
    private Date dataativacao;
    private Date datadesativacao;

    /**
     * @return the id_usuarios
     */
    public Integer getId_usuarios() {
        return id_usuarios;
    }

    /**
     * @param id_usuarios the id_usuarios to set
     */
    public void setId_usuarios(Integer id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the datanascimento
     */
    public Date getDatanascimento() {
        return datanascimento;
    }

    /**
     * @param datanascimento the datanascimento to set
     */
    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the dataativacao
     */
    public Date getDataativacao() {
        return dataativacao;
    }

    /**
     * @param dataativacao the dataativacao to set
     */
    public void setDataativacao(Date dataativacao) {
        this.dataativacao = dataativacao;
    }

    /**
     * @return the datadesativacao
     */
    public Date getDatadesativacao() {
        return datadesativacao;
    }

    /**
     * @param datadesativacao the datadesativacao to set
     */
    public void setDatadesativacao(Date datadesativacao) {
        this.datadesativacao = datadesativacao;
    }
    
}
