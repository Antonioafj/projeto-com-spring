package br.com.alura.screenmatch.model;


import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;

import java.util.OptionalDouble;

public class Serie {
    private  String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria categoria;
    private String atores;
    private String sinopse;
    private String imagemPoster;

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.categoria = Categoria.fromString(dadosSerie.categoria().split(",")[0].trim());
        this.imagemPoster = dadosSerie.imagemPoster();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse());
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagemPoster() {
        return imagemPoster;
    }

    public void setImagemPoster(String imagemPoster) {
        this.imagemPoster = imagemPoster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    @Override
    public String toString() {
        return  "  categoria= " + categoria +
                ", atores=' " + atores + '\'' +
                ", titulo=' " + titulo + '\'' +
                ", totalTemporadas= " + totalTemporadas +
                ", avaliacao= " + avaliacao +
                ", sinopse=' " + sinopse + '\'' +
                ", imagemPoster=' " + imagemPoster + '\'' ;
    }
}
