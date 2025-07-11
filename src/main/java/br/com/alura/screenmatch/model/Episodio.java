package br.com.alura.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer temporada;

    private String titulo;

    private Integer numeroEpisodio;

    private Double avaliacao;

    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio() {
    }

    public Episodio(Integer numeroTemporada, DadosEpsodio dadosEpsodio) {
            this.temporada = numeroTemporada;
            this.titulo = dadosEpsodio.titulo();
            this.numeroEpisodio = dadosEpsodio.numero();

            try {

                this.avaliacao = Double.valueOf(dadosEpsodio.avaliacao());
            }catch (NumberFormatException ex){
                this.avaliacao = 0.0;
            }

            try {

                this.dataLancamento = LocalDate.parse(dadosEpsodio.dataLancamento());
            }catch (DateTimeParseException ex){
                this.dataLancamento = null;
            }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return  "avaliacao= " + avaliacao +
                ", temporada= " + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio= " + numeroEpisodio +
                ", dataLancamento= " + dataLancamento;
    }
}
