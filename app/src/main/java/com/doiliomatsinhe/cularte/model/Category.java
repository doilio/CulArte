package com.doiliomatsinhe.cularte.model;

public class Category {

    private String id;
    private String nome;
    private String descricao;
    private String filterNome;
    private String imagemUrl;
    private Boolean visibilidade;
    private String idUsuarioLogado;

    public Category() {
    }

    public Category(String id, String nome, String descricao, String filterNome, String imagemUrl, Boolean visibilidade, String idUsuarioLogado) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.filterNome = filterNome;
        this.imagemUrl = imagemUrl;
        this.visibilidade = visibilidade;
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFilterNome() {
        return filterNome;
    }

    public void setFilterNome(String filterNome) {
        this.filterNome = filterNome;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Boolean getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(Boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

    public String getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(String idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }
}
