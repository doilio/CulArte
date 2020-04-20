package com.doiliomatsinhe.cularte.model;

import java.io.Serializable;
import java.util.ArrayList;

// Use a Parcelable Interface later
public class Artist implements Serializable {
    private String id;
    private String nomeArtistico;
    private String nomeArtisticoFilter;
    private String nomeCompleto;
    private String nomeCompletoFilter;
    private String curtaDescricao;
    private String linkInstagram;
    private String linkFacebook;
    private String linkGithub;
    private String linkDeezer;
    private String linkLinkedIn;
    private String linkMedium;
    private String linkSpotify;
    private String linkSoundCloud;
    private String linkTwitter;
    private String linkYoutube;
    private String linkEmail;
    private String categoria;
    private String biografia;
    private String carreira;
    private Long contactoProfissional;
    private Boolean visibilidade;
    private ArrayList<String> imagensUrl;
    private String idUsuarioLogado;
    private String dataDeNascimento;

    public Artist() {
    }

    public Artist(String id, String nomeArtistico, String nomeArtisticoFilter, String nomeCompleto, String nomeCompletoFilter, String curtaDescricao, String linkInstagram, String linkFacebook, String linkGithub, String linkDeezer, String linkLinkedIn, String linkMedium, String linkSpotify, String linkSoundCloud, String linkTwitter, String linkYoutube, String linkEmail, String categoria, String biografia, String carreira, Long contactoProfissional, Boolean visibilidade, ArrayList<String> imagensUrl, String idUsuarioLogado, String dataDeNascimento) {
        this.id = id;
        this.nomeArtistico = nomeArtistico;
        this.nomeArtisticoFilter = nomeArtisticoFilter;
        this.nomeCompleto = nomeCompleto;
        this.nomeCompletoFilter = nomeCompletoFilter;
        this.curtaDescricao = curtaDescricao;
        this.linkInstagram = linkInstagram;
        this.linkFacebook = linkFacebook;
        this.linkGithub = linkGithub;
        this.linkDeezer = linkDeezer;
        this.linkLinkedIn = linkLinkedIn;
        this.linkMedium = linkMedium;
        this.linkSpotify = linkSpotify;
        this.linkSoundCloud = linkSoundCloud;
        this.linkTwitter = linkTwitter;
        this.linkYoutube = linkYoutube;
        this.linkEmail = linkEmail;
        this.categoria = categoria;
        this.biografia = biografia;
        this.carreira = carreira;
        this.contactoProfissional = contactoProfissional;
        this.visibilidade = visibilidade;
        this.imagensUrl = imagensUrl;
        this.idUsuarioLogado = idUsuarioLogado;
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    public String getNomeArtisticoFilter() {
        return nomeArtisticoFilter;
    }

    public void setNomeArtisticoFilter(String nomeArtisticoFilter) {
        this.nomeArtisticoFilter = nomeArtisticoFilter;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeCompletoFilter() {
        return nomeCompletoFilter;
    }

    public void setNomeCompletoFilter(String nomeCompletoFilter) {
        this.nomeCompletoFilter = nomeCompletoFilter;
    }

    public String getCurtaDescricao() {
        return curtaDescricao;
    }

    public void setCurtaDescricao(String curtaDescricao) {
        this.curtaDescricao = curtaDescricao;
    }

    public String getLinkInstagram() {
        return linkInstagram;
    }

    public void setLinkInstagram(String linkInstagram) {
        this.linkInstagram = linkInstagram;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public String getLinkGithub() {
        return linkGithub;
    }

    public void setLinkGithub(String linkGithub) {
        this.linkGithub = linkGithub;
    }

    public String getLinkDeezer() {
        return linkDeezer;
    }

    public void setLinkDeezer(String linkDeezer) {
        this.linkDeezer = linkDeezer;
    }

    public String getLinkLinkedIn() {
        return linkLinkedIn;
    }

    public void setLinkLinkedIn(String linkLinkedIn) {
        this.linkLinkedIn = linkLinkedIn;
    }

    public String getLinkMedium() {
        return linkMedium;
    }

    public void setLinkMedium(String linkMedium) {
        this.linkMedium = linkMedium;
    }

    public String getLinkSpotify() {
        return linkSpotify;
    }

    public void setLinkSpotify(String linkSpotify) {
        this.linkSpotify = linkSpotify;
    }

    public String getLinkSoundCloud() {
        return linkSoundCloud;
    }

    public void setLinkSoundCloud(String linkSoundCloud) {
        this.linkSoundCloud = linkSoundCloud;
    }

    public String getLinkTwitter() {
        return linkTwitter;
    }

    public void setLinkTwitter(String linkTwitter) {
        this.linkTwitter = linkTwitter;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getLinkEmail() {
        return linkEmail;
    }

    public void setLinkEmail(String linkEmail) {
        this.linkEmail = linkEmail;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getCarreira() {
        return carreira;
    }

    public void setCarreira(String carreira) {
        this.carreira = carreira;
    }

    public Long getContactoProfissional() {
        return contactoProfissional;
    }

    public void setContactoProfissional(Long contactoProfissional) {
        this.contactoProfissional = contactoProfissional;
    }

    public Boolean getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(Boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

    public ArrayList<String> getImagensUrl() {
        return imagensUrl;
    }

    public void setImagensUrl(ArrayList<String> imagensUrl) {
        this.imagensUrl = imagensUrl;
    }

    public String getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(String idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }
}
